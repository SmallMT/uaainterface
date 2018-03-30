package com.bov.mt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class UAAInterfaceApplication {
    public static void main(String[] args){
        SpringApplication.run(UAAInterfaceApplication.class,args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/opt/uaainterface/temp");
        return factory.createMultipartConfig();
    }
}
