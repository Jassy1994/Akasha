package com.example.Configuration;

import com.example.Interceptor.LoginRequiredInterceptor;
import com.example.Interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Jassy on 2017/3/13.
 * description:
 */
@Component
public class ZixunConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("user/*");
        super.addInterceptors(registry);
    }
}
