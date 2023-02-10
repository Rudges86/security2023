package com.security.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity //Informa que é uma classe do security
public class SecurityConfig {

    //filtro de passagem
    //Sou obrigado a anotalo com @Bean, porque ele irá retornar um objeto que implementa esta interface
    //pois ai eu consigo utilizar o que ele pede como regra
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() //Ele trata do acesso do usuário e senha, normalmente
                                 // é desabilitado pois o dev que irá tratar esta questão de autenticação dos usuários
                .authorizeHttpRequests() //Agora as autenticações http são passiveis de autênticação
                .requestMatchers(HttpMethod.GET,"/free").permitAll() //Aqui está dizendo que quando houver uma
                //requisição com o methodo get, no endPoint free, ela sera permitida o acesso por qualquer um
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest() //Aqui informa que qualquer outra requisição
                .authenticated() //Deve ser autenticada
                .and() // E
                .cors(); // Sofrerão as restrições do CORS, nele que precisamos fazer a configuração de acesso externo
        //Adicionar o meu filtro, Classe que foi criada usando o doFilter
        //* detalhe importante, esse UsernamePasswordAuthenticationFilter.class é o responsável por gerar aquela tela de
        //login com usuário e senha quando adicionamos o spring security a nossa aplicação
        //É no filtro que fica a lógica de autênticação do token
        http.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
