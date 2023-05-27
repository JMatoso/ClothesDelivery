package com.clothesdelivery.web.configurations;

import com.clothesdelivery.web.helpers.CommonAttributesInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CommonAttributesInterceptor commonAttributesInterceptor;

    public WebMvcConfig(CommonAttributesInterceptor commonAttributesInterceptor) {
        this.commonAttributesInterceptor = commonAttributesInterceptor;
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(commonAttributesInterceptor);
    }
}

