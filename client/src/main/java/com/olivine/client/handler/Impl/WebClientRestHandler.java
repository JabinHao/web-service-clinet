package com.olivine.client.handler.Impl;

import com.olivine.client.bean.MethodInfo;
import com.olivine.client.bean.ServerInfo;
import com.olivine.client.handler.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

import static org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import static org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

/**
 * @description: 基于 Spring5 的 WbClient 的 rest handler
 * @author: jphao
 * @date: 2021/8/25 14:08
 */

@Slf4j
public class WebClientRestHandler implements RestHandler {

    private WebClient client;

    @Override
    public void init(ServerInfo serverInfo) {
        client = WebClient.create(serverInfo.getBaseUrl());
    }

    @Override
    public Object invokeRest(MethodInfo methodInfo) {

        Object result = null;

        // uri 与 参数
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        methodInfo
                .getParams()
                .forEach((k, v) -> multiValueMap.put(k, Collections.singletonList(String.valueOf(v)))
                );
        log.info("params: " + methodInfo.getParams());

        // 处理请求
        RequestBodySpec requestBodySpec = this.client
                .method(methodInfo.getMethod())
                .uri(uriBuilder -> uriBuilder
                        .path(methodInfo.getUri())
                        .queryParams(multiValueMap)
                        .build(methodInfo.getParams())
                )
                .accept(MediaType.APPLICATION_JSON);

        // 判断是否有请求体， 发送请求
        ResponseSpec retrieve = null;
        if (methodInfo.getBody() != null){
            retrieve = requestBodySpec
                    .body(methodInfo.getBody(), methodInfo.getBodyElementType())
                    .retrieve();
        }else {
            retrieve = requestBodySpec.retrieve();
        }

        // 处理返回结果
        if (methodInfo.isReturnFlux()){
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        }else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }

        return result;
    }

}
