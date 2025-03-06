package com.example.beforesecurity;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@Component
//@Order(2)
//@WebFilter(urlPatterns = "/*")
public class SMFilter implements Filter{

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        log.info("SMFilter init()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("SMFilter doFilter() 실행 전!!" + Thread.currentThread().getName());
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("SMFilter doFilter() 실행 후!!" + Thread.currentThread().getName());
    }

    @Override
    public void destroy() {
        log.info("SMFilter destroy()");
    }
}