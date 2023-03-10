package com.example.procurator.DTOClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectiveDTO {

    private String name;

    private String newName;

    private int idCollective;

    private int userId;

    private String email;

}
