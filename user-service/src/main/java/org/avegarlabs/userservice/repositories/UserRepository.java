package org.avegarlabs.userservice.repositories;

import org.avegarlabs.userservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String usename);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);

    Optional<User> findByMoniker(String moniker);


}
