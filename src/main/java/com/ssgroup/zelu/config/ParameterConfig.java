package com.ssgroup.zelu.config;

import com.ssgroup.zelu.annotation.RequestPageResolver;
import com.ssgroup.zelu.annotation.RequestUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ParameterConfig implements WebMvcConfigurer {

    @Autowired
    private RequestUserResolver requestUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(requestUserResolver);
        argumentResolvers.add(new RequestPageResolver());
    }
}
