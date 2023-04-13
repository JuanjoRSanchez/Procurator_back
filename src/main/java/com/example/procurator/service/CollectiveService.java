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

    public Collective getCollectiveById(String collectiveId) {
        Collective collective = collectiveRepository.findById(Long.parseLong(collectiveId)).orElseThrow(
                () -> new NoFoundException("Not collective found for this ID")
        );
        return collective;
    }
    public List<Collective> getAllCollectivesByUserEmail(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return collectiveRepository.findAllCollectivesByUser(user).orElseThrow();
    }

    public Collective getCollectiveByNameAndUserEmail(CollectiveDTO collectiveDTO) {
        User user = userRepository.findByEmail(collectiveDTO.getEmail()).orElseThrow();
        return collectiveRepository.findByNameAndUser(collectiveDTO.getName(), user ).orElseThrow(
                () -> new NoFoundException("No Team present with name = " + collectiveDTO.getName())
        );
    }

    public HttpStatus setCollectiveByCollectiveNameAndUserEmail(CollectiveDTO collectiveDTO){
        User userModel = userRepository.findByEmail(collectiveDTO.getEmail()).orElseThrow();
        boolean collectiveExists = collectiveRepository.findByNameAndUser(collectiveDTO.getName(), userModel).isPresent();
        if (collectiveExists) {
            throw new AlreadyExistException("Already exists a collective with this name");
        }
        var collective = Collective.builder()
                        .name(collectiveDTO.getName())
                        .user(userModel)
                        .creationDate(new Date())
                        .build();
            collectiveRepository.save(collective);

        if(getCollectiveByNameAndUserEmail(collectiveDTO).getName().isEmpty()){
            throw new RuntimeException("The save can't be do it at this time");
        }
        return  HttpStatus.OK;
    }

    public Collective updateCollective(CollectiveDTO collectiveDTO){
        User user = userService.getUserByEmail(collectiveDTO.getEmail());
        Collective collective = collectiveRepository.findById(Long.valueOf(collectiveDTO.getIdCollective())).orElseThrow();
        if(collective != null){
            collective.setName(collectiveDTO.getNewName());
            collectiveRepository.save(collective);
        }

        return collective;
    }

    public void deleteCollectiveById(String collectiveId) {
        Collective collective = getCollectiveById(collectiveId);
        collectiveRepository.deleteById(Long.parseLong(collectiveId));
    }

}
