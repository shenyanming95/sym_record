package com.sym.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 基于 apache poi 的excel导出工具
 * <p>
 * HSSF：操作Excel 2007之前版本(.xls)格式,生成的EXCEL不经过压缩直接导出
 * XSSF：操作Excel 2007及之后版本(.xlsx)格式,内存占用高于HSSF
 * SXSSF:从POI3.8 beta3开始支持,基于XSSF,低内存占用,专门处理大数据量(建议)
 * <p>
 * 注意：
 * .xls格式的excel(最大行数65536行,最大列数256列)
 * .xlsx格式的excel(最大行数1048576行,最大列数16384列)
 * <p>
 * Created by 沈燕明 on 2019/6/25 9:03.
 */
public class PoiExcelExportUtil {

    /**
     * 设置excel的标题
     */
    private static void initSheetTitle(Sheet sheet, List<String> rowTitleList) {
        // 设置标题：sheet第一行为标题
        Row titleRow = sheet.createRow(0);
        int i = 0;
        if (null == rowTitleList || rowTitleList.size() == 0) throw new IllegalArgumentException("excel标题不能为空");
        for (String rowTitle : rowTitleList) {
            Cell cell = titleRow.createCell(i++);
            cell.setCellValue(rowTitle);
        }
    }

    /**
     * 设置excel的数据
     */
    private static void initSheetMapData(Sheet sheet, List<Map<String, Object>> dataList) {
        // 因为sheet首行被当成标题，所以数据要从下一行开始
        int j = 1;
        for (Map<String, Object> map : dataList) {
            Row dataRow = sheet.createRow(j++);
            int z = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Cell dataRowCell = dataRow.createCell(z++);
                dataRowCell.setCellValue(toString(entry.getValue()));
            }
        }
    }

    /**
     * 设置excel的数据
     */
    private static void initSheetListData(Sheet sheet, List<List<String>> dataList) {
        // 因为sheet首行被当成标题，所以数据要从下一行开始
        int j = 1;
        for (List<String> datas : dataList) {
            Row dataRow = sheet.createRow(j++);
            int z = 0;
            for (String data : datas) {
                Cell dataRowCell = dataRow.createCell(z++);
                dataRowCell.setCellValue(data);
            }
        }
    }

    private static String toString(Object o) {
        String result = "";
        if (null == o) return result;
        else return String.valueOf(o);
    }

    /**
     * 设置excel的标题和数据
     */
    private static void initWorkBook(Workbook workbook, List<String> sheetNameList,
                                     List<String> rowTitleList, List<List<String>> dataList) {
        if (sheetNameList == null || sheetNameList.size() == 0) {
            Sheet sheet = workbook.createSheet();
            initSheetTitle(sheet, rowTitleList);
        } else {
            for (String sheetName : sheetNameList) {
                Sheet sheet = workbook.createSheet(sheetName);
                initSheetTitle(sheet, rowTitleList);
                initSheetListData(sheet, dataList);
            }
        }
    }

    /**
     * 使用 HSSFWorkbook 导出数据
     */
    public static void exportByHSSF(List<String> sheetNameList, List<String> rowTitleList,
                                    List<List<String>> dataList, OutputStream out) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            initWorkBook(workbook, sheetNameList, rowTitleList, dataList);
            workbook.write(out);
        } finally {
            out.close();
            workbook.close();
        }
    }

    /**
     * 使用 SXSSFWorkbook 导出数据
     */
    public static void exportBySXSSF(List<String> sheetNameList, List<String> rowTitleList,
                                     List<List<String>> dataList, OutputStream out) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        try {
            initWorkBook(workbook, sheetNameList, rowTitleList, dataList);
            workbook.write(out);
        } finally {
            out.close();
            workbook.close();
        }
    }


}
