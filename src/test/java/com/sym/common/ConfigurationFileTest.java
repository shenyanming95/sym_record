package com.sym.common;

import com.sym.configuration.ConfigurationFileUtil;
import org.junit.Test;

/**
 * @author shenyanming
 * Create on 2021/07/09 10:17
 */
public class ConfigurationFileTest {

    @Test
    public void jdkTest(){
        ConfigurationFileUtil.setStrategy(ConfigurationFileUtil.Strategy.JDK);
        ConfigurationFileUtil.setPath("/property/common-property.properties");
        String value = ConfigurationFileUtil.getValue("good");
        System.out.println("read: " + value);
    }

    @Test
    public void apachePropTest(){
        ConfigurationFileUtil.setStrategy(ConfigurationFileUtil.Strategy.APACHE);
        ConfigurationFileUtil.setPath("/property/common-property.properties");
        String value = ConfigurationFileUtil.getValue("good");
        System.out.println("read: " + value);
    }

    @Test
    public void apacheXmlTest(){
        ConfigurationFileUtil.setStrategy(ConfigurationFileUtil.Strategy.APACHE);
        ConfigurationFileUtil.setPath("/property/common-property.xml");
        System.out.println("read: " + ConfigurationFileUtil.getValue("name"));
        System.out.println("read: " + ConfigurationFileUtil.getValue("student.number"));
    }
}
