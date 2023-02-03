package com.example.procurator.service;

import com.example.procurator.DTOClasses.FieldDTO;
import com.example.procurator.Repository.CollectiveRepository;
import com.example.procurator.Repository.FieldRepository;
import com.example.procurator.exception.AlreadyExistException;
import com.example.procurator.exception.NoFoundException;
import com.example.procurator.model.Collective;
import com.example.procurator.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final CollectiveRepository collectiveRepository;
    private final FieldRepository fieldRepository;

    public Field getFieldByName(String fieldName){
        boolean fieldExists = fieldRepository.findByName(fieldName).isEmpty();
        if(fieldExists){
            throw new NoFoundException("This field doesn't  exists.");
        } else {
            return fieldRepository.findByName(fieldName).orElseThrow() ;
        }
    }

    public boolean setField(FieldDTO fieldDTO){
        boolean salida = false;
        Collective collective = collectiveRepository.findByName(fieldDTO.getCollectiveName()).orElseThrow();
        if (collective.getName().length() > 0){
            var field = Field.builder()
                    .name(fieldDTO.getName())
                    .phone(fieldDTO.getPhone())
                    .address(fieldDTO.getAddress())
                    .collective(collective)
                    .contactPhone(fieldDTO.getContactPhone())
                    .build();
            fieldRepository.save(field);
            salida = true;
        }
        return true;
    }

    public List<Field> getAllFields(){
        return fieldRepository.findAll();
    }

    public boolean deleteField(FieldDTO fieldDTO){
        boolean fieldExists = fieldRepository.findByPhone(fieldDTO.getPhone()).isPresent();
        if(fieldExists){
            var field = fieldRepository.findByPhone(fieldDTO.getPhone()).orElseThrow();
            fieldRepository.delete(field);
            return  true;
        }
        return false;
    }
}
