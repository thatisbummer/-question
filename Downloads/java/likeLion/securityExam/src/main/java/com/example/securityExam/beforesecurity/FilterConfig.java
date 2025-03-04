package com.example.securityExam.beforesecurity;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<SMFilter> SMFilter(){
        FilterRegistrationBean<SMFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SMFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}