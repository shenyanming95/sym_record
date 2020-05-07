package com.sym.http.apache;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * 使用 apache httpClient 发起Http请求
 * 参考教程地址：https://www.yiibai.com/apache_httpclient
 * <p>
 * 每次请求都重新创建一个 CloseableHttpClient 对象，而不是将一个 CloseableHttpClient 复用多次，
 *
 * @author ym.shen
 * @date 2019/5/28 16:57
 */
public class HttpClientTest {

    /**
     * 使用多个API去配置
     */
    @Test
    public void testOne() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/get/110");


        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        // 返回的头部信息
        Header[] allHeaders = httpResponse.getAllHeaders();
        for (Header h : allHeaders) {
            System.out.println(h.toString());
        }
        // 国际化信息
        Locale locale = httpResponse.getLocale();
        System.out.println(locale.getCountry());

        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        HttpEntity httpEntity = httpResponse.getEntity();
        long contentLength = httpEntity.getContentLength();
        System.out.println("返回的值大小为," + contentLength);


        InputStream inputStream = httpEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
    }


    /**
     * HttpClient不能复用，执行1000次会卡主
     */
    @Test
    public void testTwo() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < 1000; i++) {
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/get/110");
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }


    /**
     * 执行1000次耗时:
     * 2019/05/29 -- 4845ms
     */
    @Test
    public void testThree() throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/get/110");
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            httpResponse.getStatusLine().getStatusCode();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

    /**
     * 使用HttpClient发起get请求
     */
    @Test
    public void get() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            //设置超时时间, 创建一个Get请求
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/get/110");
            // 设置http请求头
            httpGet.setHeader("token", "123456");
            httpGet.setHeader("sessionID", "goodJob");
            // 连接，执行请求
            response = httpClient.execute(httpGet);
            /*
             * 处理请求返回的信息
             */
            // 获取返回头信息
            Header[] responseAllHeaders = response.getAllHeaders();
            System.out.println("返回头部信息,start...");
            for (Header header : responseAllHeaders) {
                System.out.println(header.toString());
            }
            System.out.println("返回头部信息,end...");
            // 返回的Http状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode=" + statusCode);
            // 返回的信息
            HttpEntity httpEntity = response.getEntity();
            inputStream = httpEntity.getContent();
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (inputStream.read(temp) != -1) {
                sb.append(new String(temp, StandardCharsets.UTF_8));
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
            if (response != null) response.close();
            if (inputStream != null) inputStream.close();
        }
    }


    /**
     * 使用HttpClient发起post请求
     */
    @Test
    public void post() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        BufferedReader bufferedReader = null;
        try {
            HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/post/110");
            //设置请求头
            httpPost.setHeader("token", "life is fantastic");
            //设置请求参数
            String json = "{}";
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
            // 连接,执行请求
            response = httpClient.execute(httpPost);
            // 返回的Http状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode=" + statusCode);
            // 返回的内容信息
            InputStream inputStream = response.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) response.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
