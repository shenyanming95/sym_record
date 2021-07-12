package com.sym.configuration.strategy;

/**
 * 定义读取接口
 *
 * @author shenyanming
 * Create on 2021/07/08 20:50
 */
public interface IRead {

    /**
     * 获取属性值
     *
     * @param key 键
     * @return 值
     */
    String getValue(String key);
}
