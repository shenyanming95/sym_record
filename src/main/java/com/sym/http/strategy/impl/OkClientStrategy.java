package com.sym.http.strategy.impl;

import com.google.gson.Gson;
import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;
import com.sym.http.strategy.IHttpStrategy;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author shenyanming
 * Create on 2021/07/08 10:59
 */
public class OkClientStrategy implements IHttpStrategy {

    private OkHttpClient client = initAndConfigClient();
    private Gson gson = new Gson();

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return doRequest(buildRequest(httpRequest, HttpRequest.HttpMethod.GET));
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        return doRequest(buildRequest(httpRequest, HttpRequest.HttpMethod.POST));
    }

    @Override
    public HttpResponse put(HttpRequest httpRequest) {
        return doRequest(buildRequest(httpRequest, HttpRequest.HttpMethod.PUT));
    }

    @Override
    public HttpResponse delete(HttpRequest httpRequest) {
        return doRequest(buildRequest(httpRequest, HttpRequest.HttpMethod.DELETE));
    }

    private HttpResponse doRequest(Request request) {
        Response response;
        String data = null;
        try {
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                data = body.string();
            }
        } catch (IOException e) {
            throw new RuntimeException("execute request fail", e);
        }
        HttpResponse.RequestStatus status = response.isSuccessful() ?
                HttpResponse.RequestStatus.SUCCESS :
                HttpResponse.RequestStatus.FAILURE;
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(status);
        httpResponse.setData(data);
        return httpResponse;
    }

    private OkHttpClient initAndConfigClient() {
        // TODO instead of configuration
        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .callTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    private Request buildRequest(HttpRequest httpRequest, HttpRequest.HttpMethod method) {
        Request.Builder builder;
        switch (method) {
            case GET:
                builder = new Request.Builder().get();
                break;
            case POST:
                builder = new Request.Builder().post(buildRequestBody(httpRequest));
                break;
            case PUT:
                builder = new Request.Builder().put(buildRequestBody(httpRequest));
                break;
            case DELETE:
                builder = new Request.Builder().delete();
                break;
            default:
                throw new IllegalArgumentException("invalid method");
        }
        builder.url(httpRequest.getUrl());
        // set headers
        Map<String, String> headers = httpRequest.getHeaders();
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }
        return builder.build();
    }

    private RequestBody buildRequestBody(HttpRequest request) {
        return RequestBody.create(MediaType.get("application/json"),
                gson.toJson(request.getParameters()));
    }
}
