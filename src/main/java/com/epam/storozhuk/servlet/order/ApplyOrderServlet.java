package com.epam.storozhuk.servlet.order;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.constant.order.OrderConst;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/applyOrder")
public class ApplyOrderServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ApplyOrderServlet.class);
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() {
        orderService = (OrderService) getServletContext().getAttribute(Const.ORDER_SERVICE);
        cartService = (CartService) getServletContext().getAttribute(Const.CART_SERVICE);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<String> parameters = new ArrayList<>();
        String login = (String) request.getSession().getAttribute(Const.USER_STATE_ATTRIBUTE);
        Cart cart = (Cart) request.getSession().getAttribute(Const.CART_PARAMETER);
        parameters.add(login);
        parameters.add(request.getParameter(OrderConst.RECEIVER_NAME));
        String userCard = request.getParameter(OrderConst.CARD_NUMBER);
        if (userCard == null || userCard.isEmpty()) {
            userCard = "0";
        }
        parameters.add(userCard);
        boolean createdOrder = orderService.createOrder(parameters, cart);
        if (createdOrder) {
            cartService.clearCart(login);
            request.getSession().setAttribute(Const.CART_PARAMETER, new Cart());
            request.getSession().removeAttribute(Const.CART_PARAMETER);
            try {
                response.sendRedirect(OrderConst.SUCCESSFUL_ORDER_PAGE);
            } catch (IOException e) {
                LOGGER.debug("Couldn't write response message because of exception: " + e.getMessage());
            }
        } else {
            try {
                response.sendRedirect(OrderConst.FAILED_ORDER_PAGE);
            } catch (IOException e) {
                LOGGER.debug("Couldn't write responge message because of exception: " + e.getMessage());
            }
        }
    }
}
