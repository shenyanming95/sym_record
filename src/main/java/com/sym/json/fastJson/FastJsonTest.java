package com.sym.json.fastJson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: shenym
 * @Date: 2018-12-10 9:53
 */
public class FastJsonTest {

    // fastJson转换json数据不需要实例化对象，转换对象用JsonObject，转换数组用JsonArray

    // 转换json时会使用的map数据
    private Map<String, Object> map;

    // 转换json时会使用的list数据
    private List<Map<String, Object>> list;

    // 转换json时会使用的map类型的string数据
    private String mapString;

    // 转换json时会使用的list类型的string数据
    private String listString;

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
    public void testOne() {
        // 转换成json字符串
        String jsonString = JSONObject.toJSONString(map);
        // 转换成json对象
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(map);
        System.out.println(jsonString);
        System.out.println(jsonObject);
    }


    /**
     * 将list数据转为json字符串
     */
    @Test
    public void testTwo(){
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
    public void testThree(){
        // 1、使用类类型
        Map<String,Object> map = JSONObject.parseObject(mapString, Map.class);
        // 2、使用TypeReference<T>
        Map<String,Object> map2 = JSONObject.parseObject(mapString,new TypeReference<Map>(){});
        System.out.println(map);
        System.out.println(map2);
    }


    /**
     * 将json字符串转为list
     */
    @Test
    public void testFour(){
        // 1、使用类类型
        List<Map> list = JSONArray.parseArray(listString,Map.class);
        System.out.println(list);
        // 2、使用TypeReference<T>
        List<Map<String,Object>> list2 = JSONArray.parseObject(listString,new TypeReference<List<Map<String,Object>>>(){});
        System.out.println(list2);
    }
}
