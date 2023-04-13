package com.example.procurator.service;

import com.example.procurator.DTOClasses.GameDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.GameRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.exception.NoFoundException;
import com.example.procurator.model.Collective;
import com.example.procurator.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    private final CollectiveRepository collectiveRepository;

    public Game getById(String id){
        return gameRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new NoFoundException("Game not found")
        );
    }
    public Game getById(int id){
        return gameRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new NoFoundException("Game not found")
        );
    }

    public List<Game> getGamesByCollectiveId(int collectiveId){
        Long collectiveIdL = Long.valueOf(collectiveId);
        boolean collectiveExists = collectiveRepository.findById(collectiveIdL).isPresent();
        if (collectiveExists){
            return  gameRepository.findAllByCollectiveId(collectiveIdL);
        }
        throw new NoFoundException("Collective not found.");
    }
    public Game setGame(GameDTO gameDTO){
        Collective collective = collectiveRepository.findById(Long.valueOf(gameDTO.getCollectiveId())).orElseThrow(
                () -> new NoFoundException("Collective not found")
        );
        LocalDateTime dateMatch = LocalDateTime.parse(String.valueOf(gameDTO.getDateMatch()));
        var game = Game.builder()
                .creationDate(new Date())
                .collective(collective)
                .dateMatch(dateMatch)
                .build();

        return gameRepository.save(game);
    }

    public Game updateGame(GameDTO gameDTO){
        Game gameAux = getById(gameDTO.getId());
        if (gameDTO.getDateMatch() != null){
            gameAux.setDateMatch(gameDTO.getDateMatch());
        }
        if (gameDTO.getWhiteScore() >= 0){
            gameAux.setWhiteScore(gameDTO.getWhiteScore());
        }
        if(gameDTO.getBlackScore() >= 0){
            gameAux.setBlackScore(gameDTO.getBlackScore());
        }

        return gameRepository.save(gameAux);
    }

    public Game deleteGame(int game_id){
        Game game = gameRepository.findById(Long.valueOf(game_id)).orElseThrow(
                () -> new NoFoundException("Game not found")
        );
        gameRepository.delete(game);
        return game;
    }

}
