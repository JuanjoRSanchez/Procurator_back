package com.example.procurator.service;

import com.example.procurator.DTOClasses.CollectiveDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.UserRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.exception.NoFoundException;
import com.example.procurator.model.Collective;
import com.example.procurator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CollectiveService {

    private final CollectiveRepository collectiveRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    public List<Collective> getAllCollectivesByUserId(Integer userId){
        User user = userRepository.findById( Long.valueOf(userId)).orElseThrow();
        if (user != null){
            return collectiveRepository.findAllCollectivesByUser(user).orElseThrow();
        }else {
            throw new NoFoundException("No collective founds exists.");
        }
    }

    public List<Collective> getAllCollectivesByUserEmail(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (user != null){
            return collectiveRepository.findAllCollectivesByUser(user).orElseThrow();
        }else {
            throw new NoFoundException("No collective founds exists.");
        }
    }
    public Collective getCollectiveByName(String collectiveName) {
        boolean team = collectiveRepository.findByName(collectiveName).isEmpty();
        if(team)
            throw new NoFoundException("The team in not in the DataBase: " + collectiveName);
            return collectiveRepository.findByName(collectiveName).get();


    }
    public Collective getCollectiveByNameAndUserEmail(CollectiveDTO collectiveDTO) {
        User user = userRepository.findByEmail(collectiveDTO.getEmail()).orElseThrow();

        return collectiveRepository.findByNameAndUser(collectiveDTO.getName(), user ).orElseThrow(
                () -> new NoFoundException("No Team present with name = " + collectiveDTO.getName())
        );
    }

    public Collective setCollectiveWithoutRelations(CollectiveDTO collectiveDTO){
        User userModel = userRepository.findByEmail(collectiveDTO.getEmail()).orElseThrow();
        if (userModel.getEmail().equals(collectiveDTO.getEmail())){
            Collective team = new Collective();
            team.setName(collectiveDTO.getName());
            team.setUser(userModel);
            team.setCreationDate(new Date());
            collectiveRepository.save(team);
        }
        return  collectiveRepository.findByName(collectiveDTO.getName()).orElseThrow();
    }

    public Map<String, Boolean> deleteCollectiveByNameAndUserEmail(CollectiveDTO collectiveDTO) throws RuntimeException {
        User user = userService.getUserByEmail(collectiveDTO.getEmail());
        Collective team =  collectiveRepository.findByNameAndUser(collectiveDTO.getName(), user)
                    .orElseThrow(() -> new RuntimeException("Team not found for this name :: " + collectiveDTO.getName()));
        collectiveRepository.delete(team);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response ;
    }


}
