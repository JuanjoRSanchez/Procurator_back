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

   /* @OneToOne
    private final Role role = new Role(ERole.PLAYER);
*/
    @OneToOne
    private User user;

    @OneToOne
    private Collective collective;

}
