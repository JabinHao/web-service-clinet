package com.olivine.function.handler;

import com.olivine.function.domain.Country;
import com.olivine.function.repository.CountryMongoReactiveRepository;
import com.olivine.function.repository.CountryMongoRepository;
import com.olivine.function.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @description: Book handler
 * @author: jphao
 * @date: 2021/8/20 19:12
 */
@Slf4j
@Component
public class CountryHandler {

    private final CountryMongoReactiveRepository repository;

    private final CountryMongoRepository mongoRepository;

    public CountryHandler(CountryMongoReactiveRepository repository, CountryMongoRepository mongoRepository) {
        this.repository = repository;
        this.mongoRepository = mongoRepository;
    }

    public Mono<ServerResponse> getById(ServerRequest request){
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap( user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getByName(ServerRequest request){
        String name = request.queryParam("name").orElseGet(() -> request.pathVariable("name"));
        return repository.findCountryByName(name)
                .flatMap(country -> ServerResponse.ok().bodyValue(country))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getGdpBetween(ServerRequest request){
        String start = request.queryParam("start").orElseGet(() -> request.pathVariable("start"));
        String end = request.queryParam("end").orElseGet(() -> request.pathVariable("end"));
        Double s = Double.valueOf(start);
        Double e = Double.valueOf(end);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(repository.findByGdpBetween(s, e), Country.class);
    }

    public Mono<ServerResponse> getAll(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll(), Country.class);
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Country> countryMono = request.bodyToMono(Country.class);
        return countryMono
                .flatMap(country -> repository
                        .findById(country.getId())
                        .flatMap(c -> repository
                                .delete(c)
                                .then(repository.save(country))
                                .then(ServerResponse.ok().build())
                        )
                        .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Mono<Country> countryMono = request.bodyToMono(Country.class);

        return countryMono.flatMap(country -> {

            CheckUtil.checkCountry(country);

            return repository
                    .findById(country.getId())
                    // 存在则返回 bad request
                    .flatMap(c -> {
                        log.warn("insert Country: " + country + "failed, Country already existed: " + c);
                        return ServerResponse.badRequest().bodyValue("Country already existed:\n " + c);
                    })
                    // 不存在则插入
                    .switchIfEmpty(ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(repository.save(country), Country.class));
        });
    }

    public Mono<ServerResponse> deleteByName(ServerRequest request){
        String name = request.queryParam("name").orElseGet(() -> request.pathVariable("name"));
        log.info("name: " + name);

        return repository.findCountryByName(name)
                .flatMap(country -> {
                    log.info("delete Country: " + country);
                    return repository.delete(country)
                        .then(ServerResponse.ok().build());
                }
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
