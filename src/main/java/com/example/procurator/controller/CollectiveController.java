package com.example.procurator.controller;

import com.example.procurator.DTOClasses.CollectiveDTO;
import com.example.procurator.DTOClasses.UserDAO;
import com.example.procurator.model.Collective;
import com.example.procurator.service.CollectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/collectives")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CollectiveController {

    private final CollectiveService collectiveService;

    @GetMapping("/collectiveName/{collectiveName}")
    public ResponseEntity<Collective> getCollectiveByName(@PathVariable String collectiveName){
        Collective collectiveByNameFoundByName = collectiveService.getCollectiveByName(collectiveName);
        return ResponseEntity.ok().body(collectiveByNameFoundByName);
    }
    @GetMapping("/getCollectivesByUserEmail/{userEmail}")
    public ResponseEntity<List<Collective>>  getCollectivesByUserEmail(@PathVariable String userEmail){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserEmail(userEmail));
    }
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Collective>>  getAllCollectivesByUser(@PathVariable Integer userId){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserId(userId));
    }

    @PostMapping("/getCollective")
    public ResponseEntity<Collective>  getCollectiveByNameAndUserEmail(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok().body(collectiveService.getCollectiveByNameAndUserEmail(collectiveDTO));
    }

    @PostMapping ("/getAll")
    public ResponseEntity<List<Collective>>  getAllCollectivesByUserEmail(@RequestBody UserDAO userDAO){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserEmail(userDAO.getEmail()));
    }

    @PostMapping("/addCollective")
    public ResponseEntity<HttpStatus>  setCollective01(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.setCollectiveByCollectiveNameAndUserEmail01(collectiveDTO));
    }

    @PutMapping("/updateCollective")
    public ResponseEntity<Collective>  updateCollective(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.updateCollective(collectiveDTO));
    }

    @DeleteMapping
    public ResponseEntity<Collective>  deleteCollective01(@RequestBody CollectiveDTO CollectiveDTO){
        return ResponseEntity.ok(collectiveService.deleteCollectiveByNameAndUserEmail01(CollectiveDTO));
    }

}
