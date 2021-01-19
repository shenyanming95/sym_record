package com.sym.monitor.apache;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * @author shenyanming
 * Created on 2021/1/19 08:57
 */
public class FileListenerDemo {
    public static void main(String[] args) throws Exception {
        String path = "";
        // 创建自定义的监听器
        FileAlterationListener listener = new FileListener();
        // 创建观察者
        FileAlterationObserver observer = new FileAlterationObserver(path);
        // 将监听器添加到观察者中
        observer.addListener(listener);
        // 创建监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor();
        // 将观察者添加到监听器中
        monitor.addObserver(observer);
        // 启动监听器
        monitor.start();
    }
}
