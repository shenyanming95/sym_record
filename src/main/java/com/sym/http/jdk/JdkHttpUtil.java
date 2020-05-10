package com.sym.http.jdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

/**
 * 一个使用JDK自带的 URL+HttpURLConnection Http请求工具类
 *
 * Created by 沈燕明 on 2019/5/28 15:05.
 */
public class JdkHttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(JdkHttpUtil.class);

    /**
     * get请求
     *
     * @param url           请求地址
     * @param httpHeaderMap 请求头
     * @return
     */
    public static String doGet(String url, Map<String, String> httpHeaderMap) {
        return doGet(url, 5000, 5000, httpHeaderMap);
    }

    /**
     * get请求
     *
     * @param url            请求地址
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param httpHeaderMap  请求头
     * @return
     */
    public static String doGet(String url, Integer connectTimeout, Integer readTimeout, Map<String, String> httpHeaderMap) {
        return doRequest(url, RequestMethod.get, connectTimeout, readTimeout, httpHeaderMap, Collections.emptyMap());
    }

    /**
     * post请求
     *
     * @param url           请求地址
     * @param httpHeaderMap 请求头
     * @param httpParamMap  请求参数
     * @return
     */
    public static String doPost(String url, Map<String, String> httpHeaderMap, Map<String, String> httpParamMap) {
        return doPost(url, 5000, 5000, httpHeaderMap, httpParamMap);
    }

    /**
     * post请求
     *
     * @param url            请求地址
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param httpHeaderMap  请求头
     * @param httpParamMap   请求参数
     * @return
     */
    public static String doPost(String url, Integer connectTimeout, Integer readTimeout, Map<String, String> httpHeaderMap, Map<String, String> httpParamMap) {
        return doRequest(url, RequestMethod.post, connectTimeout, readTimeout, httpHeaderMap, httpParamMap);
    }

    /**
     * 发起一次请求
     *
     * @param url            请求地址
     * @param method         请求方式
     * @param httpHeaderMap  请求头
     * @param httpParamMap   请求参数
     * @param connectTimeout 连接超时设置(ms)
     * @param readTimeout    读取超时设置(ms)
     * @return
     */
    private static String doRequest(String url, RequestMethod method, Integer connectTimeout, Integer readTimeout, Map<String, String> httpHeaderMap, Map<String, String> httpParamMap) {
        logger.info(" ... start http request ... ");
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            logger.info("url={}", url);
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            logger.info("request method={}", method.value());
            conn.setRequestMethod(method.value());//设置请求方式
            if (null != connectTimeout) conn.setConnectTimeout(connectTimeout);//连接超时设置
            if (null != readTimeout) conn.setReadTimeout(readTimeout);//读取超时设置
            // 允许输入输出流操作数据
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // 设置请求头信息
            logger.info("request headers={}", httpHeaderMap);
            if( null != httpHeaderMap ){
                if( !httpHeaderMap.containsKey("Connection") ){
                    // 通过设置 Connection: Keep-Alive 来复用底层socket
                    conn.setRequestProperty("Connection", "Keep-Alive");
                }
                for (Map.Entry entry : httpHeaderMap.entrySet()) {
                    conn.setRequestProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
            // 设置请求参数值
            if (RequestMethod.shouldJson.contains(method)) {
                logger.info("request parameters={}", httpParamMap);
                printWriter = new PrintWriter(conn.getOutputStream());
                printWriter.println(MapToJson(httpParamMap));
                printWriter.flush();
            }
            // 建立socket连接
            conn.connect();
            logger.info("socket connect");
            // 处理请求结果
            int responseCode = conn.getResponseCode();
            logger.info("response code={}", responseCode);
            //请求成功
            if (responseCode == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else { //请求失败
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            logger.info("response result={}", sb.toString());
            logger.info(" ... finish http request ... ");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) printWriter.close();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 请求方式
     */
    enum RequestMethod {
        get("GET"), post("POST"), put("PUT"), delete("DELETE");

        private String method;

        RequestMethod(String method) {
            this.method = method;
        }

        public String value() {
            return this.method;
        }

        public static HashSet<RequestMethod> shouldJson = new HashSet<RequestMethod>() {{
            add(post);
            add(put);
        }};

    }

    /**
     * 将Map数据转换为JSON字符串
     *
     * @param param
     * @return
     */
    private static String MapToJson(Map<String, String> param) {
        if(param == null) return "";
        try {
            return new ObjectMapper().writeValueAsString(param);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 本地测试
     */
    public static void main(String[] args) {
        String result = JdkHttpUtil.doPost("http://127.0.0.1:8080/get/110", null,null);
        System.out.println(result);
    }

}
