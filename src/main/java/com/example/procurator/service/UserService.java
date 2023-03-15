package com.example.procurator.service;

import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository repository;
 /*   public void saveUserDAO(UserDAO userDAO) {
        User.builder()
                .name(userDAO.getName())
                .email(userDAO.getEmail())
                .name(userDAO.getName())
                .age(userDAO.getAge())
                .address(userDAO.getAddress())
                .phone(userDAO.getPhone())
                .password(userDAO.getPassword())
                .roles(userDAO.getRole())
                .build();

    }
*/

    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public User getUserByEmail(String email){
        return repository.findByEmail(email).orElseThrow();
    }

    public User getUserByName(String name){
        return repository.findByName(name).orElseThrow();
    }

    public User getUserByPassword(String pass){
        return repository.findByPassword(pass).orElseThrow();
    }

    public void saveUser (User user){
        repository.save(user);
    }
    public boolean ifUserExistByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public boolean checkIfUserExist(User user) {
        boolean existByEmail =  repository.findByEmail(user.getEmail()).isPresent();
        boolean existByPhone =  repository.findByPhone(user.getPhone()).isPresent();
        if(existByEmail)
            throw new AlreadyExistException("The user with this email already exists.");
        else if ( existByPhone) {
            throw new AlreadyExistException("The user with this phone already exists.");
        }
        return true;
    }

    // Services for User wich role is PLAYER
    public User getUserIfPlayerByGame(int gamEId){
        return null;
    }


}
