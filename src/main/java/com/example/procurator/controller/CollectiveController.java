package com.example.procurator.controller;

import com.example.procurator.DTOClasses.CollectiveDTO;
import com.example.procurator.model.Collective;
import com.example.procurator.service.CollectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/collective")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CollectiveController {

    private final CollectiveService collectiveService;

    @GetMapping("/findByUserMail/{userEmail}")
    public ResponseEntity<List<Collective>>  getCollectivesByUserEmail(@PathVariable String userEmail){
        return ResponseEntity.ok().body(collectiveService.getAllCollectivesByUserEmail(userEmail));
    }

    @GetMapping("/{collectiveId}")
    public ResponseEntity<Collective> getCollectiveById(@PathVariable String collectiveId){

        return ResponseEntity.ok().body(collectiveService.getCollectiveById(collectiveId));
    }

    @PostMapping("/addCollective")
    public ResponseEntity<HttpStatus>  setCollective01(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.setCollectiveByCollectiveNameAndUserEmail(collectiveDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Collective>  updateCollective(@RequestBody CollectiveDTO collectiveDTO){
        return ResponseEntity.ok(collectiveService.updateCollective(collectiveDTO));
    }

    @DeleteMapping("/{collectiveId}")
    public ResponseEntity<Collective>  deleteCollective01(@PathVariable String collectiveId){

        collectiveService.deleteCollectiveById(collectiveId);
        return ResponseEntity.ok().build();
    }

}
