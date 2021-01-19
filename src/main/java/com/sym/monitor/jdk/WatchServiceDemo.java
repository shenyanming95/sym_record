package com.sym.monitor.jdk;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * {@link WatchService}采用扫描式, 效率低, 且不能监听多级目录, 要求父目录需要存在
 *
 *
 * @author shenyanming
 * Created on 2021/1/19 08:41
 */
@Slf4j
public class WatchServiceDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "/Users/shenyanming/workspace_test";
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(filePath);
        path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE, //文件创建事件
                StandardWatchEventKinds.ENTRY_MODIFY, //文件更新事件
                StandardWatchEventKinds.ENTRY_DELETE, //文件删除事件
                StandardWatchEventKinds.OVERFLOW); //事件丢失

        WatchKey watchKey;
        while((watchKey = watchService.take()) != null){
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                log.info("文件名称：{}", event.context());
                log.info("事件类型：{}", event.kind());
                log.info("事件计数：{}", event.count());
            }
            // 必须重置, 否则无法进行下次监听
            watchKey.reset();
        }
    }
}
