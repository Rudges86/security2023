package com.security.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreattinsConroller {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return  ResponseEntity.ok("Hello api");
    }

    @GetMapping("/say-good-bye")
    public ResponseEntity<String> sayGoodBye(){
        return ResponseEntity.ok("Goodbye abd See you later");
    }
}
