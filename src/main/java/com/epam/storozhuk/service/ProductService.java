package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.sql.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String, Object[]> getCheckboxValues();

    int countProducts(Integer currentLimit, RequestWrapper requestWrapper);

    List<Product> getProducts();

    List<Product> getFilteredProducts(RequestWrapper requestWrapper);

    Map<String, String> getRangedValues();

    List<String> getCheckedBoxes(Map<String, String[]> boxesMap);

    Product getProductById(int id);

    default RequestWrapper getRequestWrapper(HttpServletRequest request) {
        RequestWrapper requestWrapper = new RequestWrapper();
        Map<String, String[]> parameters = request.getParameterMap();
        for (String key : parameters.keySet()) {
            if (key.contains("_")) {
                requestWrapper.putParam(key, parameters.get(key));
            }
        }
        return requestWrapper;
    }

    default void checkForCorrectPage(RequestWrapper requestWrapper, int pagesCount) {
        String stringPage = requestWrapper.getPagination().get("page")[1];
        int page = Integer.parseInt(stringPage);
        if (page + 1 > pagesCount) {
            requestWrapper.getPagination().get("page")[1] = String.valueOf(pagesCount - 1);
        }
    }
}
