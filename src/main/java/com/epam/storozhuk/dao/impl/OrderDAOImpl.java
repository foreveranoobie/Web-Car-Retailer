package com.epam.storozhuk.dao.impl;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.constant.order.OrderConst;
import com.epam.storozhuk.dao.OrderDAO;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.entity.order.Order;
import com.epam.storozhuk.entity.order.OrderedProduct;
import com.epam.storozhuk.sql.builder.SQLInsertBuilder;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(OrderDAOImpl.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Const.DATE_FORMAT);

    @Override
    public boolean createNewOrder(List<String> orderParameters, Map<Product, Integer> cartProducts, Connection connection) throws SQLException {
        boolean insertedOrder = false;
        String login = orderParameters.get(Const.LOGIN_PARAMETER_INDEX);
        try {
            if (insertOrder(orderParameters, connection)) {
                int lastOrderId = getLatestOrderId(login, connection);
                insertedOrder = insertOrderProducts(cartProducts, lastOrderId, connection);
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't create new order because of exception: " + e.getMessage());
            throw e;
        }
        return insertedOrder;
    }

    public boolean insertOrder(List<String> orderParameters, Connection connection) throws SQLException {
        boolean insertedSuccessfully = false;
        String query = OrderConst.SQL_ORDER_INSERT;
        String date = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        String card = orderParameters.get(Const.CARD_PARAMETER_INDEX);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, orderParameters.get(Const.LOGIN_PARAMETER_INDEX));
            preparedStatement.setString(2, orderParameters.get(Const.USERNAME_PARAMETER_INDEX));
            preparedStatement.setString(3, date);
            if (!card.equals("0")) {
                preparedStatement.setLong(4, Long.parseLong(orderParameters.get(Const.CARD_PARAMETER_INDEX)));
            } else {
                preparedStatement.setLong(4, 0);
            }
            preparedStatement.setInt(5, 1);
            if (preparedStatement.executeUpdate() != 0) {
                insertedSuccessfully = true;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't make new order because of exception: " + e.getMessage());
            throw e;
        }
        return insertedSuccessfully;
    }

    private boolean insertOrderProducts(Map<Product, Integer> cartProducts, int orderId, Connection connection) throws SQLException {
        boolean insertedSuccessfully = false;
        SQLInsertBuilder sqlInsertBuilder = new SQLInsertBuilder();
        sqlInsertBuilder.insertInto(OrderConst.SQL_ORDER_PRODUCTS_TABLE);
        sqlInsertBuilder.columns(OrderConst.SQL_COLUMN_ORDER_ID, OrderConst.SQL_COLUMN_PRODUCT_ID,
                OrderConst.SQL_COLUMN_PRODUCT_AMOUNT, OrderConst.SQL_COLUMN_PRODUCT_PRICE);
        sqlInsertBuilder.setRowsCount(cartProducts.size());
        String query = sqlInsertBuilder.toString();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int index = 1;
            for (Product product : cartProducts.keySet()) {
                preparedStatement.setInt(index++, orderId);
                preparedStatement.setInt(index++, product.getProductId());
                preparedStatement.setInt(index++, cartProducts.get(product));
                preparedStatement.setBigDecimal(index++, product.getPrice());
            }
            if (preparedStatement.executeUpdate() != 0) {
                insertedSuccessfully = true;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't insert product to order because of exception: " + e.getMessage());
            throw e;
        }
        return insertedSuccessfully;
    }

    @Override
    public Order getOrder(int orderId, Connection connection) throws SQLException {
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(OrderConst.SQL_GET_ORDER)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    List<OrderedProduct> orderedProducts = new ArrayList<>();
                    String status = resultSet.getString(OrderConst.SQL_COLUMN_ORDER_STATUS);
                    String statusDescription = resultSet.getString(OrderConst.SQL_COLUMN_STATUS_DESCRIPTION);
                    String creationDate = resultSet.getString(OrderConst.SQL_COLUMN_ORDER_DATE);
                    String userLogin = resultSet.getString(OrderConst.SQL_COLUMN_ORDER_USER_LOGIN);
                    String userCard = resultSet.getString(OrderConst.SQL_COLUMN_ORDER_USER_CARD);
                    orderedProducts.add(getOrderedProduct(resultSet));
                    while (resultSet.next()) {
                        orderedProducts.add(getOrderedProduct(resultSet));
                    }
                    order = new Order(orderId, status, statusDescription, creationDate, userLogin, userCard, orderedProducts);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get products from order because of exception: " + e.getMessage());
            throw e;
        }
        return order;
    }

    @Override
    public List<Integer> getOrdersId(String login, Connection connection) throws SQLException {
        List<Integer> ordersId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(OrderConst.SQL_USER_ORDERS_ID)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ordersId.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get user's IDs because of exception: " + e.getMessage());
            throw e;
        }
        return ordersId;
    }

    @Override
    public int getLatestOrderId(String userLogin, Connection connection) throws SQLException {
        int id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(OrderConst.SQL_LATEST_ORDER)) {
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userLogin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get latest order ID because of exception: " + e.getMessage());
            throw e;
        }
        return id;
    }

    private OrderedProduct getOrderedProduct(ResultSet resultSet) throws SQLException {
        Product product = getProduct(resultSet);
        int amount = resultSet.getInt(OrderConst.SQL_COLUMN_PRODUCT_AMOUNT);
        return new OrderedProduct(product, amount);
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(OrderConst.SQL_COLUMN_PRODUCT_ID);
        String mark = resultSet.getString(OrderConst.SQL_COLUMN_PRODUCT_MARK);
        String model = resultSet.getString(OrderConst.SQL_COLUMN_PRODUCT_MODEL);
        String category = resultSet.getString(OrderConst.SQL_COLUMN_PRODUCT_CATEGORY);
        BigDecimal price = resultSet.getBigDecimal(OrderConst.SQL_COLUMN_PRODUCT_PRICE);
        int year = resultSet.getInt(OrderConst.SQL_COLUMN_PRODUCT_YEAR);
        String fuelType = resultSet.getString(OrderConst.SQL_COLUMN_PRODUCT_FUELTYPE);
        int mileage = resultSet.getInt(OrderConst.SQL_COLUMN_PRODUCT_MILEAGE);
        String image = resultSet.getString(OrderConst.SQL_COLUMN_PRODUCT_PICTURE);
        return new Product(id, mark, model, category, price, year, fuelType, mileage, image);
    }
}
