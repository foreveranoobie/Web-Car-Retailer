package com.epam.storozhuk.util;

import nl.captcha.Captcha;
import nl.captcha.text.producer.NumbersAnswerProducer;

public class CaptchaUtil {
    public static Captcha createCaptcha(String number) {
        if (number == null) {
            return new Captcha.Builder(120, 50).addText(new NumbersAnswerProducer(6)).build();
        }
        return new Captcha.Builder(120, 50).addText(() -> number).build();
    }

    public static boolean captchaIsInTime(long startTime, long maximumRange) {
        long currentTime = System.currentTimeMillis();
        return currentTime - startTime <= (maximumRange * 1000);
    }
}
