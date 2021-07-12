package com.sym.http.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP 返回体
 *
 * @author shenyanming
 * Create on 2021/07/08 10:06
 */
@Data
public class HttpResponse implements Serializable {

    private RequestStatus status;
    private String data;

    public enum RequestStatus{
        SUCCESS, FAILURE
    }
}
