package kz.spring.restapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openapi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Service")
                        .description("My little API")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Farkhat")
                                .email("farqat@mail.ru"))
                        .termsOfService("TOC")
                        .license(new License().name("Licence").url("#"))
                );
    }
}
