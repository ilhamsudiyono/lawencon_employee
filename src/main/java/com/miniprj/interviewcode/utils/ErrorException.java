package com.miniprj.interviewcode.utils;

import java.io.Serializable;

public class ErrorException extends Exception implements Serializable {

	private static final long serialVersionUID = -3757905510151618894L;

	public ErrorException(String message) {
        super(message);
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorException(Throwable cause) {
        super(cause);
    }
}