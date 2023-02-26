package com.epam.storozhuk.captcha.strategy;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.captcha.strategy.impl.CookieCaptchaStrategyImpl;
import com.epam.storozhuk.captcha.strategy.impl.FieldsCaptchaStrategyImpl;
import com.epam.storozhuk.captcha.strategy.impl.SessionCaptchaStrategyImpl;
import com.epam.storozhuk.constant.Const;

import java.util.HashMap;
import java.util.Map;

public class CaptchaStrategiesContainer {
    private final Map<String, CaptchaStrategy> commands;

    public CaptchaStrategiesContainer(Map<String, CaptchaDescriptor> mapOfCaptchas) {
        commands = new HashMap<>();
        commands.put(Const.SESSION_CAPTCHA, new SessionCaptchaStrategyImpl(mapOfCaptchas));
        commands.put(Const.COOKIE_CAPTCHA, new CookieCaptchaStrategyImpl(mapOfCaptchas));
        commands.put(Const.FIELDS_CAPTCHA, new FieldsCaptchaStrategyImpl(mapOfCaptchas));
    }

    public CaptchaStrategy getStrategy(String key) {
        if (commands.containsKey(key)) {
            return commands.get(key);
        }
        throw new IllegalArgumentException("Wrong key value");
    }
}
