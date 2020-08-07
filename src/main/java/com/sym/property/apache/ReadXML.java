package com.sym.property.apache;

import org.apache.commons.configuration.XMLConfiguration;

/**
 * 使用apache的 commons-configuration 读取xml文件
 *
 * @Auther: shenym
 * @Date: 2018-11-28 9:51
 */
public class ReadXML {

    private static XMLConfiguration xmlConfiguration;
    private static String defaultFileName = "property/common-property.xml";

    static {
        init(defaultFileName);
    }

    /**
     * 加载文件
     * @param fileName
     */
    private static void init(String fileName){
        try {
            // 默认是以ISO-8859-1编码方式读取配置文件
            xmlConfiguration = new XMLConfiguration();
            // 设置编码为utf-8
            xmlConfiguration.setEncoding("utf-8");
            // 加载配置文件
            xmlConfiguration.load(fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String getProp(String key){
        return xmlConfiguration.getString(key);
    }


    public static void main(String[] args) {
        String id = ReadXML.getProp("id");
        String name = ReadXML.getProp("name");
        System.out.println(id+"\t"+name);
    }

}
