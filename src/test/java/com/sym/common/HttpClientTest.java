package com.sym.common;

import com.sym.http.HttpUtil;
import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;
import okhttp3.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author shenyanming
 * Create on 2021/07/08 11:10
 */
public class HttpClientTest {

    @Test
    public void jdkTest() {
        HttpUtil.setStrategy(HttpUtil.Strategy.JDK);
        // 准备request
        HttpRequest request = new HttpRequest();
        HttpResponse response = HttpUtil.get(request);

        System.out.println(response.getData());
    }

    /**
     * 发送get请求.
     * HttpURLConnection默认的请求方式就是GET请求，但是需要注意一个地方：如果是以GET请求
     * 直接在url中拼接参数，不要用输出流去写入数据，不然它会改为POST请求
     */
    @Test
    public void jdkGetTest() throws IOException {
        String api = "http://127.0.0.1:8080/get/110";
        URL url = new URL(api);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        //设置http请求方式，默认就是GET方式
        httpURLConnection.setRequestMethod("GET");
        //设置Http请求头信息
        httpURLConnection.setRequestProperty("token", "123456");
        //设置连接超时时间，单位毫秒
        httpURLConnection.setConnectTimeout(10000);
        //设置读取超时时间，单位毫秒
        httpURLConnection.setReadTimeout(5000);
        // 建立socket连接
        httpURLConnection.connect();
        // 真正发起连接
        InputStream inputStream;
        if (httpURLConnection.getResponseCode() == 200) { //请求成功
            inputStream = httpURLConnection.getInputStream();
        } else { //请求失败
            inputStream = httpURLConnection.getErrorStream();
        }
        // 处理返回结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.println("result=" + result);
    }

    /**
     * 发送post请求.
     * 使用HttpURLConnection发送POST请求，需要手动指定requestMethod为POST。然后通过输出流发送请求参数
     */
    @Test
    public void jdkPostTest() throws IOException {
        String api = "http://127.0.0.1:8080/post/123";
        URL url = new URL(api);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        // 设置http请求方式
        httpURLConnection.setRequestMethod("POST");
        //设置Http请求头信息
        httpURLConnection.setRequestProperty("token", "123456");
        //允许输出流写入数据
        httpURLConnection.setDoOutput(true);
        // 设置请求参数，可以直接使用输出流写数据，也可以用包装流写入数据
        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
        printWriter.println("\"id\":1111");
        // 建立socket连接
        httpURLConnection.connect();
        // 真正发起连接
        InputStream inputStream;
        if (httpURLConnection.getResponseCode() == 200) {
            //请求成功
            inputStream = httpURLConnection.getInputStream();
        } else { //请求失败
            inputStream = httpURLConnection.getErrorStream();
        }
        // 如果不加上下面这行代码，只会读出一点点，为什么？？？
        inputStream.available();
        StringBuilder sb = new StringBuilder();
        byte[] temp = new byte[1024];
        while (inputStream.read(temp) != -1) {
            sb.append(new String(temp, StandardCharsets.UTF_8));
        }
        System.out.println("result=" + sb.toString());
    }


    @Test
    public void okClientTest() {
        HttpUtil.setStrategy(HttpUtil.Strategy.OK_CLINT);
        // 准备request
        HttpRequest request = new HttpRequest();
        HttpResponse response = HttpUtil.get(request);

        System.out.println(response.getData());
    }

    /**
     * 使用OkClient发送get请求
     */
    @Test
    public void okClientGetTest() throws IOException {
        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        // 创建请求体
        Request request = new Request.Builder().get().url("http://127.0.0.1:8080/get/123321").addHeader("token", "yes i am a token").tag("Tag").build();
        // 发起请求
        Response response = okHttpClient.newCall(request).execute();
        // 处理返回结果
        System.out.println(response.code());
        System.out.println(response.isSuccessful());
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.string());
    }

    /**
     * 使用OkClient发送post请求
     */
    @Test
    public void okClientPostTest() throws IOException {
        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        // RequestBody请求体, 这个就好比使用springMVC的@RequestBody注解, 它会将JSON串解析成一个对象
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "{\"id\":1}");
        // 创建请求体
        Request request = new Request.Builder()
                //而post请求的其它参数, 跟get请求一样, 拼接在url后面即可
                .post(body).url("http://127.0.0.1:8080/post/123321?id=110&name=123")
                .addHeader("token", "yes i am a token").tag("Tag").build();
        // 发起请求
        Response response = okHttpClient.newCall(request).execute();
        // 处理返回结果
        System.out.println(response.code());
        System.out.println(response.isSuccessful());
        Reader reader = response.body().charStream();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
        } finally {
            reader.close();
        }
    }

    @Test
    public void apacheHttpClientTest() {
        HttpUtil.setStrategy(HttpUtil.Strategy.APACHE_HTTP_CLIENT);
        // 准备request
        HttpRequest request = new HttpRequest();
        HttpResponse response = HttpUtil.get(request);

        System.out.println(response.getData());
    }

    /**
     * 使用HttpClient发起get请求
     */
    @Test
    public void apacheHttpClientGetTest() throws IOException {
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
    public void apacheHttpClientPostTest() {
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
