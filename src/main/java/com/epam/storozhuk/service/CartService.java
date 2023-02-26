package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Cart;

public interface CartService {
    void addProductToCart(String productId, String login, int amount);

    boolean clearCart(String login);

    //void incrementProductAmount(String login, int productId);

    //void decrementProductAmount(String login, int productId);

    //void removeProduct(String login, int productId);

    Cart getCart(String login);
}
