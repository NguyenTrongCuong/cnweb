package com.example.demo.infrastructure.configuration;

import com.example.demo.application.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfiguration {

    @Autowired
    @Qualifier("authFilter")
    private Filter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter((AuthFilter) this.authFilter);
        registrationBean.addUrlPatterns(
                "/logout", "/add_post", "/delete_post", "/edit_post", "/report_post", "/like", "/get_list_posts",
                "/set_comment", "/edit_comment", "/get_comment", "/check_new_item", "/get_list_posts_v2", "/get_post"
        );
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
