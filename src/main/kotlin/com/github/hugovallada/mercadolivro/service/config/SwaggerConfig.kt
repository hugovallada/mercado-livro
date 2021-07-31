package com.github.hugovallada.mercadolivro.service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration::class)
class SwaggerConfig {

    @Bean
    fun api() : Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.hugovallada.mercadolivro.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())

    }

    private fun metaData() = ApiInfoBuilder()
            .title("Mercado de Livros")
            .description("API de venda de livros")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .build()

}