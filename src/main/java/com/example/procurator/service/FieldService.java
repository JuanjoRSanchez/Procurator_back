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
    private final CollectiveService collectiveService;
    private final FieldRepository fieldRepository;

    public Field getFieldById(String fieldId){
        Field field = fieldRepository.findById(Long.parseLong(fieldId)).orElseThrow(
                () -> new NoFoundException("No filed found.")
        );
        return field;
    }
    public List<Field> getFieldByCollectiveId(String collectiveId){
        Collective collective = collectiveService.getCollectiveById(collectiveId);
        List<Field> fieldList = fieldRepository.findByCollective(collective).orElseThrow(
                () -> new NoFoundException("No fields found.")
        );
        System.out.println(fieldList);
        return fieldList;
    }

    public Field getFieldByName(String fieldName){
        boolean fieldExists = fieldRepository.findByName(fieldName).isEmpty();
        if(fieldExists){
            throw new NoFoundException("This field doesn't  exists.");
        } else {
            return fieldRepository.findByName(fieldName).orElseThrow() ;
        }
    }

    public Field setField(FieldDTO fieldDTO){
        Collective collective = collectiveService.getCollectiveById(fieldDTO.getCollectiveId());
        var field = Field.builder()
                .name(fieldDTO.getName())
                .phone(fieldDTO.getPhone())
                .address(fieldDTO.getAddress())
                .collective(collective)
                .contactPhone(fieldDTO.getContactPhone())
                .build();
        fieldRepository.save(field);
        return field;


    }

    public List<Field> getAllFields(){
        return fieldRepository.findAll();
    }

    public Field updateField(FieldDTO fieldDTO) {
        var field = getFieldById(fieldDTO.getId());
        if (fieldDTO.getName() != null){
            field.setName(fieldDTO.getName());
        }
        if (fieldDTO.getAddress() != null) {
            field.setAddress(fieldDTO.getAddress());
        }
        if (fieldDTO.getContactPhone() != null){
            field.setContactPhone(fieldDTO.getContactPhone());
        }
        fieldRepository.save(field);
        return null;
    }
    public Field deleteField(String fieldId){
        var field =  getFieldById(fieldId);
        fieldRepository.delete(field);
        return  field;
    }


}
