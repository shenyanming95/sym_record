package com.sym.dozer.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author ym.shen
 * Created on 2020/5/7 11:13
 */
@ToString
@Data
public class SimpleBeanDTO {
    private Integer id;
    private String name;
    private List<Integer> intList;
    private Map<String, Object> map;
}
