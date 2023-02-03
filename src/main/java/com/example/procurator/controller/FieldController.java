package com.example.procurator.controller;

import com.example.procurator.DTOClasses.FieldDTO;
import com.example.procurator.model.Field;
import com.example.procurator.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials =  "true")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/getAllFields")
    public List<Field> getAllFields(){
        return fieldService.getAllFields();
    }

    @GetMapping("/{fieldName}")
    public Field getFieldByName(@PathVariable String fieldName){
        return fieldService.getFieldByName(fieldName);
    }

    @PostMapping
    public boolean setField(@RequestBody FieldDTO fieldDTO){
        return fieldService.setField(fieldDTO);
    }

    @DeleteMapping
    public boolean deleteFiled(FieldDTO fieldDTO){
        return fieldService.deleteField(fieldDTO);
    }


}
