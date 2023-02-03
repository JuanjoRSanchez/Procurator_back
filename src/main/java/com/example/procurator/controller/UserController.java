package com.example.procurator.controller;

import com.example.procurator.Repository.UserRepository;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")

public class UserController {

    private final UserRepository repository;

    /*
    @GetMapping("/{userName}")
    public User getUserByName(@PathVariable String userName){
        return repository.findByName(userName).orElseThrow();
    }*/

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable String userName){
        return ResponseEntity.ok(repository.findByName(userName).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<User> getUser(){
        return ResponseEntity.ok(repository.findByEmail("javi@gmail.com").orElseThrow());
    }
}
