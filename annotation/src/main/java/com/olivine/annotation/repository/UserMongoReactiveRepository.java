package com.olivine.annotation.repository;

import com.olivine.annotation.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMongoReactiveRepository extends ReactiveMongoRepository<User, String> {
}
