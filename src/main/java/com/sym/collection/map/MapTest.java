package com.sym.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shenym on 2019/10/14.
 */
public class MapTest {

    /**
     * JDK8提供的在map遍历时, 删除元素
     */
    @Test
    public void testOne(){
        Map<String,String> map = new HashMap<>();
        map.put("1","11");
        map.put("2","22");
        map.put("3","33");
        map.entrySet().removeIf(next -> "2".equals(next.getKey()));
        System.out.println(map);
    }


    /**
     * 当 Map 中不存在指定元素时, 创建它并且返回 null;
     * 当 Map 中存在指定元素时, 返回它
     */
    @Test
    public void putIfAbsentTest(){
        Map<String, String> map = new HashMap<>();
        map.put("123", "张三");
        System.out.println(map);

        // Map 中不存在 key=456 的元素, 所以会创建它, 然后返回null
        String data = map.putIfAbsent("456", "李四");
        System.out.println(data);
        System.out.println(map);

        // Map中已经存在 key=456 的元素, 不会创建它, 直接返回原值
        String result = map.putIfAbsent("456", "王五");
        System.out.println(result);
        System.out.println(map);
    }


    /**
     * 当 Map 中不存在指定元素时, 用函数式接口构造它, 返回将其返回
     * 当 Map 中存在指定元素时, 直接返回旧值
     */
    @Test
    public void completeIfAbsentTest(){
        Map<String, Object> map = new HashMap<>();

        // 当Map中不存在key=name的元素时, 调用函数式接口创建它, 然后将其返回;
        // 区别于 putIfAbsent() -- 返回null
        Object name = map.computeIfAbsent("name", (a) -> {
            return a + " - good";
        });
        System.out.println(name);
        System.out.println(map);
    }


    /**
     * 基于 {@link LinkedHashMap}实现的 LRU(最少使用的淘汰机制)算法, 它会把最近最少访问的元素放置在
     * 哈希表的最前面, 即越常访问的元素就会放在哈希表后方...
     */
    @Test
    public void lruBaseOnLinkHashMap(){
        Map<String, String> lruMap = new LinkedHashMap<String, String>(16, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                // LinkedHashMap自带的判断是否删除最老的元素方法，默认返回false，即不删除老数据
                // 重写这个方法，当满足一定条件时删除老数据
                if (size() > 4){
                    System.out.println("最少访问的key：" + eldest.getKey());
                    return true;
                }
                return false;
            }
        };
        // 初始化数据
        lruMap.put("a", "aaa");
        lruMap.put("b", "bbb");
        lruMap.put("c", "ccc");
        lruMap.put("d", "ddd");
        System.out.println("初始化顺序：" + lruMap);

        // 访问 map
        lruMap.get("b");
        System.out.println("访问b后的顺序：" + lruMap);
        lruMap.get("c");
        System.out.println("访问c后的顺序：" + lruMap);

        // 新增数据
        lruMap.put("e", "eee");
        System.out.println("超过指定数量的顺序：" + lruMap);
    }

}
