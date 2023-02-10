package com.security.security.controllers;

import com.security.security.config.AuthToken;
import com.security.security.config.TokenUtil;
import com.security.security.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

@RestController
public class AuthController {
    @GetMapping("/free")
    public String livre(){
        return "Acesso livre";
    }

    @GetMapping("/privado")
    public String privado(){
        return "Acesso autenticado";
    }


    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody Usuario user){
        //Verifica e ele tem que retornar um token
        System.out.println(user.getLogin());
        System.out.println(user.getSenha());
        if(user.getLogin().equals("Rudge") && user.getSenha().equals("Rudge123") ){
            //codificando o token
            return ResponseEntity.ok(TokenUtil.encodeToken(user));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
