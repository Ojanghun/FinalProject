package com.smhrd.config;

import com.smhrd.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**") // /admin/dashboard 등 모두 보호
                .excludePathPatterns("/adminLogin.do", "/adminLoginCheck"); // 로그인 관련은 제외
    }
}
