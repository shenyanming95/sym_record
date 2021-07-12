package com.sym.type;

import com.sym.type.bean.TypeBean;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 表示参数化类型{@link java.lang.reflect.ParameterizedType}
 *
 * @author shenyanming
 * Create on 2021/07/09 11:31
 */
@Data
class ParameterizedTypeBean {

    /**
     * 属于 ParameterizedType
     */
    Map<String, TypeBean> map;
    Map.Entry<String, String> entry;
    Set<String> set;
    Class<?> clz;
    List<String> list;
    // 嵌套的泛型
    List<List<String>> nestedList;
    TypeBean<Integer> typeBean;


    /**
     * 不属于ParameterizedType
     */
    String str;
    Integer i;
    Set<?> aSet;
    List<?> aList;

}
