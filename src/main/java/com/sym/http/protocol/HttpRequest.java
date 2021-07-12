package com.sym.http.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * http请求抽象实体
 *
 * @author shenyanming
 * Create on 2021/07/08 10:03
 */
@Data
public class HttpRequest implements Serializable {

    /**
     * 目标地址
     */
    private String url;

    /**
     * 协议头
     */
    private Map<String, String> headers;

    /**
     * 请求参数
     */
    private Map<String, String> parameters;

    /**
     * HTTP协议方法
     */
    public enum HttpMethod {
        GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

        private String method;

        HttpMethod(String method) {
            this.method = method;
        }

        public String getName() {
            return this.method;
        }

        public static Set<HttpMethod> shouldJson = new HashSet<HttpMethod>() {{
            add(POST);
            add(PUT);
        }};
    }


}
