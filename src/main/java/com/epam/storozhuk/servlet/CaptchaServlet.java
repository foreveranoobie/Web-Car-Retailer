package com.epam.storozhuk.servlet;

import com.epam.storozhuk.captcha.strategy.CaptchaStrategy;
import com.epam.storozhuk.constant.Const;
import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CaptchaServlet.class);
    private CaptchaStrategy captchaStrategy;

    public void init() {
        captchaStrategy = (CaptchaStrategy) getServletContext().getAttribute(Const.STRATEGY_TYPE);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("GetCaptchaServlet doGet() start");
        Captcha captcha = captchaStrategy.writeValueToCaptcha(request, response);
        CaptchaServletUtil.writeImage(response, captcha.getImage());
        LOGGER.debug("GetCaptchaServlet doGet() end");
    }
}