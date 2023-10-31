package com.example.springboot.ex;

/**
 * @Author yangbohan
 * @Date 2023/10/26 17:36
 */

public class ServiceException extends Exception{

    public ServiceException(String msg){
        super(msg);
    }

    public ServiceException(String msg, Throwable cause){
        super(msg, cause);
    }
}
