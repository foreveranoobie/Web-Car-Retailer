package com.epam.storozhuk.dao.impl;

import com.epam.storozhuk.constant.cart.CartConst;
import com.epam.storozhuk.dao.CartDAO;
import com.epam.storozhuk.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartDAOImpl implements CartDAO {
    private static final Logger LOGGER = Logger.getLogger(CartDAOImpl.class);

    @Override
    public boolean createUserCart(String login, Connection connection) throws SQLException {
        boolean successfulInsertion = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_INSERT_USER_TO_CART)) {
            preparedStatement.setString(1, login);
            int rowsCount = preparedStatement.executeUpdate();
            if (rowsCount == 0) {
                successfulInsertion = false;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't create cart for user because of exception: " + e.getMessage());
            throw e;
        }
        return successfulInsertion;
    }

    @Override
    public boolean removeCart(int cartId, Connection connection) throws SQLException {
        boolean cartRemoved = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_REMOVE_CART)) {
            preparedStatement.setInt(1, cartId);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                cartRemoved = true;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't clear cart because of exception: " + e.getMessage());
            throw e;
        }
        return cartRemoved;
    }

    public int getCartId(String login, Connection connection) throws SQLException {
        int cartId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_GET_CART_ID)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cartId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get card ID because of exception: " + e.getMessage());
            throw e;
        }
        return cartId;
    }

    public boolean containsProduct(int cartId, int productId, Connection connection) throws SQLException {
        boolean containsProduct = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_SELECT_PRODUCT)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    containsProduct = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't check if database cart contains product because of exception: " + e.getMessage());
            throw e;
        }
        return containsProduct;
    }

    public boolean putProductToCart(int cartId, int productId, int amount, Connection connection) throws SQLException {
        boolean successfulInsertion = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_PUT_PRODUCT)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, amount);
            int rowsCount = preparedStatement.executeUpdate();
            if (rowsCount == 0) {
                successfulInsertion = false;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't put product to cart because of exception: " + e.getMessage());
            throw e;
        }
        return successfulInsertion;
    }

    public Map<Product, Integer> getCartProducts(int cartId, Connection connection) throws SQLException {
        Map<Product, Integer> cartProducts = new LinkedHashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_GET_PRODUCTS)) {
            preparedStatement.setInt(1, cartId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    putProductToCartMap(cartProducts, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get products from cart because of exception: " + e.getMessage());
            throw e;
        }
        return cartProducts;
    }

    @Override
    public boolean incrementProductAmount(int cartId, int productId, Connection connection) throws SQLException {
        boolean isIncremented = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CartConst.SQL_INCREMENT_AMOUNT)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);
            if (preparedStatement.executeUpdate() != 0) {
                isIncremented = true;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't increment product amount because of exception: " + e.getMessage());
            throw e;
        }
        return isIncremented;
    }

    private void putProductToCartMap(Map<Product, Integer> cartProducts, ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                resultSet.getBigDecimal(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getString(9));
        cartProducts.put(product, resultSet.getInt(10));
    }
}
