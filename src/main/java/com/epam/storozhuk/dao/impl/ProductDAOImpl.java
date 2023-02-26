package com.epam.storozhuk.dao.impl;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dao.ProductDAO;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.sql.RequestWrapper;
import com.epam.storozhuk.sql.builder.SQLSelectBuilder;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAOImpl implements ProductDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class);

    @Override
    public List<Product> getProducts(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.selectAll().from(Const.SQL_PRODUCTS_TABLE).limit(Const.PAGINATION_LIMIT_DEFAULT).orderBy(Const.SQL_PRODUCTS_TABLE + "_" + Const.SQL_PRICE);
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlSelectBuilder.toString())) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    products.add(getProductFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't receive products list from database because of exception: " + e.getMessage());
            throw e;
        }
        return products;
    }

    @Override
    public Product getProductById(int id, Connection connection) throws SQLException {
        Product product = null;
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.select("*");
        sqlSelectBuilder.from(Const.SQL_PRODUCTS_TABLE);
        sqlSelectBuilder.where(Const.SQL_PRODUCTS_TABLE + "_" + Const.SQL_IDENTIFICATOR);
        try (PreparedStatement statement = connection.prepareStatement(sqlSelectBuilder.toString())) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = getProductFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get product by ID from database because of exception: " + e.getMessage());
            throw e;
        }
        return product;
    }

    public List<Product> getFilteredProducts(Connection connection, RequestWrapper requestWrapper) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.selectAll().from(Const.SQL_PRODUCTS_TABLE);
        Map<String, String[]> requestParams = requestWrapper.getCheckboxes();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.whereIn(Const.SQL_PRODUCTS_TABLE + "_" + param, requestParams.get(param));
        }
        requestParams = requestWrapper.getRanges();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.whereBetween(Const.SQL_PRODUCTS_TABLE + "_" + param, requestParams.get(param));
        }
        requestParams = requestWrapper.getOrders();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.orderBy(Const.SQL_PRODUCTS_TABLE + "_" + requestParams.get(param)[0]);
        }
        requestParams = requestWrapper.getPagination();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.limit(requestParams.get(param));
        }
        String query = sqlSelectBuilder.toString();
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    products.add(getProductFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get filtered products because of exception: " + e.getMessage());
            throw e;
        }
        return products;
    }

    public int countProducts(Connection connection, RequestWrapper requestWrapper) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.count(null).from(Const.SQL_PRODUCTS_TABLE);
        Map<String, String[]> requestParams = requestWrapper.getCheckboxes();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.whereIn(Const.SQL_PRODUCTS_TABLE + "_" + param, requestParams.get(param));
        }
        requestParams = requestWrapper.getRanges();
        for (String param : requestParams.keySet()) {
            sqlSelectBuilder.whereBetween(Const.SQL_PRODUCTS_TABLE + "_" + param, requestParams.get(param));
        }
        String query = sqlSelectBuilder.toString();
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                LOGGER.debug("Successfully received count row from the database");
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't count products in database because of exception: " + e.getMessage());
            throw e;
        }
        return count;
    }

    public Map<String, String> getRangedValues(Connection connection, String[] parameterNames) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        for (String parameter : parameterNames) {
            sqlSelectBuilder.selectMin(Const.SQL_PRODUCTS_TABLE + "_" + parameter, "rangeMin_" + parameter);
            sqlSelectBuilder.selectMax(Const.SQL_PRODUCTS_TABLE + "_" + parameter, "rangeMax_" + parameter);
        }
        sqlSelectBuilder.from(Const.SQL_PRODUCTS_TABLE);
        Map<String, String> rangedValues = new HashMap<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlSelectBuilder.toString())) {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            resultSet.next();
            for (int i = 1; i < parameterNames.length * 2 + 1; i += 2) {
                rangedValues.put(rsmd.getColumnName(i), resultSet.getString(i));
                rangedValues.put(rsmd.getColumnName(i + 1), resultSet.getString(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't receive ranged values from database because of exception: " + e.getMessage());
            throw e;
        }
        return rangedValues;
    }

    @Override
    public List<String> getMarks(Connection connection) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.from(Const.SQL_PRODUCTS_TABLE).selectDistinct(Const.SQL_PRODUCTS_TABLE + "_" + Const.SQL_MARK, null);
        List<String> marks = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlSelectBuilder.toString())) {
            while (resultSet.next()) {
                marks.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get marks list because of exception: " + e.getMessage());
            throw e;
        }
        return marks;
    }

    @Override
    public List<String> getFuelTypes(Connection connection) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.from(Const.SQL_PRODUCTS_TABLE).selectDistinct(Const.SQL_PRODUCTS_TABLE + "_" + Const.SQL_FUELTYPE, null);
        List<String> fuelTypes = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlSelectBuilder.toString())) {
            while (resultSet.next()) {
                fuelTypes.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get vehicle's fuel types because of exception: " + e.getMessage());
            throw e;
        }
        return fuelTypes;
    }

    @Override
    public List<String> getCategories(Connection connection) throws SQLException {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilder();
        sqlSelectBuilder.from(Const.SQL_PRODUCTS_TABLE).selectDistinct(Const.SQL_PRODUCTS_TABLE + "_" + Const.SQL_CATEGORY, null);
        List<String> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlSelectBuilder.toString())) {
            while (resultSet.next()) {
                categories.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get list of categories from database because of exception: " + e.getMessage());
            throw e;
        }
        return categories;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                resultSet.getBigDecimal(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getInt(8),
                resultSet.getString(9));
    }
}
