package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.entity.order.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDAO {
    boolean createNewOrder(List<String> orderParameters, Map<Product, Integer> cartProducts, Connection connection) throws SQLException;

    Order getOrder(int orderId, Connection connection) throws SQLException;

    List<Integer> getOrdersId(String login, Connection connection) throws SQLException;

    int getLatestOrderId(String userLogin, Connection connection) throws SQLException;
}
