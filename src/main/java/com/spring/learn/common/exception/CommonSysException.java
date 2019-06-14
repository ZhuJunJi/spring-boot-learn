package com.spring.learn.common.exception;

import java.io.Serializable;

/**
 * 通用系统异常
 */
public class CommonSysException extends RuntimeException implements Serializable {

    private ExpCodeEnum codeEnum;

    public CommonSysException(ExpCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }

    public CommonSysException() {

    }
}
