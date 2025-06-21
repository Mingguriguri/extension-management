package com.madrascheck.extension_management.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI extensionManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Extension Management API")
                        .version("v1.0")
                        .description("파일 확장자 관리용 RESTful API 문서")
                        .contact(new Contact()
                                .name("김민정")
                                .email("minbory925@gmail.com")
                                .url("https://minsllogg.tistory.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/Mingguriguri/extension-management"));
    }
}
