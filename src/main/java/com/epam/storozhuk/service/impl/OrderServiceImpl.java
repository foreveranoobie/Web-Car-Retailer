package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.dao.OrderDAO;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.order.Order;
import com.epam.storozhuk.service.OrderService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
    private OrderDAO orderDAO;
    private ManagerDB managerDB;

    public OrderServiceImpl(OrderDAO orderDAO, ManagerDB managerDB) {
        this.orderDAO = orderDAO;
        this.managerDB = managerDB;
    }

    @Override
    public boolean createOrder(List<String> orderParameters, Cart cart) {
        Boolean isCreatedOrder;
        isCreatedOrder = managerDB.doTransactionQuery((connection -> orderDAO.createNewOrder(orderParameters, cart.getCartProducts(), connection)));
        if (isCreatedOrder == null) {
            isCreatedOrder = false;
        }
        return isCreatedOrder;
    }

    @Override
    public List<Order> getOrders(String login) {
        List<Integer> ordersId = managerDB.doNonTransactionQuery(connection -> orderDAO.getOrdersId(login, connection));
        List<Order> ordersList = new ArrayList<>();
        if (ordersId != null) {
            for (Integer id : ordersId) {
                ordersList.add(managerDB.doNonTransactionQuery(connection -> orderDAO.getOrder(id, connection)));
            }
        }
        return ordersList;
    }
}
