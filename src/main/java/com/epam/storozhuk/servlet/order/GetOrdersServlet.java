package com.epam.storozhuk.servlet.order;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.constant.order.OrderConst;
import com.epam.storozhuk.entity.order.Order;
import com.epam.storozhuk.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getOrders")
public class GetOrdersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetOrdersServlet.class);
    private OrderService orderService;

    @Override
    public void init() {
        orderService = (OrderService) getServletContext().getAttribute(Const.ORDER_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getSession().getAttribute(Const.USER_STATE_ATTRIBUTE);
        List<Order> ordersList = orderService.getOrders(login);
        request.setAttribute(OrderConst.ORDERS_LIST_PARAM, ordersList);
        try {
            request.getRequestDispatcher(OrderConst.ORDERS_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug("Couldn't make forward because of exception: " + e.getMessage());
        }
    }
}
