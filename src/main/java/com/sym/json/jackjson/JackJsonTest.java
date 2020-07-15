package com.sym.json.jackjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: shenym
 * @Date: 2018-11-30 15:17
 */
public class JackJsonTest {

    // 使用JackJson需要使用到objectMapper
    private ObjectMapper objectMapper;

    // 转换json时会使用的map数据
    private Map<String,Object> map;

    // 转换json时会使用的list数据
    private List<Map<String,Object>> list;

    // 转换json时会使用的map类型的string数据
    private String mapString;

    // 转换json时会使用的list类型的string数据
    private String listString;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();

        map = new HashMap<>();
        map.put("id","3146017029");
        map.put("name","张三");
        map.put("isDel",false);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("abcd","good job");
        map2.put("qydm","3146017028");
        map2.put("ttes","机构代码");

        list = new ArrayList<>(2);
        list.add(map);
        list.add(map2);

        mapString = "{\"name\":\"张三\",\"id\":\"3146017029\",\"isDel\":false}";
        listString = "[{\"name\":\"张三\",\"id\":\"3146017029\",\"isDel\":false},{\"qydm\":\"3146017028\",\"ttes\":\"机构代码\",\"abcd\":\"good job\"}]\n";
    }

    /**
     * map数据转为字符串
     */
    @Test
    public void testOne(){
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
    public void testTwo(){
        try {
            String string = objectMapper.writeValueAsString(list);
            System.out.println(string);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 将map类型的json字符串转为map
     *
     * jackJson的readValue()方法可以转换实体类和普通的集合类
     */
    @Test
    public void testThree(){
        try {
            Map<String,Object> map = objectMapper.readValue(mapString, Map.class);
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将list类型的json字符串转为list
     *
     * 如果是复杂类型的转换，需要我们自定义com.fasterxml.jackson.databind.JavaType
     * 例如当转换为List<Map>，需要定义成 List.class,Map.class，先是集合的类类型，再是集合里面元素的类类型
     */
    @Test
    public void testFour(){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,Map.class);
        try {
            List<Map<String,Object>> readValue = objectMapper.readValue(listString, javaType);
            System.out.println(readValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
