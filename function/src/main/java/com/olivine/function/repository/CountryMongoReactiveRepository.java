package com.olivine.function.repository;

import com.olivine.function.domain.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryMongoReactiveRepository extends ReactiveMongoRepository<Country, String> {

    Mono<Country> findCountryByName(String name);

    Flux<Country> findByGdpBetween(double start, double end);

    Mono<Void> deleteByName(String name);
}
