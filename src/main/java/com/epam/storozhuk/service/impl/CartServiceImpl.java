package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.CartDAO;
import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.CartService;
import org.apache.log4j.Logger;

import java.util.Map;

public class CartServiceImpl implements CartService {
    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
    private CartDAO cartDAO;
    private ManagerDB managerDB;

    public CartServiceImpl(CartDAO cartDAO, ManagerDB managerDB) {
        this.cartDAO = cartDAO;
        this.managerDB = managerDB;
    }

    @Override
    public void addProductToCart(String productId, String login, int amount) {
        Integer cartId = managerDB.doNonTransactionQuery(connection -> cartDAO.getCartId(login, connection));
        if (cartId != null && cartId != -1) {
            LOGGER.debug("Cart ID isn't null, continue to put the product to cart");
            int productIdNum = Integer.parseInt(productId);
            int finalCartId = cartId;
            if (!managerDB.doNonTransactionQuery(connection -> cartDAO.containsProduct(finalCartId, productIdNum, connection))) {
                managerDB.doTransactionQuery((connection -> cartDAO.putProductToCart(finalCartId, productIdNum, amount, connection)));
            } else {
                managerDB.doTransactionQuery(connection -> cartDAO.incrementProductAmount(finalCartId, productIdNum, connection));
            }
        }
    }

    @Override
    public boolean clearCart(String login) {
        Integer cartId = managerDB.doNonTransactionQuery(connection -> cartDAO.getCartId(login, connection));
        Boolean isClearCart = false;
        if (cartId != null && cartId != -1) {
            LOGGER.debug("Cart ID isn't null, continue to put the product to cart");
            isClearCart = managerDB.doTransactionQuery((connection -> cartDAO.removeCart(cartId, connection)));
            if (isClearCart == null) {
                isClearCart = false;
            }
        }
        return isClearCart;
    }

    @Override
    public Cart getCart(String login) {
        Cart cart = null;
        Integer cartId = managerDB.doNonTransactionQuery(connection -> cartDAO.getCartId(login, connection));
        if (cartId != null && cartId == -1){
            managerDB.doNonTransactionQuery(connection -> cartDAO.createUserCart(login, connection));
            cartId = managerDB.doNonTransactionQuery(connection -> cartDAO.getCartId(login, connection));
        }
        if (cartId != null && cartId != -1) {
            Integer finalCartId = cartId;
            Map<Product, Integer> productsCart = managerDB.doNonTransactionQuery(connection -> cartDAO.getCartProducts(finalCartId, connection));
            if (productsCart != null) {
                cart = new Cart(productsCart);
            }
        }
        return cart;
    }
}
