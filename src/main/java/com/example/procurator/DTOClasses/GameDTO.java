package com.example.procurator.DTOClasses;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private int collectiveId;

    private LocalDateTime dateMatch;

    private int whiteScore;

    private int blackScore;

    private int id;

}
