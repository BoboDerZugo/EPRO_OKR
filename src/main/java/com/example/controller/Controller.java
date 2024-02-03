package com.example.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class Controller {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld(){
        System.out.println("Hello World!");
        return "Hello World";
    }

    @PostMapping
    public ResponseEntity<String> helloWorldPost(){
        System.out.println("j");
        return  ResponseEntity.ok("Hello World");
    }

    
}
