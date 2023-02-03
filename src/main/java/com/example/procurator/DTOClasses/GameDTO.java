package com.example.procurator.DTOClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private int gameId;

    private int whiteScore;

    private int blackScore;

    private int collectiveId;

    private String collectiveGame;

    private LocalDateTime dateMatch;

    private int userId;

}
