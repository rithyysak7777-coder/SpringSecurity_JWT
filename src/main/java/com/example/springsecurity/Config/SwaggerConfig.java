package com.example.springsecurity.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

// that can use for thirty( pligin from out to use )
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Login and Register with Jwt ",
                version = "1.0",
                description = "JWT Authtication Demo"
        )
        // Security
)

public class SwaggerConfig {
}
