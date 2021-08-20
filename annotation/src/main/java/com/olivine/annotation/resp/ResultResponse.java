package com.olivine.annotation.resp;

import com.alibaba.fastjson.JSONObject;
import com.olivine.annotation.exception.info.BaseErrorInfo;
import com.olivine.annotation.exception.info.ExceptionInfoEnum;
import lombok.Data;

/**
 * @description: 自定义返回信息
 * @author: jphao
 * @date: 2021/8/20 16:25
 */
@Data
public class ResultResponse {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultResponse() {
    }

    public ResultResponse(BaseErrorInfo errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultResponse success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultResponse success(Object data) {
        ResultResponse rb = new ResultResponse();
        rb.setCode(ExceptionInfoEnum.SUCCESS.getResultCode());
        rb.setMessage(ExceptionInfoEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultResponse error(BaseErrorInfo errorInfo) {
        ResultResponse rb = new ResultResponse();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultResponse error(String code, String message) {
        ResultResponse rb = new ResultResponse();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultResponse error( String message) {
        ResultResponse rb = new ResultResponse();
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
