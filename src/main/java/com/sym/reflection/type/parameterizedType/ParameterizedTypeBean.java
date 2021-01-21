package com.sym.reflection.type.parameterizedType;

import com.sym.reflection.domain.SubClassEntity;
import com.sym.reflection.domain.TypeBeanEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 表示参数化类型{@link java.lang.reflect.ParameterizedType}
 * <p>
 * Created by shenym on 2019/12/31.
 */
@Data
class ParameterizedTypeBean {

    /**
     * 属于 ParameterizedType
     */
    Map<String, SubClassEntity> map;
    Map.Entry<String, String> entry;
    Set<String> set;
    Class<?> clz;
    List<String> list;
    // 嵌套的泛型
    List<List<String>> nestedList;
    TypeBeanEntity<Integer> typeBean;


    /**
     * 不属于ParameterizedType
     */
    String str;
    Integer i;
    Set aSet;
    List aList;

}
