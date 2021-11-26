package com.ziyear.volcano.exception;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-30 12:54
 */
public class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Exception e) {
        super(msg + " because of " + e.toString());
    }
}
