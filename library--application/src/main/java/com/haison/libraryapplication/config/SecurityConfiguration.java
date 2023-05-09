package com.haison.libraryapplication.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {

        // Disable Cross Site Request Forgery
        httpSecurity.csrf().disable();

        // Protected endpoint at /api/<type>/secure
        httpSecurity.authorizeHttpRequests(
//                config -> config.requestMatchers( "/command/books/secure/**").authenticated()
//                        .requestMatchers( "/command/reviews/secure/**").authenticated()
//                        .requestMatchers( "/command/messages/secure/**").authenticated()
//                        .requestMatchers("/api/**").permitAll()
                // Authenticated all path of controller
                config -> config.requestMatchers("command/books/secure/**",
                                                        "command/reviews/secure/**",
                                                        "command/messages/secure/**",
                                                        "command/payments/secure/**").authenticated()
                                .requestMatchers("/api/**").permitAll()// Accept all role(must be login)
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        // Add Cross filter
        httpSecurity.cors();
//
        // Add content negotiation strategy
        httpSecurity.setSharedObject(ContentNegotiationStrategy.class,
                    new HeaderContentNegotiationStrategy()
                );

        // Force a non-empty response body for 401 to make the response friendly
        Okta.configureResourceServer401ResponseBody(httpSecurity);

        return httpSecurity.build();
    }

}
