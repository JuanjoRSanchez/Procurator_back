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

    private final UserService userService;

    private final CollectiveService collectiveService;

    private final Game_PlayerRepository game_playerRepository;

    private final GameService gameService;

    private final PasswordEncoder passwordEncoder;


    public PlayerService(UserService userService,
                         GameService gameService,
                         Game_PlayerRepository game_playerRepository,
                         PasswordEncoder passwordEncoder,
                         CollectiveService collectiveService
                         ) {
        this.userService = userService;
        this.gameService = gameService;
        this.game_playerRepository = game_playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.collectiveService = collectiveService;
    }


    public List<PlayerGameDTO> getPlayersByCollectiveId(int idCollective){
        Collective collective = collectiveService.getCollectiveById(String.valueOf(idCollective));
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersByCollectiveId(Long.valueOf(idCollective)).orElseThrow(
                () ->  new NoFoundException("No players found"));

        return listPlayers;
    }

    public List<PlayerGameDTO> getPlayersByGame(String idGame){
        Game game = gameService.getById(idGame);
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersByGame(Long.valueOf(idGame)).orElseThrow(
                () -> new NoFoundException("No players found for this game"));

        return listPlayers;
    }

    public List<PlayerGameDTO> getPlayersAddedToGame(@PathVariable String idGame){
        Game game = gameService.getById(idGame);
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersAddedToGame(Long.valueOf(idGame)).orElseThrow(
                () -> new NoFoundException("No players added to this game")
        );
        return listPlayers;
    }

    public List<PlayerGameDTO> getPlayersNotAddedToGame(String idGame, String idCollective){
        Game game = gameService.getById(idGame);
        List<PlayerGameDTO> listPlayers = game_playerRepository.getPlayersNotAddedToGame((Long.parseLong(idGame)), Long.parseLong(idCollective)).orElseThrow(
                () -> new NoFoundException("No players added to this game"));
        return listPlayers;
    }
    public User addPlayerToCollective(PlayerToCollective playerToCollective){
        Collective collective = collectiveService.getCollectiveById(String.valueOf(playerToCollective.getIdCollective()));
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
        userService.saveUser(user);

        return user;
    }

    public Game_Player addPlayerToGame(PlayerToGameDTO playerToGameDTO){
        Game game = gameService.getById(playerToGameDTO.getGame_id());
        User user = userService.getById(playerToGameDTO.getPlayer_id());

        Game_Player game_player = new Game_Player();
        game_player.setGame(game);
        game_player.setUser(user);
        game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
        game_playerRepository.save(game_player);
        return game_player;
    }



    public Game_Player toConfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game_Player game_player = game_playerRepository.
                getPlayerForGameByBothIds(Long.valueOf(playerToGameDTO.getGame_id()), Long.valueOf(playerToGameDTO.getPlayer_id()))
                .orElseThrow(() -> new NoFoundException("Not found"));

        game_player.setAddedToGame(playerToGameDTO.isAddedToGame());
        return game_playerRepository.save(game_player);
    }

    public User toDisconfirmPlayerInGame(@RequestBody PlayerToGameDTO playerToGameDTO){
        Game game = gameService.getById(playerToGameDTO.getGame_id());
        User user = userService.getById(playerToGameDTO.getPlayer_id());
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
        User user = userService.getById(Math.toIntExact(player.getId()));
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
        User user = userService.getById(idPlayer);
        userService.deleteUser(user);
        return user;
    }

}
