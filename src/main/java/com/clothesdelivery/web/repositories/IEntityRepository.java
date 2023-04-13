package com.clothesdelivery.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntityRepository<TEntity> extends CrudRepository<TEntity, Long> {
    TEntity findByFriendlyUrl(String friendlyUrl);
    TEntity findByEmailAndPassword(String email, String password);
}
