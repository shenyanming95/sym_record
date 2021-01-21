package com.sym.reflection.type.genericArrayType;

import com.sym.reflection.domain.LabelEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数组化类型{@link java.lang.reflect.GenericArrayType}的实体类
 * <p>
 * Created by shenym on 2019/12/31.
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
    LabelEntity[] ints;
}
