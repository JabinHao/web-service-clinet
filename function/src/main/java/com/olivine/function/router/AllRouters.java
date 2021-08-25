package com.olivine.function.router;

import com.olivine.function.handler.CountryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/20 19:13
 */
@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> countryRouter(CountryHandler handler){
        return nest(
                path("/country"), route(
                        GET("/getAll"), handler::getAll)
                        .andRoute(GET("/get/{id}"), handler::getById)
                        .andRoute(GET("/get/by/name"), handler::getByName)
                        .andRoute(GET("/get/by/gdp"), handler::getGdpBetween)
                        .andRoute(POST("/create")
                                .and(accept(MediaType.APPLICATION_JSON)), handler::create)
                        .andRoute(DELETE("/delete"), handler::deleteByName)
                        .andRoute(PUT("/update")
                                .and(accept(MediaType.APPLICATION_JSON)), handler::update)
                );
    }
}
