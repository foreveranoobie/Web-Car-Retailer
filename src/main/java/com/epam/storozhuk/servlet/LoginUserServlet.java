package com.epam.storozhuk.servlet;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.UserService;
import com.epam.storozhuk.service.UserSessionService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginUser")
public class LoginUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterUserServlet.class);
    private UserService userService;
    private CartService cartService;

    public void init() {
        this.userService = (UserService) getServletContext().getAttribute(Const.USER_SERVICE);
        this.cartService = (CartService) getServletContext().getAttribute(Const.CART_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(Const.LOGIN_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug("Couldn't load login page because of an error: " + e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(Const.LOGIN_PARAMETER);
        String password = request.getParameter(Const.PASSWORD_PARAMETER);
        try {
            User user = userService.loginUser(login, password);
            if (user != null) {
                UserSessionService userSessionService = (UserSessionService) request.getServletContext().getAttribute(Const.USER_SESSION_SERVICE);
                userSessionService.saveUser(request.getSession(), user, cartService);
                request.getSession().setAttribute(Const.CART_PARAMETER, cartService.getCart(login));
                request.getSession().setAttribute(Const.CART_SERVICE, cartService);
                response.sendRedirect(Const.MAIN_PAGE);
            } else {
                request.setAttribute(Const.USER_ERROR, "Wrong field data");
                request.setAttribute(Const.LOGIN_PARAMETER, login);
                forwardToLogin(request, response);
            }
        } catch (IOException e) {
            LOGGER.debug("Forward to login has been fault because of input/output exception: " + e.getMessage());
        }
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher(Const.LOGIN_PAGE).forward(request, response);
        } catch (ServletException e) {
            LOGGER.debug("Forward to login has been fault because of servlet exception: " + e.getMessage());
        }
    }
}
