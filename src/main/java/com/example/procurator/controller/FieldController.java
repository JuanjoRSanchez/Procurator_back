package com.example.procurator.controller;

import com.example.procurator.DTOClasses.FieldDTO;
import com.example.procurator.model.Field;
import com.example.procurator.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class FieldController {

    private final FieldService fieldService;
/*
    @GetMapping("/getAllFields")
    public ResponseEntity<List<Field>> getAllFields(){
        return ResponseEntity.ok(fieldService.getAllFields());
    }

 */
/*
    @GetMapping("/{fieldName}")
    public ResponseEntity<Field> getFieldByName(@PathVariable String fieldName){
        return ResponseEntity.ok(fieldService.getFieldByName(fieldName));
    }

 */
    @GetMapping("/{fieldID}")
    public ResponseEntity<Field> getFieldById(@PathVariable String fieldId){
        return ResponseEntity.ok(fieldService.getFieldById(fieldId));
    }
    @GetMapping("/findByCollectiveId/{collectiveId}")
    public ResponseEntity<List<Field>> getFieldByCollectiveId(@PathVariable String collectiveId){
        return ResponseEntity.ok(fieldService.getFieldByCollectiveId(collectiveId));
    }
    @PostMapping
    public ResponseEntity<Field> setField(@RequestBody FieldDTO fieldDTO){
        return ResponseEntity.ok(fieldService.setField(fieldDTO));
    }

    @PutMapping
    public ResponseEntity<Field> updateField(@RequestBody FieldDTO fieldDTO){

        return ResponseEntity.ok(fieldService.updateField(fieldDTO));
    }
    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Field> deleteFiled(@PathVariable String fieldId){

        return ResponseEntity.ok(fieldService.deleteField(fieldId));
    }


}
