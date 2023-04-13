package com.example.procurator.controller;

import com.example.procurator.DTOClasses.GameDTO;
import com.example.procurator.model.Game;
import com.example.procurator.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class GameController {
    private final GameService gameService;

    @GetMapping("/findByCollectiveId/{collectiveId}")
    public ResponseEntity<List<Game>> getGamesByCollectiveId(@PathVariable int collectiveId){
        return ResponseEntity.ok().body(gameService.getGamesByCollectiveId(collectiveId));
    }
    @PostMapping("/")
    public ResponseEntity<Game> setGame(@RequestBody GameDTO gameDTO){
        return ResponseEntity.ok(gameService.setGame(gameDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Game> updateGame(@RequestBody GameDTO gameDTO){
        return ResponseEntity.ok().body(gameService.updateGame(gameDTO));
    }

    @DeleteMapping("/{idGame}")
    public ResponseEntity<Game> deleteGame(@PathVariable int idGame){
        return ResponseEntity.ok().body(gameService.deleteGame(idGame));
    }

}
