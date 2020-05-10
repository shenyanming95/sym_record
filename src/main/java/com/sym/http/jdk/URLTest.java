package com.sym.http.jdk;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * 使用JDK原生的API发起HTTP请求
 * <p>
 * 当 HttpURLConnection 是 "Connection: close " 模式，那么关闭 inputStream 后就会自动断开连接。
 * 当 HttpURLConnection 是 "Connection: Keep-Alive" 模式，那么关闭 inputStream 后，并不会断开底层的 Socket 连接。
 * 这样的好处，是当需要连接到同一服务器地址时，可以复用该 Socket。这时如果要求断开连接，就可以调用 connection.disconnect() 了。
 * <p>
 * <p>
 *
 * @author ym.shen
 * @date 2019/5/28 9:25
 */
public class URLTest {

    /**
     * 发送get请求.
     * HttpURLConnection默认的请求方式就是GET请求，但是需要注意一个地方：如果是以GET请求
     * 直接在url中拼接参数，不要用输出流去写入数据，不然它会改为POST请求
     *
     * @throws IOException
     */
    @Test
    public void getRequest() throws IOException {
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
    public void postRequest() throws IOException {
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

    /**
     * 执行1000次耗时:
     *      2019/05/29 -- 2287ms
     */
    @Test
    public void test(){
        long start = System.currentTimeMillis();
        for( int i=0;i<1000;i++ ){
            JdkHttpUtil.doGet("http://127.0.0.1:8080/get/123",null);
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)+"ms");
    }
}
