package com.epam.storozhuk.captcha.strategy;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.util.CaptchaUtil;
import com.epam.storozhuk.util.HashUtil;
import nl.captcha.Captcha;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

public abstract class CaptchaStrategy {
    private static final Logger LOGGER = Logger.getLogger(CaptchaStrategy.class);
    protected Map<String, CaptchaDescriptor> mapOfCaptchas;

    public CaptchaStrategy(Map<String, CaptchaDescriptor> mapOfCaptchas) {
        this.mapOfCaptchas = mapOfCaptchas;
    }

    public abstract void initCaptchaValue(HttpServletRequest request, HttpServletResponse response);

    public abstract Captcha writeValueToCaptcha(HttpServletRequest request, HttpServletResponse response);

    public boolean isValidCaptcha(HttpServletRequest request) {
        String captchaHashedAnswer = HashUtil.codeCaptchaAnswer(request.getParameter(Const.CAPTCHA_PARAMETER));
        if (!mapOfCaptchas.containsKey(captchaHashedAnswer)) {
            LOGGER.debug("Captcha Map doesn't contain user's captcha value");
            request.setAttribute(Const.CAPTCHA_ERROR, Const.CAPTCHA_VALUE_ERROR);
            return false;
        }
        long captchaCreationTime = mapOfCaptchas.get(captchaHashedAnswer).getCreationTime();
        long captchaLifeTime = Long.parseLong(request.getServletContext().getInitParameter(Const.CAPTCHA_LIFETIME_PARAMETER));
        if (!CaptchaUtil.captchaIsInTime(captchaCreationTime, captchaLifeTime)) {
            LOGGER.debug("Captcha is out of date");
            request.setAttribute(Const.CAPTCHA_ERROR, Const.CAPTCHA_TIMEOUT_ERROR);
            return false;
        }
        return true;
    }

    protected String writeInitializedValueToMap() {
        String number = String.valueOf(new Random().nextInt(88888) + 11111);
        String hashedNumber = HashUtil.codeCaptchaAnswer(number);
        mapOfCaptchas.put(hashedNumber, new CaptchaDescriptor(System.currentTimeMillis(), number));
        return hashedNumber;
    }

    protected Captcha createCaptcha(String number) {
        String captchaValue = mapOfCaptchas.get(number).getCodeValue();
        Captcha captcha = CaptchaUtil.createCaptcha(captchaValue);
        LOGGER.debug("Created captcha on value " + captchaValue);
        long captchaStartTime = System.currentTimeMillis();
        mapOfCaptchas.get(number).setCreationTime(captchaStartTime);
        LOGGER.debug("Wrote captcha launch time");
        return captcha;
    }
}
