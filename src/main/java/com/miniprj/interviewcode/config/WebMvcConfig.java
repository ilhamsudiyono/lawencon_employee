package com.miniprj.interviewcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;
    
    private final String baseUrl = "";
    
  

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
  
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
      registry.
          addResourceHandler(baseUrl + "/swagger-ui/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
          .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController(baseUrl + "/swagger-ui/")
          .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }
   
}
