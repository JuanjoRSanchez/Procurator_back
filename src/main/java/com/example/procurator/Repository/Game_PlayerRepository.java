package com.example.procurator.Repository;

import com.example.procurator.DTOClasses.Queryresponse.PlayerGameDTO;
import com.example.procurator.model.Game_Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Game_PlayerRepository extends JpaRepository<Game_Player, Long> {

    Optional<Game_Player> findById(Long game_playerId);
    @Query(value = "SELECT * FROM _user AS u \n" +
            "JOIN game_player AS gp\n" +
            "ON gp.user_id = u.id", nativeQuery = true)
    List<Integer> getUsers(Long gameId);

    //@Query(value = "SELECT id FROM game_player WHERE game_id = ?1 AND user_id = ?2", nativeQuery = true)
    //List<Object[]> getPlayerById(Long gameId, Long userId);
    @Query(value = "SELECT u.id, u.age, u.creation_date, u.email, u.name, u.phone, u.active, u.address, u.role, u.collective_id, u.password  FROM _user AS u JOIN game_player AS gp ON gp.user_id = u.id WHERE gp.game_id = ?1", nativeQuery = true)
    List<Object[]> getPlayers(Long gameId);
    @Query(value = "SELECT u.id AS id, u.age AS age, u.creation_date AS creationDate, u.email AS email, u.name AS name, u.phone AS phone, u.active AS active, u.address AS address, u.role AS role, u.collective_id AS collective_id, u.password AS password FROM _user AS u JOIN game_player AS gp ON gp.user_id = u.id WHERE gp.game_id = ?1", nativeQuery = true)
    List<PlayerGameDTO> getPlayersGame(Long gameId);

    @Query(value = "SELECT u.id AS id, u.age AS age, u.creation_date AS creationDate, u.email AS email, u.name AS name, u.phone AS phone, u.active AS active, u.address AS address, u.role AS role, u.collective_id AS collective_id, u.password AS password FROM _user AS u JOIN game_player AS gp ON gp.user_id = u.id WHERE gp.game_id = ?1 AND gp.added_to_game = true", nativeQuery = true)
    List<PlayerGameDTO> getPlayersAddedToGame(Long gameId);

    @Query(value = "SELECT u.id AS id, u.age AS age, u.creation_date AS creationDate, u.email AS email, u.name AS name, u.phone AS phone, u.active AS active, u.address AS address, u.role AS role, u.collective_id AS collective_id, u.password AS password FROM _user AS u JOIN game_player AS gp ON gp.user_id = u.id WHERE gp.game_id = ?1 AND gp.added_to_game = false", nativeQuery = true)
    List<PlayerGameDTO> getPlayersNotAddedToGame(Long gameId);

    @Query(value = "SELECT * FROM `_user` WHERE id NOT IN (SELECT user_id FROM game_player WHERE game_id = ?1) AND collective_id = ?2", nativeQuery = true)
    List<PlayerGameDTO> getPlayersNotAddedToGame1(Long gameId, Long collectiveId);


    @Query(value = "SELECT * FROM game_player  WHERE game_id = ?1 AND user_id = ?2", nativeQuery = true)
    Game_Player getPlayerForGameByBothIds(Long gameId, Long playerId);


    void deleteById(Long gamePlayer_Id);

}
