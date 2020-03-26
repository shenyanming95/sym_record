package com.sym.io;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 文件工具类
 * Created by 沈燕明 on 2019/5/14 11:55.
 */
public class FileUtil {

    private static ClassLoader classLoader = FileUtil.class.getClassLoader();

    private  FileUtil(){
    }

    /**
     * 获取类路径下的文件真实路径
     * @param classPath
     * @return
     */
    public static String getRealPath(String classPath){
        URL url = classLoader.getResource(classPath);
        if( url == null ){
            System.out.println(classPath+"，不存在");
            return "";
        }
        try {
            return URLDecoder.decode(url.getFile(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取类路径下的文件的File对象
     * @param classPath
     * @return
     */
    public static File getFile(String classPath){
        String path = getRealPath(classPath);
        if( "".equals(path) ){
            return new File(path);
        }
        return null;
    }

}
