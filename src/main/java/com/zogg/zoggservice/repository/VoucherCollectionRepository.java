package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.VoucherCollection;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoucherCollectionRepository extends MongoRepository<VoucherCollection, String> {

    List<VoucherCollection> findAllByActiveTrue();

    List<VoucherCollection> findAllByIdNotIn(Set<String> ids);
}
