package org.winterframework.sso.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.sso.example.filter.WebSsoFilter;

import javax.servlet.Filter;

/**
 * @Author: YHG
 * @Date: 2019/8/18 10:43
 */
@Configuration
public class WebConfig {

    @Value("${winter.sso.server}")
    private String ssoServer;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        WebSsoFilter filter = new WebSsoFilter();
        filter.setSsoServer(ssoServer);
        filter.addExcludedMapping("/test");
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        return registration;

    }
}
