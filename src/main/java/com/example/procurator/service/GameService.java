package com.example.procurator.service;

import com.example.procurator.DTOClasses.GameDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.GameRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.model.Collective;
import com.example.procurator.model.Game;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    private final CollectiveRepository collectiveRepository;

    private final UserRepository userRepository;

    public List<Game> getGamesByCollectiveId(int collectiveId){
        Long collectiveIdL = Long.valueOf(collectiveId);
        boolean collectiveExists = collectiveRepository.findById(collectiveIdL).isPresent();
        if (collectiveExists){
            return  gameRepository.findAllByCollectiveId(collectiveIdL);
        }
        throw new AlreadyExistException("Collective not found.");

    }
    public boolean setGame(GameDTO gameDTO){
        Collective collective = collectiveRepository.findById(Long.valueOf(gameDTO.getCollectiveId())).orElseThrow();
        LocalDateTime dateMatch = LocalDateTime.parse(String.valueOf(gameDTO.getDateMatch()));

        var game = Game.builder()
                .creationDate(new Date())
                .collective(collective)
                .dateMatch(dateMatch)
                .build();
        gameRepository.save(game);
        return true;
    }

    public Game updateGame(Game game){
        Game gameaux = gameRepository.findById(Long.valueOf(game.getId())).orElseThrow();
        if (game.getDateMatch() != null){
            gameaux.setDateMatch(game.getDateMatch());
        }
        if (game.getWhiteScore() >= 0){
            gameaux.setWhiteScore(game.getWhiteScore());
        }
        if(game.getBlackScore() >= 0){
            gameaux.setBlackScore(game.getBlackScore());
        }
        gameRepository.save(gameaux);
        return null;
    }

    public Game deleteGame(int game_id){
        Game game = gameRepository.findById(Long.valueOf(game_id))
                .orElseThrow(() -> new RuntimeException("Game not found :: " + game_id));
        gameRepository.delete(game);
        return game;
    }
}
