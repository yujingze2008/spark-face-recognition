package com.gitee.code4fun.facerecognition.gui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @author yujingze
 * @data 18/3/27
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${local.images.ori.path}")
    private String oriPath;

    @Value("${local.images.face.path}")
    private String facePath;

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize("2MB");
        //设置总上传数据总大小
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/ori/**").addResourceLocations("file:"+oriPath);
        registry.addResourceHandler("/face/**").addResourceLocations("file:"+facePath);
    }
}
