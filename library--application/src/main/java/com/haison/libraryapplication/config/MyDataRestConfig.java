package com.haison.libraryapplication.config;

import com.haison.libraryapplication.entity.Book;
import com.haison.libraryapplication.entity.Message;
import com.haison.libraryapplication.entity.Review;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Close All HTTP request "except" GET
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer, WebMvcConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnSupportActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE};

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);

        disableHttpMethods(Book.class,config,theUnSupportActions);
        disableHttpMethods(Review.class,config, theUnSupportActions);
        disableHttpMethods(Message.class,config, theUnSupportActions);

        // Config CORS Mapping
        String theAllowedOrigins = "https://localhost:9000";
        cors.addMapping(config.getBasePath() + "/**")
            .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClassConfig,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnSupportActions) {
        config.getExposureConfiguration()
                .forDomainType(theClassConfig)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportActions));
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**");
//    }

}
