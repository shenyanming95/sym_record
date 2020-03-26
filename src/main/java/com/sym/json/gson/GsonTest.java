package com.sym.json.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: shenym
 * @Date: 2018-11-30 14:47
 */
public class GsonTest {

    // 使用Gson转换JSON需要实例化一个GSON对象
    private Gson gson;

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
        gson = new Gson();

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
     * 将map数据转为json字符串，
     */
    @Test
    public void testOne(){
        String mapJsonString = gson.toJson(map);
        System.out.println(mapJsonString);
    }

    /**
     * 将list数据转为json字符串
     */
    @Test
    public void testTwo(){
        String listJson = gson.toJson(list);
        System.out.println(listJson);
    }


    /**
     * 将map类型的json字符串转为map
     */
    @Test
    public void testThree(){
        Type type = new TypeToken<Map<String,Object>>(){}.getType();
        Map<String,Object> jsonMap = gson.fromJson(mapString,type);
        System.out.println(jsonMap);
    }

    /**
     * 将list类型的json字符串转为list
     */
    @Test
    public void testFour(){
        Type type = new TypeToken<List<Map<String,Object>>>(){}.getType();
        List<Map<String,Object>> list = gson.fromJson(listString,type);
        System.out.println(list);
    }

}
