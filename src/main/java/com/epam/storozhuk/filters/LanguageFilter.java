package com.epam.storozhuk.filters;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.servlet.request.WrappedServletRequest;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LanguageFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LanguageFilter.class);
    private String defaultLocale;
    private List<String> locales;
    private String storageType;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter(Const.DEFAULT_LANGUAGE);
        locales = Arrays.asList(filterConfig.getInitParameter(Const.LANGUAGE_TYPES).split(","));
        locales.removeIf(loc -> !checkLocale(loc));
        filterConfig.getServletContext().setAttribute(Const.LOCALES_PARAM, locales);
        storageType = filterConfig.getInitParameter(Const.LOCALES_STORAGE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        WrappedServletRequest wrappedRequest = new WrappedServletRequest(request);
        String switchLanguage = request.getParameter(Const.LANG_PARAM);
        String language = null;
        if (storageType.equals("Cookie")) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(Const.LOCALE_PARAM)) {
                        language = cookie.getValue();
                        cookie.setMaxAge(0);
                        break;
                    }
                }
            }
        } else {
            language = (String) request.getSession().getAttribute(Const.LOCALE_PARAM);
        }
        boolean foundLanguage = false;
        if (switchLanguage != null && language != null) {
            if (language.equals(switchLanguage)) {
                foundLanguage = true;
            } else {
                if (checkLocale(switchLanguage)) {
                    foundLanguage = true;
                } else {
                    switchLanguage = setBrowserLocale(wrappedRequest.getLocales());
                    foundLanguage = true;
                }
            }
        }
        if (!foundLanguage && switchLanguage != null) {
            if (checkLocale(switchLanguage)) {
                foundLanguage = true;
            }
            if (!foundLanguage) {
                switchLanguage = setBrowserLocale(wrappedRequest.getLocales());
                foundLanguage = true;
            }
        }
        if (!foundLanguage && language != null) {
            switchLanguage = language;
        }
        if (!foundLanguage && language == null) {
            switchLanguage = setBrowserLocale(wrappedRequest.getLocales());
        }
        if (storageType.equals("Cookie")) {
            setLocaleToCookie(response, switchLanguage,
                    Integer.parseInt(request.getServletContext().getInitParameter(Const.COOKIE_LIFETIME)));
        } else {
            setLocaleToSession(request, switchLanguage);
        }
        wrappedRequest.setLocale(new Locale(switchLanguage));
        filterChain.doFilter(wrappedRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String setBrowserLocale(Enumeration<Locale> localesEnum) {
        String result = "";
        boolean foundLanguage = false;
        while (localesEnum.hasMoreElements()) {
            result = localesEnum.nextElement().getISO3Language();
            if (checkLocale(result)) {
                foundLanguage = true;
                LOGGER.debug("Found browser's and server's compatible languages");
                break;
            }
        }
        if (!foundLanguage) {
            LOGGER.debug("Not found any compatible language. Setting default value to " + defaultLocale);
            result = defaultLocale;
        }
        return result;
    }

    private void setLocaleToCookie(HttpServletResponse response, String locale, int lifetime) {
        Cookie cookie = new Cookie(Const.LOCALE_PARAM, locale);
        cookie.setMaxAge(lifetime);
        response.addCookie(cookie);
    }

    private void setLocaleToSession(HttpServletRequest request, String locale) {
        request.getSession().setAttribute(Const.LOCALE_PARAM, locale);
    }

    private boolean checkLocale(String language) {
        return locales.contains(language);
    }
}
