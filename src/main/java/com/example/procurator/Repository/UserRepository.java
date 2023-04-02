package com.example.procurator.Repository;

import com.example.procurator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByPhone(String phone);

    Optional<User> findByPassword(String password);
    @Query("SELECT u FROM User u WHERE u.role = 'PLAYER'")
    List<User> findAllPlayers();

    @Query("SELECT u FROM User u WHERE u.role = 'PLAYER'")
    User findPlayerById(Long player_id);

    @Query("SELECT u FROM User u WHERE u.role = 'PLAYER' ")
    List<User> findPlayersByCollectiveId(Long collective_id);

    @Query("SELECT u FROM User u WHERE u.role = 'PLAYER'")
    List<User> findPlayersByGameId(Long game_id);
/*
    @Query(value = "SELECT * FROM User WHERE game_id = 1")
    List<User> getUsersFromGame(Long gameId);

 */
}
