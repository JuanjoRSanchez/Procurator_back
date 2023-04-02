package com.example.procurator.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "collective")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @OneToOne
    private User user;

    @Column
    private String name;

    @OneToMany(mappedBy = "collective")
    private Set<User> users;
}
