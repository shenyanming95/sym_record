package com.sym.http.strategy.impl;

import com.google.gson.Gson;
import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;
import com.sym.http.strategy.IHttpStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 使用 apache httpClient 发起Http请求
 * 参考教程地址：https://www.yiibai.com/apache_httpclient
 * <p>
 * 每次请求都重新创建一个 CloseableHttpClient 对象，而不是将一个 CloseableHttpClient 复用多次，
 *
 * @author shenyanming
 * Create on 2021/07/08 10:59
 */
public class ApacheHttpClientStrategy implements IHttpStrategy {

    private Gson gson = new Gson();

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return doRequest(adapterRequest(httpRequest, HttpRequest.HttpMethod.GET));
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        return doRequest(adapterRequest(httpRequest, HttpRequest.HttpMethod.POST));
    }

    @Override
    public HttpResponse put(HttpRequest httpRequest) {
        return doRequest(adapterRequest(httpRequest, HttpRequest.HttpMethod.PUT));
    }

    @Override
    public HttpResponse delete(HttpRequest httpRequest) {
        return doRequest(adapterRequest(httpRequest, HttpRequest.HttpMethod.DELETE));
    }

    private HttpResponse doRequest(HttpUriRequest request) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response;
        InputStream inputStream = null;
        String data = null;
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (inputStream.read(temp) != -1) {
                sb.append(new String(temp, StandardCharsets.UTF_8));
            }
            data = sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("execute request fail", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        HttpResponse httpResponse = new HttpResponse();
        // 响应状态
        HttpResponse.RequestStatus status = response.getStatusLine().getStatusCode() == 200 ?
                HttpResponse.RequestStatus.SUCCESS :
                HttpResponse.RequestStatus.FAILURE;
        httpResponse.setStatus(status);
        // 数据
        httpResponse.setData(data);
        return httpResponse;
    }

    private HttpUriRequest adapterRequest(HttpRequest httpRequest, HttpRequest.HttpMethod method) {
        switch (method) {
            case GET:
                return new HttpGet(httpRequest.getUrl());
            case POST:
                HttpPost httpPost = new HttpPost(httpRequest.getUrl());
                StringEntity stringEntity = new StringEntity(toJson(httpRequest.getParameters()), "UTF-8");
                httpPost.setEntity(stringEntity);
                return httpPost;
            case PUT:
                HttpPut httpPut = new HttpPut(httpRequest.getUrl());
                httpPut.setEntity(new StringEntity(toJson(httpRequest.getParameters()), "UTF-8"));
                return httpPut;
            case DELETE:
                return new HttpDelete(httpRequest.getUrl());
            default:
                throw new IllegalArgumentException("invalid request method");
        }
    }

    private String toJson(Map<String, String> params) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        return gson.toJson(params);
    }
}
