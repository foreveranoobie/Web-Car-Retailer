package com.epam.storozhuk.servlet.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Locale;

public class WrappedServletRequest extends HttpServletRequestWrapper {
    private Locale locale;

    public WrappedServletRequest(HttpServletRequest request) {
        super(request);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
