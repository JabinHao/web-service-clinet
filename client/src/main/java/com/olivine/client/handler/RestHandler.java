package com.olivine.client.handler;

import com.olivine.client.bean.MethodInfo;
import com.olivine.client.bean.ServerInfo;

import java.lang.reflect.Method;

/**
 * @description: 远程调用 rest 接口
 * @author: jphao
 * @date: 2021/8/25 12:13
 */
public interface RestHandler {

    void init(ServerInfo serverInfo);

    Object invokeRest(MethodInfo methodInfo);
}
