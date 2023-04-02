package com.example.procurator.controller;

import com.example.procurator.DTOClasses.PlayerToCollective;
import com.example.procurator.DTOClasses.PlayerToGameDTO;
import com.example.procurator.DTOClasses.Queryresponse.PlayerGameDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.GameRepository;
import com.example.procurator.Repository.Game_PlayerRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.model.*;
import com.example.procurator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(path ="/api/v1/players")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class PlayerController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final GameRepository gameRepository;

    private final Game_PlayerRepository game_playerRepository;
    private final CollectiveRepository collectiveRepository;
    private final UserRepository userRepository;
    private final UserService service;

    @GetMapping("/getPlayers")
    public List<User> getAllPlayers(){
        List<User>  listPlayers = repository.findAllPlayers();
        return listPlayers;
    }

    @GetMapping("/getPlayersByGame/{game_id}")
    public List<User> getAllPlayersByGame(@PathVariable int game_id){
        List<User>  listPlayers = repository.findPlayersByGameId(Long.valueOf(game_id));
        return listPlayers;
    }
    @GetMapping("/getPlayersByCollective/{idCollective}")
    public List<User> getPlayersByCollective(@PathVariable int idCollective){
        List<User> listPlayers = repository.findPlayersByCollectiveId(Long.valueOf(idCollective));
        return listPlayers;
    }

    @GetMapping("/{idGame}")
    public List<User> getPlayersBygame(@PathVariable int idGame){

        return null;
    }

    @PostMapping("/addPlayerToCollective")
    public ResponseEntity<User> addPlayerToCollective(@RequestBody PlayerToCollective playerToCollective){
        System.out.println("///////////////////////////////////////////////////////////////////////");
        Collective collective = collectiveRepository.findById(Long.valueOf(playerToCollective.getIdCollective())).orElseThrow();
        User player = playerToCollective.getPlayer();
        var user = User.builder()
                .name(player.getName())
                .password(passwordEncoder.encode(player.getPassword()))
                .role(player.getRole())
                .phone(player.getPhone())
                .address(player.getAddress())
                .email(player.getEmail())
                .age(player.getAge())
                .active(player.isActive())
                .creationDate(new Timestamp(new Date().getTime()))
                .collective(collective)
                .build();
        if(userRepository.findByEmail(playerToCollective.getPlayer().getEmail()).isEmpty() && collective != null){
            service.saveUser(user);
        }
        return ResponseEntity.ok(player);
    }
    /*
    @PostMapping("/addPlayerToGame")
    public User addPlayerToGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game game = gameRepository.findById(Long.valueOf(playerToGameDTO.getGame_id())).orElseThrow();
        User user = userRepository.findById(Long.valueOf(playerToGameDTO.getPlayer_id())).orElseThrow();
        System.out.println(user);
        if(game != null && user != null){
            Game_Player game_player = new Game_Player();
            game_player.setGame(game);
            game_player.setUser(user);
            game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
            game_playerRepository.save(game_player);
        }
        return user;
    }
    */

    @PostMapping("/addPlayerToGame")
    public Game_Player addPlayerToGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game game = gameRepository.findById(Long.valueOf(playerToGameDTO.getGame_id())).orElseThrow();
        User user = userRepository.findById(Long.valueOf(playerToGameDTO.getPlayer_id())).orElseThrow();
        System.out.println(user);
        Game_Player game_playerTest = game_playerRepository.getPlayerForGameByBothIds(Long.valueOf(playerToGameDTO.getGame_id()), Long.valueOf(playerToGameDTO.getPlayer_id()));
        if (game_playerTest == null){
            if(game != null && user != null){
                Game_Player game_player = new Game_Player();
                game_player.setGame(game);
                game_player.setUser(user);
                game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
                game_playerRepository.save(game_player);
                return game_player;
            }
        }
        return game_playerTest;
    }
    @DeleteMapping("/takeOutPlayerFromGame/{gameId}/{playerId}")
    public Game_Player takeOutPlayerFromGame(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId){
        User user = userRepository.findById(Long.parseLong(playerId)).orElseThrow();
        Game_Player game_player = game_playerRepository.getPlayerForGameByBothIds(Long.parseLong(gameId), Long.parseLong(playerId));
        if (game_player != null){
             game_playerRepository.deleteById(game_player.getId());
        }
        return game_player;
    }
    @GetMapping("/getPlayersForGame/{gameId}")
    public List<Object[]> getPlayersByGame(@PathVariable int gameId){
        Game game = gameRepository.findById(Long.valueOf(gameId)).orElseThrow();
        List<Object[]> listPlayers = game_playerRepository.getPlayers(Long.valueOf(gameId));
        return listPlayers;
    }
    @GetMapping("/getPlayersForGames/{gameId}")
    public List<PlayerGameDTO> getPlayersGame(@PathVariable int gameId){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersGame(Long.valueOf(gameId));
        return listPlayers;
    }

    @GetMapping("/getPlayersAddedToGames/{gameId}")
    public List<PlayerGameDTO> getPlayersAddedToGame(@PathVariable int gameId){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersAddedToGame(Long.valueOf(gameId));
        return listPlayers;
    }

    @GetMapping("/getPlayersNotAddedToGames/{gameId}/{collectiveId}")
    public List<PlayerGameDTO> getPlayersNotAddedToGame(@PathVariable("gameId") String gameId, @PathVariable("collectiveId") String collectiveId){
        System.out.println("/////////////////////////////////////");
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersNotAddedToGame1(Long.parseLong(gameId),Long.parseLong(collectiveId));
        return listPlayers;
    }

    @DeleteMapping("/deletePlayer/{idPlayer}")
    public ResponseEntity<User> deletePlayerById(@PathVariable int idPlayer){
        return ResponseEntity.ok(service.deletePlayerById(idPlayer));
    }





    @PostMapping("/confirmPlayerInGame")
    public Game_Player toConfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game_Player game_player = game_playerRepository.getPlayerForGameByBothIds(Long.valueOf(playerToGameDTO.getGame_id()), Long.valueOf(playerToGameDTO.getPlayer_id()));
        if(game_player != null){
            game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
            game_playerRepository.save(game_player);
        }
        return game_player;
    }
    @PostMapping("/disconfirmPlayerInGame")
    public User toDisconfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game game = gameRepository.findById(Long.valueOf(playerToGameDTO.getGame_id())).orElseThrow();
        User user = userRepository.findById(Long.valueOf(playerToGameDTO.getPlayer_id())).orElseThrow();
        System.out.println(user);
        if(game != null && user != null){
            Game_Player game_player = new Game_Player();
            game_player.setGame(game);
            game_player.setUser(user);
            game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
            game_playerRepository.save(game_player);
        }
        return user;
    }

}
