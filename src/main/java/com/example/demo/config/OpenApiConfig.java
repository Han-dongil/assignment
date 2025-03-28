package com.example.demo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

public class OpenApiConfig {

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("전체 API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi tutorApi() {
        return GroupedOpenApi.builder() 
                .group("tutor")
                .pathsToMatch("/api/v1/tutor/**")
                .build(); 
    }
    
    @Bean 
    public GroupedOpenApi studentApi() { 
    	return GroupedOpenApi.builder()
    			.group("student")
    			.pathsToMatch("/api/v1/student/**") 
    			.build(); 
    }
 
	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
		Info info = new Info().title("타이틀 입력").version(springdocVersion).description("API에 대한 설명 부분");
		SecurityScheme auth = new SecurityScheme()
			      .type(SecurityScheme.Type.APIKEY)
			      .in(SecurityScheme.In.COOKIE).name("JSESSIONID");
		SecurityRequirement securityRequirement = new SecurityRequirement()
				.addList("basicAuth");
		return new OpenAPI()
				.info(info)
				.components(new Components().addSecuritySchemes("basicAuth", auth))
			    .addSecurityItem(securityRequirement);
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/swagger-ui/index.html").addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
////		registry.addResourceHandler("/upload/**").addResourceLocations("file:///" + path).setCachePeriod(3600);
//	}
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//        				.allowedOrigins("*");
//
//	}
}