package com.sym.dozer;

import com.sym.dozer.bean.BeanFactory;
import com.sym.dozer.bean.SimpleBean;
import com.sym.dozer.bean.SimpleBeanDTO;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ym.shen
 * Created on 2020/5/7 11:09
 */
@Slf4j
public class DozerTest {

    private Mapper mapper;

    @Before
    public void before() {
        mapper = new DozerBeanMapper();
    }

    /**
     * 变量名称相同的bean拷贝
     */
    @Test
    public void test01() {
        SimpleBean simpleBean = BeanFactory.simpleBean();
        SimpleBeanDTO simpleBeanDTO = new SimpleBeanDTO();

        log.info("拷贝前: {}", simpleBeanDTO);
        mapper.map(simpleBean, simpleBeanDTO);
        log.info("拷贝后: {}", simpleBeanDTO);
    }

    /**
     * bean变量与Class的映射
     */
    @Test
    public void test02() {
        SimpleBean simpleBean = BeanFactory.simpleBean();
        SimpleBeanDTO simpleBeanDTO = mapper.map(simpleBean, SimpleBeanDTO.class);
        log.info("映射后：{}", simpleBeanDTO);
    }
}
