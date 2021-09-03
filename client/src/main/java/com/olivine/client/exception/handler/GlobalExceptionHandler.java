package com.olivine.client.exception.handler;

import com.olivine.client.resp.ResultResponse;
import com.olivine.client.exception.BizException;
import com.olivine.client.exception.info.ExceptionInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.netty.http.server.HttpServerRequest;

/**
 * @description: 全局异常处理类
 * @author: jphao
 * @date: 2021/8/20 16:52
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultResponse bizExceptionHandler(BizException e, HttpServerRequest req){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return ResultResponse.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultResponse nullPointerExceptionHandler(NullPointerException e, HttpServerRequest req){
        log.error("发生空指针异常！原因是:",e);
        return ResultResponse.error(ExceptionInfoEnum.BODY_NOT_MATCH);
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultResponse exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return ResultResponse.error(ExceptionInfoEnum.INTERNAL_SERVER_ERROR);
    }
}
