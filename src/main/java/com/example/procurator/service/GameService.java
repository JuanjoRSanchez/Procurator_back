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

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    private final CollectiveRepository collectiveRepository;

    private final UserRepository userRepository;

    public List<Game> getAllGamesByCollective(GameDTO gameDTO){
        Long userId = null;
        Long collectiveId = null;
        User user = userRepository.findById(Long.valueOf(gameDTO.getUserId())).orElseThrow();
        boolean userExists = userRepository.findById(Long.valueOf(gameDTO.getUserId())).isPresent();
        boolean collectiveExists = collectiveRepository.findByNameAndUser(gameDTO.getCollectiveGame(), user).isPresent();
        if (userExists && collectiveExists){
             userId = userRepository.findById(Long.valueOf(gameDTO.getUserId())).orElseThrow().getId();
             collectiveId = collectiveRepository.findByNameAndUser(gameDTO.getCollectiveGame(), user).orElseThrow().getId();
        }
        return gameRepository.findAllByCollectiveNameAndCollective(collectiveId, userId);
    }
    public List<Game> getGamesByCollectiveId(int collectiveId){
        Long collectiveIdL = Long.valueOf(collectiveId);
        boolean collectiveExists = collectiveRepository.findById(collectiveIdL).isPresent();
        if (collectiveExists){
            return  gameRepository.findAllByCollectiveId(collectiveIdL);
        }
        throw new AlreadyExistException("Collective not found.");

    }
    public boolean setGame(GameDTO gameDTO){
        System.out.println(gameDTO.getCollectiveId());
        Collective collective = collectiveRepository.findById(Long.valueOf(gameDTO.getCollectiveId())).orElseThrow();
        var game = Game.builder()
                .blackScore(gameDTO.getBlackScore())
                .whiteScore(gameDTO.getWhiteScore())
                .collective(collective)
                .dateMatch(gameDTO.getDateMatch())
                .build();
        gameRepository.save(game);
        return true;
    }

    public boolean deleteGame(GameDTO gameDTO){
        Game game = gameRepository.findById(Long.valueOf(gameDTO.getGameId())).orElseThrow();
        gameRepository.delete(game);
        return true;
    }
}
