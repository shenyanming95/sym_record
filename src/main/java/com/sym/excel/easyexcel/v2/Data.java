package com.sym.excel.easyexcel.v2;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 字段：排序、编码、名称、换购价、总换购量、营销补贴
 *
 */


public class Data {

    @ExcelProperty("字段")
    private String s1;
    @ExcelProperty("排序")
    private String s2;
    @ExcelProperty("编码")
    private String s3;
    @ExcelProperty("名称")
    private String s4;
    @ExcelProperty("换购价")
    private String s5;
    @ExcelProperty("总换购量")
    private String s6;
    @ExcelProperty("营销补贴")
    private String s7;
}
