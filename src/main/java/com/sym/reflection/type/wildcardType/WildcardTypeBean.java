package com.sym.reflection.type.wildcardType;

import com.sym.reflection.domain.SubClassEntity;
import com.sym.reflection.domain.TypeBeanEntity;
import lombok.Data;

import java.util.List;

/**
 * 泛型表达式的类型{@link java.lang.reflect.WildcardType}, 有两种表达式：
 * "<? super SubClassEntity>" 意味 ? 代表的类必须是 SubClassEntity 的父类;
 * "<? extends SubClassEntity>" 意味 ? 代表的类必须是 SubClassEntity 的子类.
 * <p>
 * Created by shenym on 2019/12/31.
 */
@Data
class WildcardTypeBean {

    // 上边界为Object, 下边界为SubClassEntity(表达式只规定为SubClassEntity的父类, 而Object是所有对象的父类, 所以上限就到Object)
    List<? super SubClassEntity> list;

    // 上边界为SubClassEntity, 没有下边界(表达式规定只能为SubClassEntity的子类, 所以上限就只能为SubClassEntity, 而子类可以无限扩展, 所以无下限)
    TypeBeanEntity<? extends SubClassEntity> entity;
}
