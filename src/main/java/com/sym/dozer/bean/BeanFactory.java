package com.sym.dozer.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ym.shen
 * Created on 2020/5/7 11:14
 */
public class BeanFactory {

    public static SimpleBean simpleBean(){
        SimpleBean simpleBean = new SimpleBean();
        Map<String, Object> map = new HashMap<>();
        map.put("k", 9527);
        simpleBean.setId(10).setName("test")
                .setIntList(Arrays.asList(1,2,3,4,5))
                .setMap(map);
        return simpleBean;
    }
}
