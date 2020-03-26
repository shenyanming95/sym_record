package com.sym.property.useJDK;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * ResourceBundle：参数给出完整的路径、文件名
 * Properties：参数按照文件的相对位置、完整的文件名(包括后缀)
 *
 * @author Administrator
 */
public class Resource {
    public static String usernam;
    public static String password;

    public String getUserName() {
        //获取用户名用ResourceBundle(资源绑定)类
        ResourceBundle rb = ResourceBundle.getBundle("net.sym.property.res");//给出完整包路径
        return rb.getString("username");
    }

    public String getPassword() {
        //获取密码使用Properties类来获取
        Properties pt = new Properties();
        String password = "";
        try {
            //参数的写法是依据配置文件来写的，如果配置文件与Resource在同一个包内，直接写文件名就行,不在同一个包内，以
            // "/"开始表示src包，按包路径写
            pt.load(Resource.class.getResourceAsStream("res.properties"));
            password = pt.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }
}
