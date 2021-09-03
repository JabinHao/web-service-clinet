package com.olivine.client.proxy;

import com.olivine.client.api.UserApi;

/**
 * @description: 为 API 创建动态代理
 * @author: jphao
 * @date: 2021/8/25 14:05
 */
public interface ProxyCreator {

    Object createProxy(Class<?> api);
}
