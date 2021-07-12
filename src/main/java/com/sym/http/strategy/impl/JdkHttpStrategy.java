package com.sym.http.strategy.impl;

import com.google.gson.Gson;
import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;
import com.sym.http.strategy.IHttpStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * JDK api
 *
 * @author shenyanming
 * Create on 2021/07/08 10:11
 */
@Slf4j
public class JdkHttpStrategy implements IHttpStrategy {

    private Gson gson = new Gson();

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return doRequest(httpRequest, HttpRequest.HttpMethod.GET);
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        return doRequest(httpRequest, HttpRequest.HttpMethod.POST);
    }

    @Override
    public HttpResponse put(HttpRequest httpRequest) {
        return doRequest(httpRequest, HttpRequest.HttpMethod.PUT);
    }

    @Override
    public HttpResponse delete(HttpRequest httpRequest) {
        return doRequest(httpRequest, HttpRequest.HttpMethod.DELETE);
    }

    private HttpResponse doRequest(HttpRequest request, HttpRequest.HttpMethod method) {
        HttpURLConnection conn = openAndSetRequestMethod(request.getUrl(), method);
        configConnection(conn);
        setHeaders(conn, request);
        setParameters(conn, request, method);
        try {
            // 开启连接
            conn.connect();
        } catch (IOException e) {
            throw new RuntimeException("connect fail", e);
        }
        // 处理请求结果
        return handleResponse(conn);
    }


    private HttpURLConnection openAndSetRequestMethod(String url, HttpRequest.HttpMethod method) {
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(method.getName());
            return connection;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("illegal url of " + url);
        } catch (ProtocolException e) {
            throw new IllegalArgumentException("illegal http method of " + method);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configConnection(HttpURLConnection connection) {
        // TODO instead of config
        connection.setConnectTimeout(60000);
        connection.setReadTimeout(60000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    private void setHeaders(HttpURLConnection conn, HttpRequest request) {
        Map<String, String> headers = request.getHeaders();
        if (Objects.isNull(headers) || headers.isEmpty()) {
            return;
        }
        // 通过设置 Connection: Keep-Alive 来复用底层socket
        conn.setRequestProperty("Connection", "Keep-Alive");
        headers.forEach(conn::setRequestProperty);
    }

    private void setParameters(HttpURLConnection conn, HttpRequest request, HttpRequest.HttpMethod method) {
        Map<String, String> parameters = request.getParameters();
        if (!HttpRequest.HttpMethod.shouldJson.contains(method)) {
            return;
        }
        if (Objects.isNull(parameters) || parameters.isEmpty()) {
            return;
        }
        try (PrintWriter printWriter = new PrintWriter(conn.getOutputStream())) {
            printWriter.println(gson.toJson(parameters));
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse handleResponse(HttpURLConnection conn) {
        BufferedReader bufferedReader = null;
        HttpResponse.RequestStatus status;
        StringBuilder sb = new StringBuilder();
        try {
            int code = conn.getResponseCode();

            if (code == 200) {
                status = HttpResponse.RequestStatus.SUCCESS;
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                //请求失败
                status = HttpResponse.RequestStatus.FAILURE;
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("handle response fail", e);
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
        HttpResponse response = new HttpResponse();
        response.setStatus(status);
        response.setData(sb.toString());
        return response;
    }
}
