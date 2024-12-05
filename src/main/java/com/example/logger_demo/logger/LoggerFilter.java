package com.example.logger_demo.logger;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author niffler on 24/09/24
 * @project logger-demo
 */
@Component
@Order(Integer.MIN_VALUE)
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String url = "URL : Unable to Parse ..!!";
        if (servletRequest instanceof HttpServletRequest) {
            url = ((HttpServletRequest) servletRequest).getServletPath();
        }
        APILogger logger = new APILogger(url, servletRequest.getRequestId());
        APILoggerContexHolder.setLogger(logger);
        filterChain.doFilter(servletRequest, servletResponse);
        APILoggerContexHolder.remove();
    }
}