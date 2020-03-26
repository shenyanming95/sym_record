package com.sym.io.path;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * 解析类路径下的文件
 * <p>
 * https://www.jianshu.com/p/dea6937d65b9
 * <p>
 * Created by shenym on 2019/12/20.
 */
public class PathResolver {

    private ClassLoader classLoader;

    public PathResolver() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PathResolver(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 解析类路径下的所有class文件
     *
     * @param packageName 包路径
     * @return
     */
    public Set<Class> parsePackageName(String packageName) {
        Set<Class> resultSet = new HashSet<>();
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                // 获取文件对象
                File parentFile = new File(url.toURI());
                if (!parentFile.exists()) return Collections.emptySet();
                // 获取当前包路径下的所有文件信息, 可能为文件, 也可能为文件夹
                int i = packageName.lastIndexOf(".");
                this.resolveDirectory(parentFile, packageName.substring(0,i), resultSet);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * 递归处理一个文件File
     *
     * @param file     文件对象
     * @param classSet class集合
     */
    private void resolveDirectory(File file, String packageName, final Set<Class> classSet) {
        if (file.isDirectory()) {
            // 存在子文件夹, 递归处理
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                Arrays.stream(files).forEach(f -> resolveDirectory(f, packageName + "." + file.getName(), classSet));
            }
        } else {
            // 普通文件
            int i = file.getName().indexOf(".class");
            if (i > -1) {
                String className = file.getName().substring(0, i);
                try {
                    classSet.add(classLoader.loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    // ignore
                }
            }
        }
    }
}
