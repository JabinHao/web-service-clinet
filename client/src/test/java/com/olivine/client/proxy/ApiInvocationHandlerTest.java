package com.olivine.client.proxy;

import com.olivine.client.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/25 17:26
 */
public class ApiInvocationHandlerTest {

    @Test
    public void testRequestMapping(){
        RequestMethod get = RequestMethod.GET;
        System.out.println(get.name());
    }

    @Test
    public void annotationTest() throws NoSuchMethodException {
        Method method = TestController.class.getMethod("getById", String.class);
        System.out.println(method);
        Annotation[] annotations = method.getDeclaredAnnotations();
        Annotation annotation = annotations[0];
        RequestMapping requestMapping = annotation.getClass().getAnnotation(RequestMapping.class);
        System.out.println(requestMapping);

    }
}
