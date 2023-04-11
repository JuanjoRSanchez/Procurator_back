package com.example.procurator.auth;

import com.example.procurator.config.JwtService;
import com.example.procurator.model.RegisterRequest;
import com.example.procurator.model.Role;
import com.example.procurator.model.User;
import com.example.procurator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserService service;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;
 /*   public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRole())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .age(request.getAge())
                .creationDate(new Timestamp(new Date().getTime()))
                .build();
        boolean checkIfExists = service.checkIfUserExist(user);
        if(checkIfExists) {
            service.saveUser(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else{
            return AuthenticationResponse.builder()
                    .build();
        }
    }*/

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .age(request.getAge())
                .creationDate(new Timestamp(new Date().getTime()))
                .build();
        boolean checkIfExists = service.checkIfUserDontExist(user);
        if(checkIfExists) {
            service.saveUser(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .userName(user.getName())
                    .status(HttpStatus.OK)
                    .token(jwtToken)
                    .build();
        }else{
            return AuthenticationResponse.builder()
                    .build();
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = service.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userName(user.getName())
                .status(HttpStatus.OK)
                .build();
    }

    public boolean passwordValidates( String pass ) {
        return  pass.length() > 6  ;
        /*
        if(pass.matches("salida = \"The user actually exist in the data base\";")){
           return true;
       }
         */
        /*
        int count = 0;

        if( 8 <= pass.length() && pass.length() <= 32  )
        {
            if( pass.matches(".*\\d.*") )
                count ++;
            if( pass.matches(".*[a-z].*") )
                count ++;
            if( pass.matches(".*[A-Z].*") )
                count ++;
            if( pass.matches(".*[*.!@#$%^&(){}[]:";'<>,.?/~`_+-=|\\].*") ))
            count ++;
        }

        return count >= 3;
        */
    } // TODO

    public boolean roleValidates(String role ) {
        if(role.equals(Role.ADMIN.name()) || role.equals(Role.MANAGER.name()) || role.equals(Role.PLAYER.name())){
            return true;
        }
        return false;
    } // TODO


}

