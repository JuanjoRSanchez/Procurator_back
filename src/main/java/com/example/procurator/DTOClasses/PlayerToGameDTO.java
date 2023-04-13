package com.example.procurator.DTOClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerToGameDTO {

    private int player_id;

    private int game_id;

    private String collective_id;

    private boolean addedToGame;

    private boolean confirmed;
}
