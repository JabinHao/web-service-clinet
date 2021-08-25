package com.olivine.function.repository;

import com.olivine.function.domain.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/24 16:51
 */
public interface CountryMongoRepository extends MongoRepository<Country, String> {

}
