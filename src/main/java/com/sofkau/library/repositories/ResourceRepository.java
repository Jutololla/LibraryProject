package com.sofkau.library.repositories;

import com.sofkau.library.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;


public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByType(final String type);
    List<Resource> findByThematic(final String thematic);

}

