package com.epam.storozhuk.listener;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.CartService;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String login = (String) se.getSession().getAttribute(Const.USER_STATE_ATTRIBUTE);
        if (login != null) {
            CartService cartService = (CartService) se.getSession().getAttribute(Const.CART_SERVICE);
            Cart cart = (Cart) se.getSession().getAttribute(Const.CART_PARAMETER);
            if (cart != null) {
                cartService.clearCart(login);
                Map<Product, Integer> cartProducts = cart.getCartProducts();
                for (Product product : cartProducts.keySet()) {
                    cartService.addProductToCart(String.valueOf(product.getProductId()), login, cartProducts.get(product));
                }
            }
        }
    }
}
