package com.sym.excel;

import com.sym.excel.easyexcel.v1.EasyexcelV1ExportUtil;
import com.sym.excel.easyexcel.v2.EasyExcelListener;
import com.sym.excel.easyexcel.v2.EasyExcelV2ExportUtil;
import com.sym.excel.easyexcel.v2.ExcelData;
import com.sym.excel.easyexcel.v2.SimpleData;
import com.sym.excel.poi.PoiExcelExportUtil;
import com.sym.util.TimeWatch;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 沈燕明 on 2019/6/25 9:25.
 */
public class ExcelExportTest {

    private String titleStr = "主键,姓名,年龄,籍贯";
    private List<List<String>> dataListList;


    /**
     * HSSFWorkbook 最大支持65536行
     * 在允许行数下，HSSFWorkbook比SXSSFWorkbook慢了0.7s左右
     */
    @Test
    public void firstTest() throws IOException {
        initDataMap(65535);
        FileOutputStream fops1 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\1.xls"));
        FileOutputStream fops2 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\2.xlsx"));

        TimeWatch.start();
        PoiExcelExportUtil.exportByHSSF(null,Arrays.asList(titleStr.split(",")),dataListList,fops1);
        TimeWatch.end();

        TimeWatch.start();
        PoiExcelExportUtil.exportBySXSSF(null,Arrays.asList(titleStr.split(",")),dataListList,fops1);
        TimeWatch.end();
    }


    /**
     * SXSSFWorkbook导出100W数据量要8S
     */
    @Test
    public void secondTest() throws IOException {
        FileOutputStream fops2 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\2.xlsx"));
        // 100W的数据量
        initDataMap(1000000);
        TimeWatch.start();
        PoiExcelExportUtil.exportBySXSSF(null,Arrays.asList(titleStr.split(",")),dataListList,fops2);
        TimeWatch.end();
    }


    /**
     * 用 EasyExcel 单个sheet导出8s
     */
    @Test
    public void thirdTest() throws IOException {
        FileOutputStream fops2 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\3.xlsx"));
        // 100W的数据量
        initDataList(1000000);
        TimeWatch.start();
        EasyexcelV1ExportUtil.exportSingleSheet(initExcelTitle(titleStr),dataListList,fops2);
        TimeWatch.end();
    }

    /**
     * 用 EasyExcel 多个sheet批量导出
     */
    @Test
    public void fourthTest() throws IOException {
        FileOutputStream fops2 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\3.xlsx"));
        // 100W的数据量
        initDataList(1000000);
        TimeWatch.start();
        EasyexcelV1ExportUtil.exportMoreSheet(initExcelTitle(titleStr),dataListList,fops2);
        TimeWatch.end();
    }


    /**
     * EasyExcel 新版本导出
     */
    @Test
    public void fifthTest() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        List<ExcelData> dataList = new ArrayList<>();
        dataList.add(new ExcelData(1,"亚索","面对疾风吧"));
        dataList.add(new ExcelData(2,"提莫","正在送命"));
        EasyExcelV2ExportUtil.exportExcel(fos, ExcelData.class, dataList);
    }


    /**
     * EasyExcel 新版本导入
     */
    @Test
    public void sixTest() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        EasyExcelV2ExportUtil.importExcel(fis,new EasyExcelListener(),ExcelData.class);
    }


    /**
     * EasyExcel 新版本导入
     */
    @Test
    public void seventhTest() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        List<SimpleData> dataList = new ArrayList<>();
        dataList.add(new SimpleData(1,"张三"));
        dataList.add(new SimpleData(2,"李四"));
        List<List<String>> titleList = new ArrayList<>();
        titleList.add(Collections.singletonList("标识"));
        titleList.add(Collections.singletonList("名称"));
        EasyExcelV2ExportUtil.exportExcel(fos, titleList, dataList);
    }


    private List<List<String>> initExcelTitle(String str){
        String[] strArray = str.split(",");
        List<List<String>> list = new ArrayList<>(strArray.length);
        for( String s : strArray ){
            list.add(Collections.singletonList(s));
        }
        return list;
    }

    private void initDataMap(int size){
        List<Map<String, Object>> dataMapList = new ArrayList<>(size);
        Map<String,Object> map;
        for( int i=0;i<size;i++ ){
            map = new HashMap<>();
            map.put("id",i);
            map.put("name","张三");
            map.put("age",23);
            map.put("place","厦门");
            dataMapList.add(map);
        }
    }

    private void initDataList(int size){
        dataListList = new ArrayList<>(size);
        for( int i=0;i<size;i++ ){
            dataListList.add(Arrays.asList(i+"","张三","23","2008-10-10"));
        }
    }

}
