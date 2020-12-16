package com.blogging.PJBlogging;

import com.blogging.PJBlogging.config.SwaggerConfiguration;
import com.blogging.PJBlogging.controller.AuthController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
//@ComponentScan(basePackageClasses = AuthController.class)
public class PjBloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjBloggingApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("*").allowedHeaders("*").exposedHeaders("Authorization")
						.allowCredentials(true);
			}
		};
	}
}
