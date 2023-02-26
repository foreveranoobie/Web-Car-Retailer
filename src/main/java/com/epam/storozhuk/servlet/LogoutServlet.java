package com.epam.storozhuk.servlet;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.service.UserSessionService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        UserSessionService userSessionService = (UserSessionService) request.getServletContext().getAttribute(Const.USER_SESSION_SERVICE);
        userSessionService.deleteUser(request.getSession());
        LOGGER.debug("Removed userState attribute from appContext");
        try {
            response.sendRedirect(Const.MAIN_PAGE);
        } catch (IOException e) {
            LOGGER.debug("Couldn't redirect to main page because of the exception: " + e.getMessage());
        }
    }
}
