package com.sym.monitor.apache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * @author shenyanming
 * Created on 2021/1/19 08:58
 */
@Slf4j
public class FileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileCreate(File file) {
        log.info("文件创建：{}", file.getPath());
    }

    @Override
    public void onFileChange(File file) {
        log.info("文件变动：{}", file.getPath());
    }
}
