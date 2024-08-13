package com.finalProject.questionAndAnswer.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;


/**
 *   make api to restAPI
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    /**
     *      this bean for refresh token
     *      JwtAuthenticationProvider check authentication  with refresh token
     *
     */
    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder") JwtDecoder refreshTokenJwtDecoder) {
        return new JwtAuthenticationProvider(refreshTokenJwtDecoder);
    }


    /**
     *      DaoAuthenticationProvider is a bean use for authentication with dao layer
     *      We DaoAuthenticationProvider when we need implement security with database
     *      dao : Data Access Object
     *      this bean work with user from database
     *      DaoAuthenticationProvider check authentication with userName and password
     *
     */
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }


    /**
     *      config security create bean securityFilterChain
     *      @return HttpSecurity
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception {

        /**
         *       required
         *       follow by rule RESTAPI
         *       make stateless session
         *       once session is stateless all endpoints no security anymore
         */
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /**
         *      required disable CSRF token for post data from client to server
         */
        http.csrf(AbstractHttpConfigurer::disable);


        /**
         *   required
         *   security mechanism
         *   explain: once we set spring security use JWT .
         *   it will required bean JwtDecoder for decode JWT Token
         */
        http.oauth2ResourceServer(jwt -> jwt
                .jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder))
        );


        /**
         *      required
         *      add security to all endpoints
         */
        http.authorizeHttpRequests(endpoint -> endpoint
                        .requestMatchers("/api/v1/auth/**","/upload/**","/api/v1/**","/images/**").permitAll()

//                        .requestMatchers("/api/v1/**").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN") // SCOPE_  is default prefix and required when use jwt
//                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
//                        .requestMatchers(HttpMethod.GET ,"/api/v1").authenticated()
                        .anyRequest().authenticated()
        );


        return http.build();
    }
}
