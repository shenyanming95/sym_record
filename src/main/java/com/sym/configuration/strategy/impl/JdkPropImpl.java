package com.sym.configuration.strategy.impl;

import com.sym.configuration.strategy.AbstractPropRead;

import java.io.IOException;
import java.util.Properties;

/**
 * @author shenyanming
 * Create on 2021/07/08 20:52
 */
public class JdkPropImpl extends AbstractPropRead {

    private Properties properties;

    public JdkPropImpl(String path) {
        super(path);
    }

    @Override
    protected void init() {
        properties = new Properties();
        try {
            // 参数的写法是依据配置文件来写, 如果配置文件与当前类在同一个包内,
            // 直接写文件名就行,不在同一个包内，以"/"开始表示src包, 按包路径写.
            properties.load(JdkPropImpl.class.getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("load properties fail", e);
        }
    }

    @Override
    protected void checkFileFormat() {
        if (!path.endsWith(SUFFIX_PROPERTIES)) {
            throw new RuntimeException("only support .properties");
        }
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
