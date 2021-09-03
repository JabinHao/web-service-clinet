package com.olivine.client.controller;

import com.olivine.client.api.CountryApi;
import com.olivine.client.api.UserApi;
import com.olivine.client.domain.Country;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {

    private final CountryApi countryApi;

    private final UserApi userApi;

    public TestController(CountryApi countryApi, UserApi userApi) {
        this.countryApi = countryApi;
        this.userApi = userApi;
    }

    @GetMapping("/")
    public Flux<Country> getAll() {
        return countryApi.getAll();
    }

    @GetMapping("/get/{id}")
    public Mono<Country> getById(@PathVariable("id") String id){
        return countryApi.getById(id);
    }

    @RequestMapping(value = "/get/by/name", method = RequestMethod.GET)
    Mono<Country> getByName(@RequestParam("name") String name){
        return countryApi.getByName("China");
    }

    @RequestMapping(value = "/get/by/gdp", method = RequestMethod.GET)
    Flux<Country> getGdpBetween(@RequestParam double start, @RequestParam double end){
        return countryApi.getGdpBetween(10.0, 30.0);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    Mono<Void> update(@RequestBody Country country){
        return countryApi.update(Mono.just(new Country("114514", "Wakanda", "Birnin Zana", 1.333, 999.9)));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    Mono<Void> deleteByName(@RequestParam String name){
        return countryApi.deleteByName("Wakanda");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    Mono<Void> create(@RequestBody Country country){
        return countryApi.create(Mono.just(new Country("114514", "Wakanda", "Birnin Zana", 1.333, 999.9)));
    }

    public void countryTest(){
        Flux<Country> all = countryApi.getAll();
        System.out.println();
        countryApi.getById("1");
        countryApi.getByName("China");
        countryApi.getGdpBetween(10.0, 30.0);
        countryApi.deleteByName("Wakanda");
    }
}



