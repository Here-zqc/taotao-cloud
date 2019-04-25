package com.itszt.common;

import java.io.Serializable;

public class EasyUIResult implements Serializable {

    private int status;
    private String message;

    public static final int OK=200;
    public static final int NOT_FOUND=404;
    public static final int ERROR=500;



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
