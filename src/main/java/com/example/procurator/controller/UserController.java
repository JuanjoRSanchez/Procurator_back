package com.example.procurator.controller;

import com.example.procurator.Repository.UserRepository;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path ="/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")

public class UserController {
    private final UserRepository repository;

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable String userName){
        return ResponseEntity.ok(repository.findByName(userName).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<User> getUser(){
        return ResponseEntity.ok(repository.findByEmail("javi@gmail.com").orElseThrow());
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<User> addUserPlayer(@RequestBody User user){
        user.setCreationDate(new Timestamp(new Date().getTime()));
        return ResponseEntity.ok(repository.save(user));
    }
}
