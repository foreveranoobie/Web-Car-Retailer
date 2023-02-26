package com.epam.storozhuk.servlet.cart;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/clearCart")
public class ClearCartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Const.CART_PARAMETER, new Cart());
        response.setStatus(200);
    }
}
