package com.smhrd.config;

import com.smhrd.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new AdminInterceptor())
	            .addPathPatterns("/admin/**", "/admin/refund-session") // ⬅️ 이 경로 포함
	            .excludePathPatterns("/adminLogin.do", "/adminLoginCheck");
	}

}
