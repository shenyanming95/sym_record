package com.sym.proxy.cglib.lazy;

import com.sym.proxy.cglib.lazy.entity.SymLazyEntity;
import net.sf.cglib.proxy.LazyLoader;

import java.math.BigDecimal;

/**
 * Created by shenym on 2020/1/15.
 */
public class ConcreteLazyLoader implements LazyLoader {

    @Override
    public Object loadObject() {
        System.out.println("LazyLoader..start");
        SymLazyEntity entity = new SymLazyEntity();
        entity.setId(110).setName("LazyLoader").setRate(1.99).setSalary(BigDecimal.valueOf(8585));
        return entity;
    }

}
