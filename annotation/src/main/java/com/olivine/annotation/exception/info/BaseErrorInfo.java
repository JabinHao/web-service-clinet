package com.olivine.annotation.exception.info;

/**
 * @description: 服务接口类
 * @author: jphao
 * @date: 2021/8/20 16:04
 */
public interface BaseErrorInfo {

    /**
     *  错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}

