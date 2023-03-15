package com.example.procurator.controller;

import com.example.procurator.DTOClasses.GameDTO;
import com.example.procurator.model.Game;
import com.example.procurator.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class GameController {
    private final GameService gameService;

    @GetMapping("/getGames/{collectiveId}")
    public ResponseEntity<List<Game>> getGamesByCollectiveId(@PathVariable int collectiveId){
        return ResponseEntity.ok().body(gameService.getGamesByCollectiveId(collectiveId));
    }
    @PostMapping("/addGame")
    public boolean setGame(@RequestBody GameDTO gameDTO){
        return gameService.setGame(gameDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<Game> updateGame(@RequestBody Game game){

        return ResponseEntity.ok().body(gameService.updateGame(game));
    }

    @DeleteMapping("/deleteGame/{idGame}")
    public ResponseEntity<Game> deleteGame(@PathVariable int idGame){
        return ResponseEntity.ok().body(gameService.deleteGame(idGame));
    }
}
