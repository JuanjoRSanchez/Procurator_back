package com.example.procurator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/players")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class UserPlayerController {



}
