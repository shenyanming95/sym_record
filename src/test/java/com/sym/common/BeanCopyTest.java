package com.sym.common;

import com.sym.beancopy.BeanCopyConfig;
import com.sym.beancopy.BeanCopyUtil;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenyanming
 * Create on 2021/07/07 15:05
 */
@Slf4j
public class BeanCopyTest {

    private SimpleBean bean;

    @Before
    public void before(){
        Map<String, Object> map = new HashMap<>();
        map.put("k", 9527);
        SimpleBean bean = new SimpleBean();
        bean.setId(10).setName("test").setIntList(Arrays.asList(1, 2, 3, 4, 5)).setMap(map);
        this.bean = bean;
    }

    @Test
    public void mapTest(){
        SimpleBeanDTO simpleBeanDTO = BeanCopyUtil.map(bean, SimpleBeanDTO.class);
        log.info("映射后：{}", simpleBeanDTO);
    }

    @Test
    public void copyTest(){
        // 准备待拷贝bean
        SimpleBeanDTO simpleBeanDTO = new SimpleBeanDTO();

        // 执行拷贝
        log.info("拷贝前: {}", simpleBeanDTO);
        BeanCopyUtil.copy(bean, simpleBeanDTO);
        log.info("拷贝后: {}", simpleBeanDTO);
    }

    @Test
    public void resetTest(){
        BeanCopyUtil.reset(BeanCopyConfig.Strategy.MODEL_MAPPER);
        log.info("映射后：{}", BeanCopyUtil.map(bean, SimpleBeanDTO.class));
    }

    @Data
    @ToString
    @Accessors(chain = true)
    public static class SimpleBean {
        private int id;
        private String name;
        private List<Integer> intList;
        private Map<String, Object> map;
    }

    @Data
    @ToString
    public static class SimpleBeanDTO {
        private Integer id;
        private String name;
        private List<Integer> intList;
        private Map<String, Object> map;
    }
}
