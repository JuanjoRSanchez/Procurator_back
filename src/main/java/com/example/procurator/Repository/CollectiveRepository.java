package com.example.procurator.Repository;


import com.example.procurator.model.Collective;
import com.example.procurator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectiveRepository extends JpaRepository<Collective, Long> {


    Optional<Collective> findByName(String teamName);

    Optional <List<Collective>> findAllCollectivesByUser(User user);

    Optional<Collective> findByNameAndUser(String name, User user);

}
