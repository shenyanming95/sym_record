package com.sym.excel.easyexcel.v2;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;

/**
 * Created by shenym on 2019/10/31.
 */
public class EasyExcelListener extends AnalysisEventListener {

    // 这里的变量是可以被使用到的
    private int index = 1;

    @Override
    public void invoke(Object data, AnalysisContext context) {
        // 如果没指定泛型, 默认都是Map
        ReadSheetHolder readSheetHolder = context.readSheetHolder(); //sheet相关内容
        ReadRowHolder readRowHolder = context.readRowHolder(); //单行相关内容
        System.out.println("读取第"+(index++)+"行数据："+data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有行读取成功后, 调用此方法
    }
}
