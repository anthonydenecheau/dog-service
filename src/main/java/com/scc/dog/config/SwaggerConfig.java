package com.scc.dog.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

   @Bean
   public Docket dogApi2() {
      return new Docket(DocumentationType.SWAGGER_2).groupName("dogservice-2.0")
            .globalOperationParameters(getGlobalOperationParameters()).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage("com.scc.dog.controllers")).paths(regex("/v2.*"))
            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
            .apis(customRequestHandlers()).paths(PathSelectors.any()).build();
   }

   @Bean
   public Docket dogApi1() {
      return new Docket(DocumentationType.SWAGGER_2).groupName("dogservice-1.0")
            .globalOperationParameters(getGlobalOperationParameters()).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage("com.scc.dog.controllers")).paths(regex("/v1.*"))
            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
            .paths(PathSelectors.any()).build();
   }

   @Override
   protected void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

      registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
   }

   private ApiInfo apiInfo() {

      String detailDescription = "The Dog Microservice is a RESTful API that provides information about dog . \n \n"
            + "Below is a list of available REST API calls for dog resources.";

      return new ApiInfoBuilder().title("Overview").description(detailDescription).termsOfServiceUrl("[here is url]")
            .contact(readContact()).license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0").build();
   }

   private Contact readContact() {
      return new Contact("Centrale Canine", "", "lof.contact@centrale-canine.fr");
   }

   private List<Parameter> getGlobalOperationParameters() {
      List<Parameter> list = new ArrayList<Parameter>();
      Parameter p = new ParameterBuilder().name("X-SCC-authentification").description("Application access key")
            .modelRef(new ModelRef("string")).parameterType("header").required(true).build();

      list.add(p);

      return list;
   }

   // Filter upon method
   private Predicate<RequestHandler> customRequestHandlers() {
      return new Predicate<RequestHandler>() {
         @Override
         public boolean apply(RequestHandler input) {
            Set<RequestMethod> methods = input.supportedMethods();
            return methods.contains(RequestMethod.GET)
            //|| methods.contains(RequestMethod.POST)
            //|| methods.contains(RequestMethod.PUT)
            //|| methods.contains(RequestMethod.DELETE)
            ;
         }
      };
   }

}
