package com.example.procurator.DTOClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FieldDTO {

    private String id;

    private String name;

    private String address;

    private String phone;

    private String contactPhone;

    private String collectiveName;

    private String collectiveId;
}
