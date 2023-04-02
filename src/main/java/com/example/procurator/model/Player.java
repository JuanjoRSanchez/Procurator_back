package com.example.procurator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*@Entity
@Table(name = "_player")*/
public class Player extends User{

    @Enumerated(EnumType.STRING)
    private  Role role;

    @OneToOne
    private Collective collective;

}
