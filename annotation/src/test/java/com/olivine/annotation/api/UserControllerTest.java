package com.olivine.annotation.api;

import com.olivine.annotation.controller.UserController;
import com.olivine.annotation.domain.User;
import com.olivine.annotation.exception.BizException;
import com.olivine.annotation.repository.UserMongoReactiveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/20 17:38
 */
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserMongoReactiveRepository repository;

    @Autowired
    private UserController userController;

    @Test
    public void testGetAll(){
        Flux<User> userFlux = userController.getAll();
        userFlux.subscribe(System.out::println);
    }

    @Test
    public void testGet(){
        Mono<User> user = userController.getUserById("5");
        user.subscribe(System.out::println);
    }

}
