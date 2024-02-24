package com.ssgroup.zelu.config;

import com.ssgroup.zelu.annotation.RequestPageResolver;
import com.ssgroup.zelu.annotation.RequestTokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ParameterConfig implements WebMvcConfigurer {

    @Autowired
    private RequestTokenResolver requestUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(requestUserResolver);
        argumentResolvers.add(new RequestPageResolver());
    }
}
