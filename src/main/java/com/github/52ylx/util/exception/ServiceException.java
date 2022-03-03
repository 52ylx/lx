package com.gitee.ylx.util.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
