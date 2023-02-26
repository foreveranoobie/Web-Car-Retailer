package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.dao.ProductDAO;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.ProductService;
import com.epam.storozhuk.sql.RequestWrapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
    private ProductDAO productDAO;
    private ManagerDB managerDB;

    public ProductServiceImpl(ProductDAO productDAO, ManagerDB managerDB) {
        this.productDAO = productDAO;
        this.managerDB = managerDB;
    }

    public List<Product> getProducts() {
        LOGGER.debug("started");
        List<Product> products = managerDB.doNonTransactionQuery((connection) ->
                productDAO.getProducts(connection));
        if(products == null){
            products = Collections.emptyList();
        }
        if (products.isEmpty()) {
            LOGGER.debug("Products list is empty");
        }
        LOGGER.debug("Returning products");
        return products;
    }

    public List<Product> getFilteredProducts(RequestWrapper requestWrapper) {
        LOGGER.debug("started");
        List<Product> products = managerDB.doNonTransactionQuery((connection) ->
                productDAO.getFilteredProducts(connection, requestWrapper));
        if(products == null){
            products = Collections.emptyList();
        }
        if (products.isEmpty()) {
            LOGGER.debug("Products list is empty");
        }
        LOGGER.debug("Returning products");
        return products;
    }

    public Map<String, Object[]> getCheckboxValues() {
        Map<String, Object[]> checkboxValues = new HashMap<>();
        Map<String, Supplier<List<String>>> checkboxValuesSearcher = getMapForCheckboxesSearch();
        List<String> value;
        for (String key : checkboxValuesSearcher.keySet()) {
            value = checkboxValuesSearcher.get(key).get();
            if(value == null){
                value = new ArrayList<>();
            }
            checkboxValues.put(key, value.toArray());
        }
        return checkboxValues;
    }

    @Override
    public Map<String, String> getRangedValues() {
        LOGGER.debug("started");
        Map<String, String> rangedValues = managerDB.doNonTransactionQuery((connection) ->
                productDAO.getRangedValues(connection, initRangedParameters()));
        if(rangedValues == null){
            rangedValues = Collections.emptyMap();
        }
        if (rangedValues.isEmpty()) {
            LOGGER.debug("Products list is empty");
        }
        LOGGER.debug("Returning rangedValues");
        return rangedValues;
    }

    @Override
    public List<String> getCheckedBoxes(Map<String, String[]> boxesMap) {
        List<String> checkedValues = new ArrayList<>();
        for (String key : boxesMap.keySet()) {
            if (key.contains(Const.WRAPPER_CHECKBOX + "_")) {
                checkedValues.addAll(Arrays.asList(boxesMap.get(key)));
            }
        }
        return checkedValues;
    }

    @Override
    public Product getProductById(int id) {
        Product product = managerDB.doNonTransactionQuery((connection -> productDAO.getProductById(id, connection)));
        if(product == null){
            LOGGER.debug("Product object is null");
        }
        return product;
    }


    public int countProducts(Integer currentLimit, RequestWrapper requestWrapper) {
        Integer productsSize = managerDB.doNonTransactionQuery((connection) ->
                productDAO.countProducts(connection, requestWrapper));
        if (productsSize == null) {
            productsSize = 0;
        }
        if (productsSize == 0) {
            LOGGER.debug("Products list is empty");
            return 1;
        }
        int pagesCount = productsSize / currentLimit;
        if (productsSize % currentLimit != 0) {
            pagesCount++;
        }
        LOGGER.debug("Returning pages count");
        return pagesCount;
    }

    private String[] initRangedParameters() {
        return new String[]{Const.SQL_PRICE, Const.SQL_MILEAGE, Const.SQL_YEAR};
    }

    private Map<String, Supplier<List<String>>> getMapForCheckboxesSearch() {
        Map<String, Supplier<List<String>>> checkboxValuesSearchers = new HashMap<>();
        checkboxValuesSearchers.put(Const.SQL_MARK, () -> managerDB.doNonTransactionQuery((connection) ->
                productDAO.getMarks(connection)));
        checkboxValuesSearchers.put(Const.SQL_FUELTYPE, () -> managerDB.doNonTransactionQuery((connection) ->
                productDAO.getFuelTypes(connection)));
        checkboxValuesSearchers.put(Const.SQL_CATEGORY, () -> managerDB.doNonTransactionQuery((connection) ->
                productDAO.getCategories(connection)));
        return checkboxValuesSearchers;
    }
}
