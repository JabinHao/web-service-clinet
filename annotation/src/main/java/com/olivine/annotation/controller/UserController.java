package com.olivine.annotation.controller;

import com.olivine.annotation.domain.User;
import com.olivine.annotation.repository.UserMongoReactiveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMongoReactiveRepository userMongoReactiveRepository;

    public UserController(UserMongoReactiveRepository userMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Mono<User> getUserById(@PathVariable("id") String id){
        return userMongoReactiveRepository.findById(id);
    }

    @RequestMapping(value = "/get", produces = {MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.GET)
    public Flux<User> getAll(){
        return userMongoReactiveRepository.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Mono<User> createUser(@RequestBody User user){
        return userMongoReactiveRepository.save(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
        return userMongoReactiveRepository.findById(id)
                .flatMap(user -> userMongoReactiveRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/update")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody User user){

        return userMongoReactiveRepository.findById(user.getId())
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    return userMongoReactiveRepository.save(u);
                })
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
