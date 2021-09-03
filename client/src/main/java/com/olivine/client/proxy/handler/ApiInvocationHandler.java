package com.olivine.client.proxy.handler;

import com.olivine.client.bean.MethodInfo;
import com.olivine.client.exception.BizException;
import com.olivine.client.handler.RestHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 代理逻辑，远程调用
 * @author: jphao
 * @date: 2021/8/25 16:34
 */
@Slf4j
@Data
@AllArgsConstructor
public class ApiInvocationHandler implements InvocationHandler {

    private RestHandler handler;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 获取 api 中的请求信息
        MethodInfo methodInfo = extractMethodInfo(method, args);
        log.info("methodInfo: " + methodInfo);

        // 通过 rest handler调用远程接口
        return handler.invokeRest(methodInfo);
    }

    /**
     * 获取 API 中请求方法相关信息
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args){
        MethodInfo methodInfo = new MethodInfo();

        extractUrlAndMethod(method, methodInfo);

        extractRequestParamsAndBody(method, args, methodInfo);

        extractReturnInfo(method, methodInfo);

        return methodInfo;
    }

    /**
     * 获取 请求 mapping 与 HttpMethod
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {

        Map<HttpMethod, Class<?>> mappingMap = new HashMap(){{
            put(HttpMethod.GET, GetMapping.class);
            put(HttpMethod.DELETE, DeleteMapping.class);
            put(HttpMethod.PUT, PutMapping.class);
            put(HttpMethod.POST, PostMapping.class);
            put(HttpMethod.PATCH, PatchMapping.class);
        }};

        Annotation[] annotations = method.getAnnotations();

        for (Annotation annotation : annotations) {

            if (annotation instanceof RequestMapping){

                RequestMapping requestMapping = (RequestMapping) annotation;
                log.info("requestMapping: " + requestMapping);

                String uri = requestMapping.value()[0];
                methodInfo.setUri(uri);

                RequestMethod requestMethod = requestMapping.method()[0]; // 本地API中应当只有一种方法
                HttpMethod httpMethod = HttpMethod.resolve(requestMethod.name());
                methodInfo.setMethod(httpMethod);
            }
            // TODO: don't support GetMapping, PostMapping, etc.
        }
        if (methodInfo.getMethod() == null){
            throw new BizException("60443","annotation RequestMapping was missing");
        }
    }

    /**
     * 获取请求参数与请求体
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractRequestParamsAndBody(Method method, Object[] args, MethodInfo methodInfo){

        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new HashMap<>();

        for (int i = 0; i < parameters.length; i++) {

            PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
            if (annoPath != null){
                params.put(annoPath.value(), args[i]);
            }

            RequestParam annoParam = parameters[i].getAnnotation(RequestParam.class);
            if (annoParam != null){
                params.put(annoParam.value(), args[i]);
            }

            RequestBody annoBody = parameters[i].getAnnotation(RequestBody.class);
            if (annoBody != null){
                methodInfo.setBody((Mono<?>) args[i]);
                methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
            }
        }

        methodInfo.setParams(params);
    }

    /**
     *  获取返回信息
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo){

        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);

        Class<?> returnElementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(returnElementType);
    }

    /**
     * 获取请求体类型
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {

        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();

        return (Class<?>) actualTypeArguments[0];
    }
}
