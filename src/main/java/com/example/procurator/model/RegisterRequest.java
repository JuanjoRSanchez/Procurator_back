package com.example.procurator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String name;

    private String password;

    private Integer age;

    private String address;

    private String phone;

    private String email;

    private Role role;
}
