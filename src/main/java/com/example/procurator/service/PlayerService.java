package com.example.procurator.service;

import com.example.procurator.DTOClasses.PlayerToCollective;
import com.example.procurator.DTOClasses.PlayerToGameDTO;
import com.example.procurator.DTOClasses.Queryresponse.PlayerGameDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.GameRepository;
import com.example.procurator.Repository.Game_PlayerRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.NoFoundException;
import com.example.procurator.exception.ServiceException;
import com.example.procurator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PlayerService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CollectiveRepository collectiveRepository;

    private final Game_PlayerRepository game_playerRepository;

    private final GameRepository gameRepository;

    private final PasswordEncoder passwordEncoder;


    public PlayerService(UserRepository userRepository, UserService userService, CollectiveRepository collectiveRepository, Game_PlayerRepository game_playerRepository, GameRepository gameRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.collectiveRepository = collectiveRepository;
        this.game_playerRepository = game_playerRepository;
        this.gameRepository = gameRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PlayerGameDTO> getPlayersByCollectiveId(int idCollective){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersByCollectiveId(Long.valueOf(idCollective)).orElseThrow(
                () ->  new NoFoundException("No collectives found"));

        return listPlayers;
    }

    public User addPlayerToCollective(PlayerToCollective playerToCollective){
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
            userService.saveUser(user);
        }
        return player;
    }

    public Game_Player addPlayerToGame(PlayerToGameDTO playerToGameDTO){
        Game game = gameRepository.findById(Long.valueOf(playerToGameDTO.getGame_id())).orElseThrow();
        User user = userRepository.findById(Long.valueOf(playerToGameDTO.getPlayer_id())).orElseThrow();
        System.out.println(user);
        Game_Player game_playerTest = game_playerRepository.
                getPlayerForGameByBothIds(Long.valueOf(playerToGameDTO.getGame_id()), Long.valueOf(playerToGameDTO.getPlayer_id()))
                .orElseThrow(() -> new RuntimeException("No player found for this game"));
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

    public List<PlayerGameDTO> getPlayersByGame(int IdGame){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersByGame(Long.valueOf(IdGame)).orElseThrow(
                () -> new NoFoundException("No players found for this game"));

        return listPlayers;
    }

    public List<PlayerGameDTO> getPlayersAddedToGame(@PathVariable int gameId){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersAddedToGame(Long.valueOf(gameId)).orElseThrow(
                () -> new NoFoundException("No players added to this game")
        );
        return listPlayers;
    }

    public List<PlayerGameDTO> getPlayersNotAddedToGame(String idGame, String idCollective){
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersNotAddedToGame1((Long.parseLong(idGame)), Long.parseLong(idCollective)).orElseThrow(
                () -> new NoFoundException("No players added to this game"));
        return listPlayers;
    }

    public Game_Player toConfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game_Player game_player = game_playerRepository.
                getPlayerForGameByBothIds(Long.valueOf(playerToGameDTO.getGame_id()), Long.valueOf(playerToGameDTO.getPlayer_id()))
                .orElseThrow(() -> new ServiceException("111111"));
        if(game_player != null){
            game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
            game_playerRepository.save(game_player);
        }
        return game_player;
    }

    public User toDisconfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game game = gameRepository.findById(Long.valueOf(playerToGameDTO.getGame_id())).orElseThrow(
                () -> new NoFoundException("No game found by this ID")
        );
        User user = userRepository.findById(Long.valueOf(playerToGameDTO.getPlayer_id())).orElseThrow(
                () -> new NoFoundException("No user found by this ID")
        );
        if(game != null && user != null){
            Game_Player game_player = new Game_Player();
            game_player.setGame(game);
            game_player.setUser(user);
            game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
            game_playerRepository.save(game_player);
        }
        return user;
    }

    public User updatePlayer(Player player){
        User user = userRepository.findById(player.getId()).orElseThrow(
                () -> new NoFoundException("User not found"));
        user.setEmail(player.getEmail());
        user.setPassword(player.getPassword());
        user.setPhone(player.getPhone());
        user.setAddress(player.getAddress());
        user.setAge(player.getAge());
        user.setName(player.getName());
        userService.saveUser(user);
        return user;
    }

    public HttpStatus deletePlayerFromGame (String idGame, String idPlayer){
        Game_Player game_player = game_playerRepository.getPlayerForGameByBothIds(Long.parseLong(idGame), Long.parseLong(idPlayer))
                .orElseThrow(() -> new NoFoundException("No player found for this game"));
        if (game_player != null) {
            game_playerRepository.deleteById(game_player.getId());
            return HttpStatus.OK;
        }
        return  HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public User deletePlayerFromCollective(int idPlayer){
        User user = userRepository.findById(Long.valueOf(idPlayer))
                .orElseThrow(() -> new ServiceException("Game not found :: " + idPlayer));
        userRepository.delete(user);
        return user;
    }

}
