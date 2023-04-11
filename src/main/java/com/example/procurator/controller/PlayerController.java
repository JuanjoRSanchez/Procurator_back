package com.example.procurator.controller;

import com.example.procurator.DTOClasses.PlayerToCollective;
import com.example.procurator.DTOClasses.PlayerToGameDTO;
import com.example.procurator.DTOClasses.Queryresponse.PlayerGameDTO;
import com.example.procurator.model.*;
import com.example.procurator.service.PlayerService;
import com.example.procurator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path ="/api/v1/players")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class PlayerController {

    private final UserService service;

    private final PlayerService playerService;

    @GetMapping("/getPlayersByCollective/{idCollective}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersByCollectiveId(@PathVariable int idCollective){

        return ResponseEntity.ok(playerService.getPlayersByCollectiveId(idCollective));
    }
    @PostMapping("/addPlayerToCollective")
    public ResponseEntity<User> addPlayerToCollective(@RequestBody PlayerToCollective playerToCollective){

        return ResponseEntity.ok(playerService.addPlayerToCollective(playerToCollective));
    }

    @PostMapping("/addPlayerToGame")
    public ResponseEntity<Game_Player> addPlayerToGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.addPlayerToGame(playerToGameDTO));
    }

    @DeleteMapping("/takeOutPlayerFromGame/{gameId}/{playerId}")
    public ResponseEntity<HttpStatus> takeOutPlayerFromGame(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId){

        return  ResponseEntity.ok(playerService.deletePlayerFromGame(gameId, playerId));
    }

    @GetMapping("/getPlayersForGames/{gameId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersGame(@PathVariable int gameId){

        return ResponseEntity.ok(playerService.getPlayersByGame(gameId)) ;
    }

    @GetMapping("/getPlayersAddedToGames/{gameId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersAddedToGame(@PathVariable int gameId){

        return ResponseEntity.ok(playerService.getPlayersAddedToGame(gameId));
    }
    @GetMapping("/getPlayersNotAddedToGames/{gameId}/{collectiveId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersNotAddedToGame(@PathVariable("gameId") String gameId, @PathVariable("collectiveId") String collectiveId){
        List<PlayerGameDTO> listPlayers = playerService.getPlayersNotAddedToGame(gameId, collectiveId);
        return ResponseEntity.ok(listPlayers);
    }

    @PostMapping("/confirmPlayerInGame")
    public ResponseEntity<Game_Player> toConfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.toConfirmPlayerInGame(playerToGameDTO));
    }
    @PostMapping("/disconfirmPlayerInGame")
    public ResponseEntity<User> toDisconfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.toDisconfirmPlayerInGame(playerToGameDTO));
    }

    @PutMapping("/updatePlayer")
    public ResponseEntity<User> updatePlayer(@RequestBody Player player){

        return  ResponseEntity.ok(playerService.updatePlayer(player));
    }

    @DeleteMapping("/deletePlayer/{idPlayer}")
    public ResponseEntity<User> deletePlayerFromCollectiveById(@PathVariable int idPlayer){

        return ResponseEntity.ok(playerService.deletePlayerFromCollective(idPlayer));
    }

}
