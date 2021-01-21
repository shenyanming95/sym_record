package com.sym.proxy.cglib.lazy;

import com.sym.proxy.cglib.lazy.entity.SymCommonEntity;
import org.junit.Test;

/**
 * Created by shenym on 2020/1/15.
 */
public class LazyTest {

    /**
     * 测试延迟加载
     */
    @Test
    public void firstTest(){
        SymCommonEntity commonEntity = new SymCommonEntity(120, "测试");
        System.out.println(commonEntity.getEntityByLazyLoader());
        System.out.println(commonEntity.getEntityByDispatcher());
        System.out.println();
        System.out.println(commonEntity.getEntityByLazyLoader());
        System.out.println(commonEntity.getEntityByDispatcher());
    }
}
