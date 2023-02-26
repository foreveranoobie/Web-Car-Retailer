package com.epam.storozhuk.captcha.cleaner;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import org.apache.log4j.Logger;

import java.util.Map;

public class Cleaner implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Cleaner.class);
    private long captchaLifeTime;
    private Map<String, CaptchaDescriptor> captchaCodes;

    public Cleaner(long captchaLifeTime, Map<String, CaptchaDescriptor> captchaMap) {
        this.captchaLifeTime = captchaLifeTime;
        captchaCodes = captchaMap;
    }

    @Override
    public void run() {
        long creationTime;
        for (String key : captchaCodes.keySet()) {
            creationTime = captchaCodes.get(key).getCreationTime();
            if ((System.currentTimeMillis() - creationTime) / 1000 > captchaLifeTime) {
                LOGGER.debug("Removing key:" + key);
                captchaCodes.remove(key);
            }
        }
    }
}
