package com.olivine.client.proxy.Impl;

import com.olivine.client.annotation.WSCServer;
import com.olivine.client.bean.ServerInfo;
import com.olivine.client.handler.Impl.WebClientRestHandler;
import com.olivine.client.handler.RestHandler;
import com.olivine.client.proxy.ProxyCreator;
import com.olivine.client.proxy.handler.ApiInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @description: 基于JDK8 的动态代理
 * @author: jphao
 * @date: 2021/8/25 14:06
 */

public class JDKProxyCreator implements ProxyCreator {

    @Override
    public Object createProxy(Class<?> api) {

        // 获取 server url
        ServerInfo serverInfo = extractServerInfo(api);

        // rest handler 用于调用远程接口
        RestHandler handler = new WebClientRestHandler();
        handler.init(serverInfo);

        // invoke
        ApiInvocationHandler invocationHandler = new ApiInvocationHandler(handler);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{api}, invocationHandler);
    }

    /**
     * 获取本地 API 对应的远程接口 url
     * @param api
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> api){

        WSCServer server = api.getAnnotation(WSCServer.class);

        String baseUrl = server.value();

        return new ServerInfo(baseUrl);
    }
}
