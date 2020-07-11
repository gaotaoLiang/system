package com.example.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 由于shiro使用redirect进行url的跳转，在遇到前端界面与后台业务处理不在一个域名下时，
 * 会遇到跨域问题，我们在使用springBoot时，一般会增加一个configuration类的问题允许进行跨跨请求
 * https://blog.csdn.net/fycghy0803/article/details/90700328
 * @author: 老骨头（lgt）
 * @date: 2020/2/11
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }
}
