package com.sym.http.okClient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

/**
 * @author ym.shen
 * @date 2019/5/28 16:56
 */
public class OkClientTest {

    /**
     * OkHttpClient可以被重复复用
     */
    @Test
    public void test() throws IOException {
        OkHttpClient okClient = new OkHttpClient();
        for (int i = 0; i < 1000; i++) {
            Request request = new Request.Builder().addHeader("token", "i just test").url("http://127.0.0.1:8080/get/123").get().build();
            Response response = okClient.newCall(request).execute();
            System.out.println(response.code());
        }
    }

    /**
     * 执行1000次耗时：
     * 2019/05/29 --- 3044ms
     */
    @Test
    public void testOne() throws IOException {
        long start = System.currentTimeMillis();
        OkHttpClient httpClient = new OkHttpClient();
        for (int i = 0; i < 1000; i++) {
            Request request = new Request.Builder().get().url("http://127.0.0.1:8080/get/123321").build();
            Response response = httpClient.newCall(request).execute();
            response.code();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

    /**
     * 使用OkClient发送get请求
     */
    @Test
    public void get() throws IOException {
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
     *
     * @throws IOException
     */
    @Test
    public void post() throws IOException {
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
}
