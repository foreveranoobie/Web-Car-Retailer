package com.epam.storozhuk.servlet;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.ProductService;
import com.epam.storozhuk.sql.RequestWrapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/getProducts")
public class GetProductsServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetProductsServlet.class);
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute(Const.PRODUCT_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> mapOfRanges;
        List<Product> productsList;
        RequestWrapper requestWrapper = productService.getRequestWrapper(request);
        int pagesCount = productService.countProducts(getProductsLimit(request), requestWrapper);
        request.setAttribute(Const.PAGES_COUNT_PARAMETER, pagesCount);
        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
        parameterMap.remove(Const.LANG_PARAM);
        if (parameterMap.isEmpty()) {
            mapOfRanges = productService.getRangedValues();
            productsList = productService.getProducts();
            saveProductsListAndPage(request, 0, productsList);
        } else {
            productService.checkForCorrectPage(requestWrapper, pagesCount);
            productsList = productService.getFilteredProducts(requestWrapper);
            mapOfRanges = getRangedValues(request.getParameterMap());
            if (mapOfRanges.isEmpty()) {
                mapOfRanges = productService.getRangedValues();
            }
            saveProductsListAndPage(request, Integer.parseInt(requestWrapper.getPagination().get(Const.WRAPPER_PAGE)[1]), productsList);
            saveFilters(request);
        }
        request.setAttribute(Const.CHECKBOXES_LIST_PARAMETER, productService.getCheckboxValues());
        request.setAttribute(Const.RANGED_LIST_PARAMETER, mapOfRanges);
        try {
            request.getRequestDispatcher(Const.PRODUCTS_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    private void saveProductsListAndPage(HttpServletRequest request, int currentPage, List<Product> productsList) {
        request.setAttribute(Const.PRODUCTS_LIST_PARAMETER, productsList);
        request.setAttribute(Const.CURRENT_PAGE_PARAMETER, currentPage);
    }

    private void saveFilters(HttpServletRequest request) {
        request.setAttribute(Const.CHECKED_VALUES_PARAMETER, productService.getCheckedBoxes(request.getParameterMap()));
        request.setAttribute(Const.SELECTED_ORDER_PARAMETER, request.getParameter(Const.SORT_ORDER_PARAMETER));
        request.setAttribute(Const.SELECTED_LIMIT_PARAMETER, request.getParameter(Const.PRODUCTS_COUNT_LIMIT_PARAMETER));
    }

    private Integer getProductsLimit(HttpServletRequest request) {
        return request.getParameter(Const.PRODUCTS_COUNT_LIMIT_PARAMETER) == null ? 6 : Integer.parseInt(request.getParameter(Const.PRODUCTS_COUNT_LIMIT_PARAMETER));
    }


    private Map<String, String> getRangedValues(Map<String, String[]> parameters) {
        Map<String, String> rangedValues = new HashMap<>();
        for (String key : parameters.keySet()) {
            if (key.contains(Const.WRAPPER_RANGE)) {
                rangedValues.put(key, parameters.get(key)[0]);
            }
        }
        return rangedValues;
    }
}
