package com.olivine.client.api;

import com.olivine.client.annotation.WSCServer;
import com.olivine.client.domain.Country;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/25 14:14
 */
@WSCServer("http://localhost:8081/function/country")
public interface CountryApi {

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    Flux<Country> getAll();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    Mono<Country> getById(@PathVariable("id") String id);

    @RequestMapping(value = "/get/by/name", method = RequestMethod.GET)
    Mono<Country> getByName(@RequestParam("name") String name);

    @RequestMapping(value = "/get/by/gdp", method = RequestMethod.GET)
    Flux<Country> getGdpBetween(@RequestParam("start") double start, @RequestParam("end") double end);

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    Mono<Void> update(@RequestBody Mono<Country> country);

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    Mono<Void> deleteByName(@RequestParam("name") String name);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    Mono<Void> create(@RequestBody Mono<Country> country);

}
