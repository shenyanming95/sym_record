package com.sym.configuration.strategy.impl;

import com.sym.configuration.strategy.AbstractPropRead;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.nio.charset.StandardCharsets;

/**
 * @author shenyanming
 * Create on 2021/07/08 21:04
 */
public class ApachePropImpl extends AbstractPropRead {

    private PropertiesConfiguration propConfig;
    private XMLConfiguration xmlConfiguration;
    private boolean isProperties;

    public ApachePropImpl(String path) {
        super(path);
    }

    @Override
    protected void init() {
        if ((isProperties = path.endsWith(SUFFIX_PROPERTIES))) {
            initPropertiesConfig();
        } else {
            initXmlConfig();
        }
    }

    @Override
    protected void checkFileFormat() {
        if (path.endsWith(SUFFIX_XML) || (path.endsWith(SUFFIX_PROPERTIES))) {
            return;
        }
        throw new RuntimeException("only support .xml and .properties");
    }

    @Override
    public String getValue(String key) {
        return isProperties ? propConfig.getString(key) : xmlConfiguration.getString(key);
    }

    private void initPropertiesConfig() {
        propConfig = new PropertiesConfiguration();
        propConfig.setEncoding(StandardCharsets.UTF_8.name());
        try {
            // 读取文件, 默认是从classpath下开始找
            propConfig.load(formatPath(path));
        } catch (ConfigurationException e) {
            throw new RuntimeException("load properties [" + path + "] fail", e);
        }
    }

    private void initXmlConfig() {
        xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.setEncoding(StandardCharsets.UTF_8.name());
        try {
            // 读取文件, 默认是从classpath下开始找
            xmlConfiguration.load(formatPath(path));
        } catch (ConfigurationException e) {
            throw new RuntimeException("load xml [" + path + "] fail", e);
        }
    }

    private String formatPath(String path) {
        return path.startsWith("/") ? path.substring(1) : path;
    }
}
