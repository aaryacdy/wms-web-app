package com.kathford.wms.config;

/*import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My API",
                description = "My API description",
                version = "1.0.0",
                contact = @Contact(name = "John Doe", email = "aarya@example.com")
        ),
        servers = @Server(url = "http://localhost:8080")
        //security = @SecurityRequirement(name = "basicAuth"),
        //securitySchemes = @SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
)*/
public class SwaggerConfig {

    /*@Bean
    public GroupedOpenApi myApi() {
        System.out.println(" I am API inside ");
        return GroupedOpenApi.builder()
                .group("my-api")
                .pathsToMatch("/api/**")
                .build();
    }*/
}
