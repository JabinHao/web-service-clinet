package com.olivine.client.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/25 16:42
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodInfo {

    // 请求 mapping
    private String uri;

    private HttpMethod method;

    // 参数 PathVariable 与 RequestParam
    private Map<String, Object> params;

    // 请求体
    private Mono body;

    // 请求体类型
    private Class<?> bodyElementType;

    // 返回类型 true：Flux， false：Mono
    private boolean returnFlux;

    // 返回对象类型
    private Class<?> returnElementType;
}
