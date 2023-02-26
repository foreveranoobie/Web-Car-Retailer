package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.UserSessionService;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class UserSessionServiceImpl implements UserSessionService {
    private CartService cartService;

    public UserSessionServiceImpl(CartService cartService) {
        this.cartService = cartService;
    }

    public void saveUser(HttpSession session, User user, CartService cartService) {
        if (session.getAttribute(Const.CART_PARAMETER) != null) {
            Cart cart = (Cart) session.getAttribute(Const.CART_PARAMETER);
            addCartToDatabase(cart, user.getUserName());
        }
        session.removeAttribute(Const.CART_PARAMETER);
        session.setAttribute(Const.USER_STATE_ATTRIBUTE, user.getUserName());
        session.setAttribute(Const.USER_AVATAR_ATTRIBUTE, user.getAvatarPath());
    }

    public void deleteUser(HttpSession session) {
        session.invalidate();
    }

    private void addCartToDatabase(Cart cart, String login) {
        Map<Product, Integer> cartProducts = cart.getCartProducts();
        for (Product product : cartProducts.keySet()) {
            cartService.addProductToCart(String.valueOf(product.getProductId()), login, cartProducts.get(product));
        }
    }
}
