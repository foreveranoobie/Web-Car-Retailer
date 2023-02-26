package com.epam.storozhuk.captcha.strategy.impl;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.captcha.strategy.CaptchaStrategy;
import com.epam.storozhuk.constant.Const;
import nl.captcha.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CookieCaptchaStrategyImpl extends CaptchaStrategy {
    private static final Logger LOGGER = Logger.getLogger(CookieCaptchaStrategyImpl.class);

    public CookieCaptchaStrategyImpl(Map<String, CaptchaDescriptor> mapOfCaptchas) {
        super(mapOfCaptchas);
    }

    @Override
    public void initCaptchaValue(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Put new captcha to map");
        Cookie cookie = new Cookie(Const.CAPTCHA_RIGHT_PARAMETER, writeInitializedValueToMap());
        cookie.setMaxAge(150);
        response.addCookie(cookie);
        LOGGER.debug("Captcha has been put to cookie");
    }

    @Override
    public Captcha writeValueToCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String number = StringUtils.EMPTY;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(Const.CAPTCHA_RIGHT_PARAMETER)) {
                LOGGER.debug("Found captcha value in cookie");
                number = cookie.getValue();
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        LOGGER.debug("Calling captcha creation");
        return createCaptcha(number);
    }
}
