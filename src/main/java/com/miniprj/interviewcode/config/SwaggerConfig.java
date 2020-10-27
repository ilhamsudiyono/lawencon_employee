package com.miniprj.interviewcode.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
//@EnableWebMvc
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		        .paths(PathSelectors.ant("/api/**/*"))
		        .apis(RequestHandlerSelectors.basePackage("com.miniprj.interviewcode"))
		        .build()
		        .apiInfo(apiDetails());
	}
	
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Authorization", ApiKeyVehicle.HEADER,
				"Authorization", ",");
	}
	
	private ApiKey apiKey() {
	    return new ApiKey("Authorization", "Authorization", "header");
	  }

	  private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.ant("/api/**/*"))
	        .build();
	  }

	  List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return new ArrayList<SecurityReference>(){{
	        new SecurityReference("Authorization", authorizationScopes);}};
	  }
	
	
	private ApiInfo apiDetails() {
		return new ApiInfo("Address Test Project API", "Sample API for backend program", "1.0", "Free to use", new Contact("Ilham Zannuary", "https://www.linkedin.com/in/ilham-zannuary-b74177a6/", "ilhamsudiyono29@gmail.com"), "Licence of API",  "API license URL", Collections.emptyList());
	}

	

}
