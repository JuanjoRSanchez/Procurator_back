package com.example.procurator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*@Entity
@Table(name = "_manager")*/
public class Manager extends User {

/*    @OneToOne
    private final Role role = new Role(ERole.MANAGER);*/

    @OneToOne
    private User user;

    @OneToOne
    private Collective collective;
}
