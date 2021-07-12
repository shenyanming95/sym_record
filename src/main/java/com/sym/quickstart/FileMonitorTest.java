package com.sym.quickstart;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * 监测文件变动
 *
 * @author shenyanming
 * Create on 2021/07/08 20:40
 */
@Slf4j
public class FileMonitorTest {

    /**
     * {@link WatchService}采用扫描式, 效率低, 且不能监听多级目录, 要求父目录需要存在
     */
    @Test
    public void jdkTest() throws IOException, InterruptedException {
        String filePath = "/Users/shenyanming/workspace_test";
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(filePath);
        path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE, //文件创建事件
                StandardWatchEventKinds.ENTRY_MODIFY, //文件更新事件
                StandardWatchEventKinds.ENTRY_DELETE, //文件删除事件
                StandardWatchEventKinds.OVERFLOW); //事件丢失

        WatchKey watchKey;
        while ((watchKey = watchService.take()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                log.info("文件名称：{}", event.context());
                log.info("事件类型：{}", event.kind());
                log.info("事件计数：{}", event.count());
            }
            // 必须重置, 否则无法进行下次监听
            watchKey.reset();
        }
    }

    @Test
    public void apacheCommonTest() throws Exception {
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

    @Slf4j
    public static class FileListener extends FileAlterationListenerAdaptor {

        @Override
        public void onFileCreate(File file) {
            log.info("文件创建：{}", file.getPath());
        }

        @Override
        public void onFileChange(File file) {
            log.info("文件变动：{}", file.getPath());
        }
    }
}
