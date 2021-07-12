package com.sym.http.strategy;

import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;

/**
 * HTTP抽象方法
 *
 * @author shenyanming
 * Create on 2021/07/08 10:08
 */
public interface IHttpStrategy {

    /**
     * get请求
     *
     * @param httpRequest 请求对象
     * @return 返回对象
     */
    HttpResponse get(HttpRequest httpRequest);

    /**
     * post请求
     *
     * @param httpRequest 请求对象
     * @return 返回对象
     */
    HttpResponse post(HttpRequest httpRequest);

    /**
     * put请求
     *
     * @param httpRequest 请求对象
     * @return 返回对象
     */
    HttpResponse put(HttpRequest httpRequest);

    /**
     * delete请求
     *
     * @param httpRequest 请求对象
     * @return 返回对象
     */
    HttpResponse delete(HttpRequest httpRequest);
}
