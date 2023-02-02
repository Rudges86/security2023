package com.security.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//Quando criamos essa config, o spring vai sair da segurança padrão para esta que criamos
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests() //Requisições http autorização de requisição
                .anyRequest() //ta permitindo qualquer requisição
                .authenticated()//desde que seja autenticada
                .and()
                .httpBasic();
        return http.build();
    }
}
