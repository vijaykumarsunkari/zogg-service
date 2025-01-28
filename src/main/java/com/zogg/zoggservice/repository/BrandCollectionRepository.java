package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.BrandCollection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandCollectionRepository extends MongoRepository<BrandCollection, String> {

    BrandCollectionRepository findByNameAndActiveTrue(String name);

    List<BrandCollection> findAllByActiveTrue();
}
