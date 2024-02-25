package com.example.service;

//import com.example.model.OKRSet;

import com.example.model.Unit;
import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UnitService extends MongoRepository<Unit, UUID> {

    /**
     * Searches for a Unit containing a certain user
     *
     * @param user Entry of User filtered for
     * @return Optional containing a Unit containing user or NULL
     */
    Optional<Unit> findByEmployeeSetContains(User user);

    /**
     * Deletes Unit corresponding to the given ID
     *
     * @param id UUID to be deleted
     * @return Optional containing the deleted Unit or NULL
     */
    Optional<Unit> deleteByUuid(UUID id);
}
