package com.olivine.client.config;

import com.olivine.client.api.CountryApi;
import com.olivine.client.api.UserApi;
import com.olivine.client.domain.Country;
import com.olivine.client.proxy.Impl.JDKProxyCreator;
import com.olivine.client.proxy.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/25 14:13
 */

@Configuration
public class ClientConfig {

    @Bean
    FactoryBean<UserApi> userApiFactoryBean(ProxyCreator proxyCreator){

        return new FactoryBean<UserApi>() {
            @Override
            public UserApi getObject() throws Exception {
                return (UserApi) proxyCreator.createProxy(UserApi.class);
            }

            @Override
            public Class<?> getObjectType() {
                return UserApi.class;
            }
        };
    }

    @Bean
    FactoryBean<CountryApi> countryApiFactoryBean(ProxyCreator proxyCreator){

        return new FactoryBean<CountryApi>() {
            @Override
            public CountryApi getObject() throws Exception {
                return (CountryApi) proxyCreator.createProxy(CountryApi.class);
            }

            @Override
            public Class<?> getObjectType() {
                return CountryApi.class;
            }
        };
    }

    /**
     * 注入 ProxyCreator 的实现类
     * @return
     */
    @Bean
    ProxyCreator getProxyCreator(){
        return new JDKProxyCreator();
    }

}
