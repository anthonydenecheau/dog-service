package com.scc.enci;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicates;
import com.scc.enci.utils.UserContextFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableSwagger2
public class Application extends WebMvcConfigurationSupport {

    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }
    
    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
    
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.scc.enci.controllers"))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    private ApiInfo apiInfo() {
    	
    	String detailDescription = "The Enci Microservice is a RESTful API that provides information for Enci Show . \n \n" 
	    		+"Below is a list of available REST API calls for enci site resources.";

        return new ApiInfoBuilder()
        	.title("Overview")
		    .description(detailDescription)
		    .termsOfServiceUrl("[here is url]")
		    .contact("Centrale Canine")
		    .license("Apache 2.0")
		    .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
		    .version("1.0")
		    .build();        
    }
    
    
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
}
