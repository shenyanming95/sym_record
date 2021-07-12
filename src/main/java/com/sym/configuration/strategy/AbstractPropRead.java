package com.sym.configuration.strategy;

/**
 * @author shenyanming
 * Create on 2021/07/08 20:53
 */
public abstract class AbstractPropRead implements IRead {

    protected static final String SUFFIX_PROPERTIES = ".properties";
    protected static final String SUFFIX_XML = ".xml";

    protected String path;

    protected AbstractPropRead(String path) {
        this.path = path;
        checkFileFormat();
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 是否支持这一文件格式
     */
    protected abstract void checkFileFormat();
}
