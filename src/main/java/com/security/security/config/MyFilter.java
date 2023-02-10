package com.security.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//meu filtro para barrar ou liberar as requisições
//E antes de chegar nele eu posso ter vários filtros
public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, //requisição
            HttpServletResponse response, //resposta
            FilterChain filterChain //filtro
    ) throws ServletException, IOException {
        //Em um primeiro momento o que é preciso fazer?
        //É preciso que o filtro valide se a requisição tem um cabeçalho, coloque ela na requisição
        //e encaminhe ela

        //Verifica se o cabeçalho de requisição existe
        if(request.getHeader("Authorization") != null){

            //Aqui eu passo o token de autenticação que é verificado lá na classe TokenUtil que criei
            Authentication  auth =  TokenUtil.decodeToken(request);
            System.out.println(auth);
            if(auth != null){
                //Verifico se ele for válido, eu passo a requisição para frente, indicando que ela esta autenticada
                //** Aqui temos que passar a variável auth que foi criada, para liberar a autenticação, antes não estava
                //autênticando porque eu tinha setado null.
                //Verifica se ele é válido
                SecurityContextHolder.getContext().setAuthentication(auth);
            }else{
                //Verifica se o token é inválido e customiza a mensagem de erro
                System.out.println("Erro no token");
                ErroDTO erroDTO = new ErroDTO(401,"Usuário não autorizado");
                response.setStatus(erroDTO.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(erroDTO));
                response.getWriter().flush();
                return;
            }
        }

        //passa a requisição para frente, encaminha
        System.out.printf("Debug Requisição passou pelo filtro");
        filterChain.doFilter(request,response);
    }
}
