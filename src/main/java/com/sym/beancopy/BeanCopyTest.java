package com.sym.beancopy;

import com.sym.beancopy.bean.SimpleBean;
import com.sym.beancopy.bean.SimpleBeanDTO;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyanming
 * Created on 2021/5/16 19:59.
 */
@Slf4j
public class BeanCopyTest {

    private DozerBeanMapper dozerBeanMapper;
    private ModelMapper modelMapper;
    private SimpleBean bean;

    @Before
    public void before() {
        dozerBeanMapper = new DozerBeanMapper();
        modelMapper = new ModelMapper();
        bean = new SimpleBean();
        Map<String, Object> map = new HashMap<>();
        map.put("k", 9527);
        bean.setId(10).setName("test").setIntList(Arrays.asList(1, 2, 3, 4, 5)).setMap(map);
    }

    /**
     * 变量名称相同的bean拷贝
     */
    @Test
    public void test01() {
        SimpleBeanDTO simpleBeanDTO = new SimpleBeanDTO();
        log.info("拷贝前: {}", simpleBeanDTO);
        dozerBeanMapper.map(bean, simpleBeanDTO);
        log.info("拷贝后: {}", simpleBeanDTO);
    }

    /**
     * bean变量与Class的映射
     */
    @Test
    public void test02() {
        SimpleBeanDTO simpleBeanDTO = dozerBeanMapper.map(bean, SimpleBeanDTO.class);
        log.info("映射后：{}", simpleBeanDTO);
    }

    @Test
    public void test03() {
        log.info("映射后：{}", modelMapper.map(bean, SimpleBeanDTO.class));
    }
}
