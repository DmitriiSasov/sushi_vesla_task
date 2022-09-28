package com.example.proj.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    public static final String ARTICLE_CONTROLLER = "Article controller";

    public static final String CATEGORY_CONTROLLER = "Category controller";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.proj.controllers"))
                .paths(PathSelectors.any())
                .build()
                .tags(
                        new Tag(ARTICLE_CONTROLLER, "Обработчик обращений к статьям"),
                        new Tag(CATEGORY_CONTROLLER, "Обработчик обращений к категориям")
                        )
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Blog API").version("1.0.0").build();
    }
}
