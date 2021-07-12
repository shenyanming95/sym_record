package com.sym.quickstart;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author shenyanming
 * Create on 2021/07/08 16:00
 */
public class JsonTest {

    private Map<String, Object> map;
    private List<Map<String, Object>> list;
    private String mapString;
    private String listString;

    private Gson gson = new Gson();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        map = new HashMap<>();
        map.put("id", "3146017029");
        map.put("name", "张三");
        map.put("isDel", false);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("abcd", "good job");
        map2.put("qydm", "3146017028");
        map2.put("ttes", "机构代码");

        list = new ArrayList<>(2);
        list.add(map);
        list.add(map2);

        mapString = "{\"name\":\"张三\",\"id\":\"3146017029\",\"isDel\":false}";
        listString = "[{\"name\":\"张三\",\"id\":\"3146017029\",\"isDel\":false},{\"qydm\":\"3146017028\",\"ttes\":\"机构代码\",\"abcd\":\"good job\"}]\n";
    }

    /**
     * 将map数据转为json字符串
     */
    @Test
    public void fastJsonMap2String() {
        // 转换成json字符串
        String jsonString = JSONObject.toJSONString(map);
        // 转换成json对象
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        System.out.println(jsonString);
        System.out.println(jsonObject);
    }

    /**
     * 将list数据转为json字符串
     */
    @Test
    public void fastJsonList2String() {
        // 转换为json字符串
        String jsonString = JSONArray.toJSONString(list);
        // 转换成json数组对象
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        System.out.println(jsonString);
        System.out.println(jsonArray);
    }

    /**
     * 将json字符串转为map
     */
    @Test
    public void fastJsonString2Map() {
        // 1、使用类类型
        Map<String, Object> map = JSONObject.parseObject(mapString, Map.class);
        // 2、使用TypeReference<T>
        Map<String, Object> map2 = JSONObject.parseObject(mapString, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(map);
        System.out.println(map2);
    }

    /**
     * 将json字符串转为list
     */
    @Test
    public void fastJsonString2List() {
        // 1、使用类类型
        List<Map> list = JSONArray.parseArray(listString, Map.class);
        System.out.println(list);
        // 2、使用TypeReference<T>
        List<Map<String, Object>> list2 = JSONArray.parseObject(listString, new TypeReference<List<Map<String, Object>>>() {
        });
        System.out.println(list2);
    }

    /**
     * 将map数据转为json字符串，
     */
    @Test
    public void gsonMap2String() {
        String mapJsonString = gson.toJson(map);
        System.out.println(mapJsonString);
    }

    /**
     * 将list数据转为json字符串
     */
    @Test
    public void gsonList2String() {
        String listJson = gson.toJson(list);
        System.out.println(listJson);
    }

    /**
     * 将map类型的json字符串转为map
     */
    @Test
    public void gsonString2Map() {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonMap = gson.fromJson(mapString, type);
        System.out.println(jsonMap);
    }

    /**
     * 将list类型的json字符串转为list
     */
    @Test
    public void gsonString2List() {
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> list = gson.fromJson(listString, type);
        System.out.println(list);
    }

    /**
     * map数据转为字符串
     */
    @Test
    public void jackJsonMap2String() {
        try {
            String string = objectMapper.writeValueAsString(map);
            System.out.println(string);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * list数据转为字符串
     */
    @Test
    public void jackJsonList2String() {
        try {
            String string = objectMapper.writeValueAsString(list);
            System.out.println(string);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将map类型的json字符串转为map
     * <p>
     * jackJson的readValue()方法可以转换实体类和普通的集合类
     */
    @Test
    public void jackJsonString2Map() {
        try {
            Map<String, Object> map = objectMapper.readValue(mapString, Map.class);
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list类型的json字符串转为list
     * <p>
     * 如果是复杂类型的转换，需要我们自定义com.fasterxml.jackson.databind.JavaType
     * 例如当转换为List<Map>，需要定义成 List.class,Map.class，先是集合的类类型，再是集合里面元素的类类型
     */
    @Test
    public void jackJsonString2List() {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Map.class);
        try {
            List<Map<String, Object>> readValue = objectMapper.readValue(listString, javaType);
            System.out.println(readValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
