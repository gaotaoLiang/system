package com.siec.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: 实现下载文件，使用范围：项目部署和上传的文件需要在同一服务器上
 * @author: 老骨头（lgt）
 * @date: 2020/1/28
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中OTA表示访问的前缀。"file:D:/OTA/"是文件真实的存储路径
        registry.addResourceHandler("/uploadImage/**").addResourceLocations("file:/uploadImage/");
        //registry.addResourceHandler("/uploadImage/**").addResourceLocations("file:D:/uploadImage/");
    }
}