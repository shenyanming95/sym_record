package com.sym.excel.easyexcel.v1;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 基于 阿里巴巴的 easyexcel 工具, 读取或者到处Excel
 * github：https://github.com/alibaba/easyexcel
 * 官方文档：https://alibaba-easyexcel.github.io/
 * 注：这个实现旧版本的(1.x版本), 2.x版本API有变化
 *
 * Created by 沈燕明 on 2019/6/25 9:03.
 */
public class EasyexcelV1ExportUtil {

    private final static int MAX_SHEET_COLUMN = 100;//单个sheet假设最大支持100个数据项

    /**
     * 无论数据量多大, 都只会在一个sheet导入, 可能造成OOM异常
     * @param excelTile excel标题
     * @param dataList excel数据项
     * @param out 输出流
     */
    public static void exportSingleSheet(List<List<String>> excelTile,List<List<String>> dataList,OutputStream out){
        ExcelWriter excelWriter = null;
        try{
            excelWriter = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            // 设置excel标题
            Table table = new Table(1);
            table.setHead(excelTile);
            // 设置sheet
            Sheet sheet = new Sheet(1);
            sheet.setAutoWidth(true);
            // 开始写入
            excelWriter.write0(dataList,sheet,table);
        }finally {
            if( null != excelWriter ) excelWriter.finish();
        }
    }


    /**
     * 按批次导出excel, 可以写入到不同的sheet中
     * @param titleList excel标题
     * @param dataList excel数据项
     * @param out 输出流
     */
    public static void exportMoreSheet(List<List<String>> titleList, List<List<String>> dataList, OutputStream out){
        ExcelWriter excelWriter = null;
        try{
            excelWriter = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            // 设置excel标题
            Table table = new Table(1);
            table.setHead(titleList);
            // 设置sheet
            int size  = dataList.size();
            int sheetCounts;
            if( size % MAX_SHEET_COLUMN == 0 ){
                sheetCounts = size/MAX_SHEET_COLUMN;
            }else{
                sheetCounts = size/MAX_SHEET_COLUMN + 1;
            }
            int startIdx;
            int endIdx;
            for( int i = 0;i<sheetCounts;i++ ){
                Sheet sheet = new Sheet(i);
                sheet.setAutoWidth(true);
                startIdx = i*MAX_SHEET_COLUMN;
                if( i < sheetCounts-1 ){
                    endIdx = startIdx + MAX_SHEET_COLUMN;
                }else{
                    endIdx = size - 1 ;
                }
                excelWriter.write0(dataList.subList(startIdx,endIdx),sheet,table);
            }

        }finally {
            if( null != excelWriter ) excelWriter.finish();
        }
    }


    /**
     * 读取一个excel
     * @param stream 输入流
     */
    public static void importExcel(InputStream stream){
        ExcelReader excelReader = new ExcelReader(stream,ExcelTypeEnum.XLSX,new AnalysisEventListener(){
            @Override
            public void invoke(Object data, AnalysisContext context) {
                System.out.println("每读取一行, 都是封装成："+data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                Integer total = context.readSheetHolder().getTotal();//总的行数
                Integer sheetNo = context.readSheetHolder().getSheetNo();//sheet行号
                String sheetName = context.readSheetHolder().getSheetName();//sheet名称
                Integer rowIndex = context.readRowHolder().getRowIndex();//行号
                Object currentRowAnalysisResult = context.readRowHolder().getCurrentRowAnalysisResult();
                System.out.println("所有行读取完毕..");
            }
        });
        excelReader.read();
        excelReader.finish();
    }
}
