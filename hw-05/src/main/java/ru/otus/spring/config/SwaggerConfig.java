package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controller.AuthorController;
import ru.otus.spring.controller.BookController;
import ru.otus.spring.controller.GenreController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(BookController.class.getPackage().getName()))
            .apis(RequestHandlerSelectors.basePackage(AuthorController.class.getPackage().getName()))
            .apis(RequestHandlerSelectors.basePackage(GenreController.class.getPackage().getName()))
            .build();
    }
}
