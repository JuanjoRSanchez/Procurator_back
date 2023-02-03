package com.example.procurator.controller;

import com.example.procurator.Repository.UserRepository;
import com.example.procurator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
@RequestMapping("/api/v1")
public class firstController {


    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private  final UserRepository repository;

    @GetMapping("/greeting")
    public String greeting(){
         return "Hello Pelacañas";
    }

    @GetMapping("/todos")
    public List<Object> todos(){
        return Arrays.stream(repository.findAll().toArray()).toList();
    }

}