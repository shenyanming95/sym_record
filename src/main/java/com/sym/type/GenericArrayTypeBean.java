package com.sym.type;

import com.sym.type.bean.TypeBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数组化类型{@link java.lang.reflect.GenericArrayType}的实体类
 *
 * @author shenyanming
 * Create on 2021/07/09 11:30
 */
@Data
class GenericArrayTypeBean<T extends Serializable> {
    /**
     * 属于 GenericArrayType
     */
    T[] tArray;
    List<String>[] listArray;
    Map<Object, Object>[] mapArray;

    /**
     * 不属于 GenericArrayType
     */
    List<String> list;
    String[] stringArray;
    TypeBean[] ints;
}
