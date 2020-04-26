package com.sym.excel.easyexcel.v2;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ym.shen
 * @date 2019/11/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelData {

    /**
     * 此字段不会作为excel标题来映射
     */
    @ExcelIgnore
    private Integer id;

    /**
     * 此字段映射excel的“姓名”列
     */
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("假设我很长很长很长~~")
    private String desc;
}
