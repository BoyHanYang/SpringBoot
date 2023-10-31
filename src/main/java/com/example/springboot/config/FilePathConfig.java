package com.example.springboot.config;

import com.example.springboot.util.Constant;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yangbohan
 * @Date 2023/10/26 19:59
 */

public class FilePathConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constant.UPLOAD_PATH);
    }
}
