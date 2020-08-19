package com.mc.bzly.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	HandlerInterceptor localInterceptor() {
        return new GlobalInperceptor();
    }
	@Bean
	HandlerInterceptor AntiTheftInterceptor() {
		return new AntiTheftInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 其他接口拦截器
		registry.addInterceptor(localInterceptor())
			.excludePathPatterns("/userInfo/loginByPassword",
								"/userInfo/loginBySms",
								"/userInfo/wechatLogin",
								"/userInfo/reg",
								"/userInfo/regH5"
								,"/tpGame/xwCallback");
		
		// 防盗机制拦截器
		/*
		 * registry.addInterceptor(AntiTheftInterceptor())
		 * .addPathPatterns("/userInfo/loginByPassword", "/userInfo/loginBySms",
		 * "/userInfo/reg", "/userInfo/regH5");
		 */
		
		super.addInterceptors(registry);
	}
}
