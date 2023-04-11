package com.example.procurator.service;

import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public User getUserByEmail(String email){
        return repository.findByEmail(email).orElseThrow();
    }

    public User saveUser (User user){
       return repository.save(user);
    }
    public boolean ifUserExistByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public boolean checkIfUserDontExist(User user) {

        boolean existByEmail =  repository.findByEmail(user.getEmail()).isPresent();
        if(existByEmail){
            throw new AlreadyExistException("The user with this email already exists.");
        }

        boolean existByPhone =  repository.findByPhone(user.getPhone()).isPresent();
        if ( existByPhone) {
            throw new AlreadyExistException("The user with this phone already exists.");
        }

        return true;
    }




}
