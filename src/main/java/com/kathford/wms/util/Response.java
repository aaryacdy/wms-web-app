package com.kathford.wms.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Object body;
    private String message;
    private boolean success;

    public Response(Object body, String message, boolean success) {
        this.body = body;
        this.message = message;
        this.success = success;
    }
}
