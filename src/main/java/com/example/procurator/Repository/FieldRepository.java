package com.example.procurator.Repository;

import com.example.procurator.model.Collective;
import com.example.procurator.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    Optional<Field> findByName(String fieldName);

    Optional<Field> findByPhone(String phone);

    Optional<List<Field>> findByCollective(Collective collective);
}
