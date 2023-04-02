package com.example.procurator.DTOClasses.Queryresponse;

import java.util.Date;

public interface PlayerGameDTO {

     Long getId();

     String getName();

     Integer getAge();

     String getAddress();

     String getPhone();

     String getEmail();

     String getPassword();

     Date getCreationDate();

     String getRole();

     Long getCollective_id();

     boolean isActive();

}
