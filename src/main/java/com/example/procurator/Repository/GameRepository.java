package com.example.procurator.Repository;

import com.example.procurator.DTOClasses.GameDTO;
import com.example.procurator.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

     List<Game> findAllByCollectiveId(Long collectiveId);
}
