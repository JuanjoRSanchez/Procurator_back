package com.example.procurator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int whiteScore;

    @Column
    private int blackScore;

    @OneToOne
    private Collective collective;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateMatch;

    @OneToMany(mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Game_Player> game_players = new HashSet<>();




}
