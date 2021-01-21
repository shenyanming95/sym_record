package com.sym.proxy.cglib.lazy;

import com.sym.proxy.cglib.lazy.entity.SymLazyEntity;
import net.sf.cglib.proxy.Dispatcher;

import java.math.BigDecimal;

/**
 * Created by shenym on 2020/1/15.
 */
public class ConcreteDispatcher implements Dispatcher {

    @Override
    public Object loadObject() {
        System.out.println("Dispatcher..start");
        SymLazyEntity entity = new SymLazyEntity();
        entity.setId(110).setName("Dispatcher").setRate(1.99).setSalary(BigDecimal.valueOf(8585));
        return entity;
    }

}
