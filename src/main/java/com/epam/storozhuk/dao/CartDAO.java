package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface CartDAO {
    boolean createUserCart(String login, Connection connection) throws SQLException;

    boolean containsProduct(int cartId, int productId, Connection connection) throws SQLException;

    boolean removeCart(int cartId, Connection connection) throws SQLException;

    int getCartId(String login, Connection connection) throws SQLException;

    boolean putProductToCart(int cartId, int productId, int amount, Connection connection) throws SQLException;

    Map<Product, Integer> getCartProducts(int cartId, Connection connection) throws SQLException;

    boolean incrementProductAmount(int cartId, int productId, Connection connection) throws SQLException;
}
