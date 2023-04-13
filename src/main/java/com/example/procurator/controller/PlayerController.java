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
@RequestMapping(path ="/api/v1/player")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class PlayerController {

    private final UserService service;

    private final PlayerService playerService;

    @GetMapping("/collective/{idCollective}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersByCollectiveId(@PathVariable int idCollective){

        return ResponseEntity.ok(playerService.getPlayersByCollectiveId(idCollective));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersGame(@PathVariable String gameId){

        return ResponseEntity.ok(playerService.getPlayersByGame(gameId)) ;
    }

    @GetMapping("/findAddedGame/{gameId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersAddedToGame(@PathVariable String gameId){

        return ResponseEntity.ok(playerService.getPlayersAddedToGame(gameId));
    }
    @GetMapping("/findNotAddedToGame/{gameId}/{collectiveId}")
    public ResponseEntity<List<PlayerGameDTO>> getPlayersNotAddedToGame(@PathVariable("gameId") String gameId, @PathVariable("collectiveId") String collectiveId){
        List<PlayerGameDTO> listPlayers = playerService.getPlayersNotAddedToGame(gameId, collectiveId);
        return ResponseEntity.ok(listPlayers);
    }
    /*
    @PostMapping("/addPlayerToCollective")
    public ResponseEntity<User> addPlayerToCollective(@RequestBody PlayerToCollective playerToCollective){

        return ResponseEntity.ok(playerService.addPlayerToCollective(playerToCollective));
    }
     */
    @PostMapping("/collective")
    public ResponseEntity<User> addPlayerToCollective(@RequestBody PlayerToCollective playerToCollective){

        return ResponseEntity.ok(playerService.addPlayerToCollective(playerToCollective));
    }
    @PostMapping("/game")
    public ResponseEntity<Game_Player> addPlayerToGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.addPlayerToGame(playerToGameDTO));
    }

    @PostMapping("/confirmInGame")
    public ResponseEntity<Game_Player> toConfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.toConfirmPlayerInGame(playerToGameDTO));
    }
    @PostMapping("/disconfirmInGame")
    public ResponseEntity<User> toDisconfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){

        return ResponseEntity.ok(playerService.toDisconfirmPlayerInGame(playerToGameDTO));
    }

    @PutMapping("/")
    public ResponseEntity<User> updatePlayer(@RequestBody Player player){

        return  ResponseEntity.ok(playerService.updatePlayer(player));
    }

    @DeleteMapping("/takeOutPlayerFromGame/{gameId}/{playerId}")
    public ResponseEntity<HttpStatus> takeOutPlayerFromGame(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId){

        return  ResponseEntity.ok(playerService.deletePlayerFromGame(gameId, playerId));
    }

    @DeleteMapping("/{idPlayer}")
    public ResponseEntity<User> deletePlayerFromCollectiveById(@PathVariable int idPlayer){

        return ResponseEntity.ok(playerService.deletePlayerFromCollective(idPlayer));
    }

}
