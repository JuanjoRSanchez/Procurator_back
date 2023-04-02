package com.example.procurator.DTOClasses;

import com.example.procurator.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerToCollective {

    private int idCollective;

    private User player;
}
