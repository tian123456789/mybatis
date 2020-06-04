package com.tgr.springbootmybatis.file.export.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {
    /**
     * Description: 获取单元格数据  返回字符串
     */
    public static String getCellValue2String(Cell cell) {
        String str = "";
        if (cell == null) return str;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                double nbvalue = cell.getNumericCellValue();
                str = String.valueOf(nbvalue);

                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //读取日期
                    double d = cell.getNumericCellValue();
                    Date date = HSSFDateUtil.getJavaDate(d);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    str = sdf.format(date);
                }
                break;

            case Cell.CELL_TYPE_STRING:
                if (cell.getStringCellValue() != null)
                    str = cell.getStringCellValue().trim();
                break;

            default:
        }

        return str;
    }
}
