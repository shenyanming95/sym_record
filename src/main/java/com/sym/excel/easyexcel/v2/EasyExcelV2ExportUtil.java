package com.sym.excel.easyexcel.v2;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 基于 阿里巴巴的 easyexcel 工具, 读取或者到处Excel
 * github：https://github.com/alibaba/easyexcel
 * 官方文档：https://alibaba-easyexcel.github.io/
 * 新版本(2.x)API有变化
 * <p>
 * Created by shenym on 2019/10/31.
 */
public class EasyExcelV2ExportUtil {


    /**
     * 导出excel
     *
     * @param outputStream 输出对象
     * @param headClass    excel标题信息, 可以是一个类如：{@link ExcelData}, 也可以是List集合
     * @param dataList     数据集, 泛型允许为Object, easyExcel会使用它自带的转换器{@link com.alibaba.excel.converters.Converter}去把Object转换为
     *                     对应的类型; 当然也可以直接全部使用String来替换, 如List<List<String>>
     */
    public static <T> void exportExcel(OutputStream outputStream, Class<T> headClass, List<T> dataList) {
        EasyExcel.write(outputStream, headClass).sheet("自定义")// 设置输出流+标题
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())//注册写处理器, 这个类是easyExcel的自动列宽
                .doWrite(dataList);//使用doWrite()会自动关闭流
    }


    /**
     * 导出excel
     *
     * @param outputStream 输出对象
     * @param titleList    excel标题信息, 可以是一个类如：{@link ExcelData}, 也可以是List集合
     * @param dataList     数据集, 泛型允许为Object, easyExcel会使用它自带的转换器{@link com.alibaba.excel.converters.Converter}去把Object转换为
     *                     对应的类型; 当然也可以直接全部使用String来替换, 如List<List<String>>
     */
    public static void exportExcel(OutputStream outputStream, List<List<String>> titleList, List<?> dataList) {
        EasyExcel.write(outputStream).sheet().head(titleList).doWrite(dataList);
    }


    /**
     * 导入excel
     *
     * @param inputStream  输入对象
     * @param readListener 监听器, 一般用来对大容量的Excel做批次处理, 避免一次性加载太多数据导致内存GG
     * @param headClass    设置标题行
     */
    public static void importExcel(InputStream inputStream, ReadListener readListener, Class headClass) {
        EasyExcel.read(inputStream, readListener)
                .sheet(0) //读取哪一个sheet
                .head(headClass) //设置excel标题
                .doRead();
    }
}
