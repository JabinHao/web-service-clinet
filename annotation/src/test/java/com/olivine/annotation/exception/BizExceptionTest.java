package com.olivine.annotation.exception;

import com.olivine.annotation.repository.UserMongoReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/20 17:03
 */
@SpringBootTest
public class BizExceptionTest {

    @BeforeEach
    void init(){

    }

    @Test
    @DisplayName("BizException test")
    public void test(){
        throw new BizException();
    }
}
