package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.sql.RequestWrapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDAO {
    List<Product> getProducts(Connection connection) throws SQLException;

    Product getProductById(int id, Connection connection) throws SQLException;

    int countProducts(Connection connection, RequestWrapper requestWrapper) throws SQLException;

    List<Product> getFilteredProducts(Connection connection, RequestWrapper requestWrapper) throws SQLException;

    Map<String, String> getRangedValues(Connection connection, String[] parameterNames) throws SQLException;

    List<String> getMarks(Connection connection) throws SQLException;

    List<String> getFuelTypes(Connection connection) throws SQLException;

    List<String> getCategories(Connection connection) throws SQLException;
}
