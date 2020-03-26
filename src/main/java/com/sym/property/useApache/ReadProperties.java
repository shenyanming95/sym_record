package com.sym.property.useApache;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * 使用apache的 commons-configuration 读取properties文件
 *
 * @Auther: shenym
 * @Date: 2018-11-28 9:51
 */
public class ReadProperties {

    private static PropertiesConfiguration propConfig;
    private static String defaultFileName = "property/common-property.properties";

    static {
        init(defaultFileName);
    }

    /**
     * 加载配置文件
     * @param fileName
     */
    private static void init(String fileName){
        try {
            // 默认是以ISO-8859-1的编码方式读取配置，
            propConfig = new PropertiesConfiguration();
            // 设置为utf-8编码方式读取
            propConfig.setEncoding("utf-8");
            // 读取文件，默认是从classpath下开始找
            propConfig.load(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换加载别的配置文件
     * @param fileNames 文件所在路径
     */
    public static void changeProperties(String fileNames){
        init(fileNames);
    }

    /**
     * 获取properties的值
     * @param key
     * @return
     */
    public static String getProp(String key){
        return propConfig.getString(key);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("请输入key:");
            String str = scanner.nextLine();
            System.out.println("获取的值为："+getProp(str));
        }
    }

}
