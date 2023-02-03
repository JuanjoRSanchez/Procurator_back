package com.example.procurator.controller;

import com.example.procurator.DTOClasses.CollectiveDTO;
import com.example.procurator.DTOClasses.UserDAO;
import com.example.procurator.model.Collective;
import com.example.procurator.service.CollectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/collectives")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CollectiveController {

    private final CollectiveService collectiveService;

    @GetMapping("/collectiveName/{collectiveName}")
    public ResponseEntity<Collective> getCollectiveByName(@PathVariable String collectiveName){
        Collective collectiveByNameFoundByName = collectiveService.getCollectiveByName(collectiveName);
        return ResponseEntity.ok().body(collectiveByNameFoundByName);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Collective>>  getAllCollectivesByUser(@PathVariable Integer userId){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserId(userId));
    }

    @PostMapping ("/getAll")
    public ResponseEntity<List<Collective>>  getAllCollectivesByUser(@RequestBody UserDAO userDAO){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserEmail(userDAO.getEmail()));
    }

    @GetMapping
    public ResponseEntity<Collective> getCollectiveByNameAndUserEmail(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.getCollectiveByNameAndUserEmail(collectiveDTO));
    }

    @PostMapping("/addCollective")
    @Secured("USER")
    public ResponseEntity<Collective>  setCollective(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.setCollectiveWithoutRelations(collectiveDTO));
    }

    @DeleteMapping
    @Secured("USER")
    public ResponseEntity<Map<String, Boolean>>  deleteCollective(@RequestBody CollectiveDTO CollectiveDTO){
        return ResponseEntity.ok(collectiveService.deleteCollectiveByNameAndUserEmail(CollectiveDTO));
    }
}
