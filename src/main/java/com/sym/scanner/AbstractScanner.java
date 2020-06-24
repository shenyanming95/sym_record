package com.sym.scanner;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 包扫描器抽象父类
 *
 * @author shenyanming
 * Created on 2020/6/24 09:17
 */
@Slf4j
public abstract class AbstractScanner implements IScanner {

    private ClassLoader classLoader;
    private final static String CLASS_FILE_SUFFIX = ".class";
    private final static String JAR_PROTOCOL_NAME = "jar";
    private final static String FILE_PROTOCOL_NAME = "file";

    protected AbstractScanner() {
        this(null);
    }

    protected AbstractScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public final List<Class<?>> doScan(String basePackagePath) {
        if (Objects.isNull(basePackagePath) || "".equals(basePackagePath)) {
            return Collections.emptyList();
        }
        // 获取旗下所有的Class
        List<Class<?>> classList = this.scanPathAndParseToClass(basePackagePath);
        // 过滤不满足的Class
        classList.removeIf(aClass -> !filter(aClass));
        return classList;
    }

    /**
     * 根据给定的包目录路径, 获取旗下的所有Class类型
     *
     * @param basePackagePath 包路径
     * @return 该package下的所有Class
     */
    private List<Class<?>> scanPathAndParseToClass(String basePackagePath) {
        // 获取所有的类全名集合
        List<String> classNameList = doScanPath(formatPath(basePackagePath, true));
        return classNameList.stream().map(className -> {
            try {
                return Class.forName(className);
            } catch (Throwable e) {
                log.error("解析Class对象失败: {}", e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 解析路径, 获取父目录对象, 可能为文件夹, 也可能在jar包
     *
     * @param basePackagePath 包路径
     * @return 类全名集合
     */
    private List<String> doScanPath(String basePackagePath) {
        // 获取类加载器
        ClassLoader classLoader = this.getClassLoader();
        // 返回值
        List<String> retList = new ArrayList<>();
        try {
            Enumeration<URL> resources = classLoader.getResources(basePackagePath);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if (FILE_PROTOCOL_NAME.equals(protocol)) {
                    // 说明是文件夹
                    File parentFile = new File(url.getPath());
                    this.getClassNameFromFolder(parentFile, retList, basePackagePath);
                } else if (JAR_PROTOCOL_NAME.equals(protocol)) {
                    // 说明是jar
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = urlConnection.getJarFile();
                    this.getClassNameFromJar(jarFile, retList, basePackagePath);
                } else {
                    log.warn("不支持的协议类型: {}", protocol);
                }
            }
        } catch (IOException e) {
            log.error("解析包路径失败, ", e);
        }
        return retList;
    }

    /**
     * 从文件夹中获取类全名
     *
     * @param parentFile    文件夹目录
     * @param classNameList 类全名集合
     */
    private void getClassNameFromFolder(File parentFile, List<String> classNameList, String basePackagePath) {
        if (parentFile.isDirectory()) {
            // 获取旗下的文件列表
            File[] listFiles = parentFile.listFiles();
            if (Objects.nonNull(listFiles)) {
                List<File> fileList = new ArrayList<>();
                for (File file : listFiles) {
                    // 有可能文件夹的子文件还是文件夹, 所以这边用递归处理
                    this.getFileFromFolderLoop(file, fileList);
                }
                // 将File转换为类全名
                classNameList.addAll(this.parseFileToClassName(fileList, basePackagePath));
            }
        }
    }

    /**
     * 从jar中获取类全名
     *
     * @param jarFile       jar文件
     * @param classNameList 类全名集合
     */
    private void getClassNameFromJar(JarFile jarFile, List<String> classNameList, String basePackagePath) {
        List<JarEntry> jarEntries = jarFile.stream().parallel()
                .filter(jarEntry -> !jarEntry.isDirectory()
                        && jarEntry.getName().contains(basePackagePath)
                        && jarEntry.getName().endsWith(CLASS_FILE_SUFFIX))
                .collect(Collectors.toList());
        // 将JarEntry转换为类全名
        classNameList.addAll(this.parseJarEntityToClassName(jarEntries));
    }

    /**
     * 递归获取一个文件下的所有.class文件
     *
     * @param file   父目录
     * @param result .class文件描述符
     */
    private void getFileFromFolderLoop(File file, List<File> result) {
        if (!file.isDirectory()) {
            if (file.getPath().endsWith(CLASS_FILE_SUFFIX)) {
                result.add(file);
            }
            // 递归终止条件
            return;
        }
        File[] listFiles = file.listFiles();
        if (Objects.nonNull(listFiles)) {
            for (File listFile : listFiles) {
                this.getFileFromFolderLoop(listFile, result);
            }
        }
    }

    /**
     * 将文件集合解析成类全名集合
     *
     * @param fileList        文件集合
     * @param basePackagePath 扫描目录
     * @return 类全名集合
     */
    private List<String> parseFileToClassName(List<File> fileList, String basePackagePath) {
        return fileList.stream().map(file -> {
            String filePath = file.getPath();
            int beginIndex = filePath.indexOf(basePackagePath);
            int endIndex = filePath.lastIndexOf(CLASS_FILE_SUFFIX);
            String substring = filePath.substring(beginIndex, endIndex);
            return this.formatPath(substring, false);
        }).collect(Collectors.toList());
    }

    /**
     * 将JarEntry集合解析成类全名集合
     * @param jarEntries jar信息
     * @return 类全名集合
     */
    private List<String> parseJarEntityToClassName(List<JarEntry> jarEntries) {
        return jarEntries.stream().map(jarEntry -> {
            String name = jarEntry.getName();
            int endIndex = name.lastIndexOf(CLASS_FILE_SUFFIX);
            String substring = name.substring(0, endIndex);
            return this.formatPath(substring, false);
        }).collect(Collectors.toList());
    }

    /**
     * 获取类加载器, 优先级顺序：用户配置 > 当前线程 > 系统默认
     *
     * @return 类加载器
     */
    private ClassLoader getClassLoader() {
        ClassLoader ret = classLoader;
        if (Objects.isNull(ret)) {
            ret = Thread.currentThread().getContextClassLoader();
        }
        if (Objects.isNull(ret)) {
            ret = ClassLoader.getSystemClassLoader();
        }
        return ret;
    }

    /**
     * 转换路径的格式
     *
     * @param path          路径
     * @param toFilePattern true-转换为文件路径格式, 即"."转"/"
     * @return 转换后的路径
     */
    private String formatPath(String path, boolean toFilePattern) {
        if (toFilePattern) {
            path = path.replace(".", "/");
        } else {
            path = path.replace("/", ".");
        }
        return path;
    }

    /**
     * 过滤符合条件的Class类型
     *
     * @param c 待检查的Class
     * @return true-满足要求的class, false-需要被过滤掉
     */
    protected abstract boolean filter(Class<?> c);

}
