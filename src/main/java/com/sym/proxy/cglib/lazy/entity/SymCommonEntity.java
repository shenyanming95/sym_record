package com.sym.proxy.cglib.lazy.entity;

import com.sym.proxy.cglib.lazy.ConcreteDispatcher;
import com.sym.proxy.cglib.lazy.ConcreteLazyLoader;
import lombok.Data;
import lombok.ToString;
import net.sf.cglib.proxy.Enhancer;

/**
 *
 *
 * Created by shenym on 2020/1/15.
 */
@Data
@ToString
public class SymCommonEntity {
    private Integer commonId;
    private String commonDesc;
    private SymLazyEntity entityByLazyLoader;
    private SymLazyEntity entityByDispatcher;

    public SymCommonEntity(Integer commonId, String commonDesc){
        this.commonId = commonId;
        this.commonDesc = commonDesc;
        this.entityByLazyLoader = createEntityByLazyLoader();
        this.entityByDispatcher = createEntityByDispatcher();
    }

    /**
     * 使用cglib进行懒加载, 对需要延迟加载的对象添加代理, 在获取该对象属性时先通过代理类回调方法进行对象初始化.
     * 在不需要加载该对象时, 只要不去获取该对象内属性, 该对象就不会被初始化了
     * (在CGLib的实现中只要去访问该对象内属性的getter方法, 就会自动触发代理类回调).
     */

    private SymLazyEntity createEntityByLazyLoader() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SymLazyEntity.class);
        return (SymLazyEntity) Enhancer.create(SymLazyEntity.class, new ConcreteLazyLoader());
    }

    private SymLazyEntity createEntityByDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SymLazyEntity.class);
        return (SymLazyEntity) Enhancer.create(SymLazyEntity.class, new ConcreteDispatcher());
    }
}
