package com.sym.dozer.bean;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author ym.shen
 * Created on 2020/5/7 11:13
 */
@ToString
@Data
@Accessors(chain = true)
public class SimpleBean {
    private int id;
    private String name;
    private List<Integer> intList;
    private Map<String, Object> map;
}
