package com.epam.storozhuk.captcha.strategy.impl;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.captcha.strategy.CaptchaStrategy;
import com.epam.storozhuk.constant.Const;
import nl.captcha.Captcha;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class SessionCaptchaStrategyImpl extends CaptchaStrategy {
    private static final Logger LOGGER = Logger.getLogger(SessionCaptchaStrategyImpl.class);

    public SessionCaptchaStrategyImpl(Map<String, CaptchaDescriptor> mapOfCaptchas) {
        super(mapOfCaptchas);
    }

    @Override
    public void initCaptchaValue(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Put new captcha to map");
        request.getSession().setAttribute(Const.CAPTCHA_ANSWER_PARAMETER, writeInitializedValueToMap());
    }

    @Override
    public Captcha writeValueToCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String number = (String) request.getSession().getAttribute(Const.CAPTCHA_ANSWER_PARAMETER);
        request.getSession().removeAttribute(Const.CAPTCHA_ANSWER_PARAMETER);
        LOGGER.debug("Calling captcha creation");
        return createCaptcha(number);
    }
}
