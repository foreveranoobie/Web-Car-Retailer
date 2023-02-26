package com.epam.storozhuk.filters;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.servlet.response.gzip.wrapper.GZIPServletResponse;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseCompressorFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(ResponseCompressorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (response.getContentType() != null) {
            LOGGER.debug("Content-type is not empty. Checking for text parameter");
            if (response.getContentType().contains("text")
                    && isAcceptedGZipEncoding(request.getHeader(Const.RESPONSE_HEADER_ENCODING))) {
                GZIPServletResponse gzipServletResponse = new GZIPServletResponse(response);
                gzipServletResponse.setCharacterEncoding(Const.UTF_ENCODING);
                filterChain.doFilter(request, gzipServletResponse);
                gzipServletResponse.close();
                return;
            }
        }
        servletResponse.setCharacterEncoding(Const.UTF_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean isAcceptedGZipEncoding(String header) {
        return header != null && header.contains(Const.GZIP);
    }
}
