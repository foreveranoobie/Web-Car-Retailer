package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.order.Order;

import java.util.List;

public interface OrderService {
    boolean createOrder(List<String> orderParameters, Cart cart);

    List<Order> getOrders(String login);
}
