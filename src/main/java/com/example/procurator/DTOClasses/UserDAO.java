package com.example.procurator.DTOClasses;

import com.example.procurator.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDAO {

    private Long id;

    private String name;

    private Integer age;

    private String address;

    private String phone;

    private String email;

    private String password;

    private Date creationDate;

    private Role role;
}
