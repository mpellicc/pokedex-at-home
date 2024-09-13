package com.mpellicc.pokedex.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.title}") String title,
            @Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title(title).version(appVersion));
    }
}
