package com.qualitychemicals.qciss.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class Swagger2Config extends WebMvcConfigurationSupport {
    @Bean
    public Docket QCiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qualitychemicals.qciss"))
                .paths(PathSelectors.any())//PathSelectors.ant("/api/*")
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("QCiSS API")
                .description("QCi Staff Sacco main app API")
                .termsOfServiceUrl("https://www.ruraaratechempire.com")
                .contact(new Contact("Ruraara Tech Empire","https://www.ruraaratechempire.com","service@ruraara.com"))
                .license("A license given")
                .licenseUrl("https://www.ruraaratechempire.com")
                .version("1.0")
                .build();
    }

}
