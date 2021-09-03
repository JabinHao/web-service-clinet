package com.olivine.client;

import com.olivine.client.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientApplicationTests {

    @Autowired
    private TestController controller;

    @Test
    void contextLoads() {
    }

    @Test
    public void countryApiTest(){

        controller.countryTest();
    }

}
