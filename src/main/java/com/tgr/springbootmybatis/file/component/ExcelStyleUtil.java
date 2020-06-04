package com.tgr.springbootmybatis.file.component;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

public class ExcelStyleUtil {
    /**
     * 背景为菊黄样式
     */
    public static CellStyle getTitleStyle(Workbook wookbook) {
        CellStyle colorStyle = wookbook.createCellStyle();
        colorStyle.setBorderLeft(BorderStyle.THIN);
        colorStyle.setBorderTop(BorderStyle.THIN);
        colorStyle.setBorderRight(BorderStyle.THIN);
        colorStyle.setBorderBottom(BorderStyle.THIN);
        colorStyle.setFillPattern(FillPatternType.BRICKS);
        colorStyle.setAlignment(HorizontalAlignment.CENTER);
        colorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        colorStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        colorStyle.setFillBackgroundColor(HSSFColor.LIGHT_ORANGE.index);
        return colorStyle;
    }

    /**
     * 普通单元格样式
     */
    public static CellStyle getCellStyle(Workbook wookbook) {
        CellStyle colorStyle = wookbook.createCellStyle();
        colorStyle.setBorderLeft(BorderStyle.THIN);
        colorStyle.setBorderTop(BorderStyle.THIN);
        colorStyle.setBorderRight(BorderStyle.THIN);
        colorStyle.setBorderBottom(BorderStyle.THIN);
        colorStyle.setAlignment(HorizontalAlignment.CENTER);
        colorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return colorStyle;
    }


    /**
     * 提供整型类型数据的样式,白色背景
     */
    public static CellStyle getIntWhiteStyle(Workbook wookbook) {
        CellStyle colorStyle = wookbook.createCellStyle();
        colorStyle.setBorderLeft(BorderStyle.THIN);
        colorStyle.setBorderTop(BorderStyle.THIN);
        colorStyle.setBorderRight(BorderStyle.THIN);
        colorStyle.setBorderBottom(BorderStyle.THIN);
        colorStyle.setAlignment(HorizontalAlignment.CENTER);
        colorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        colorStyle.setWrapText(true);

        DataFormat format = wookbook.createDataFormat();
        colorStyle.setDataFormat(format.getFormat("0"));
        return colorStyle;
    }

    /**
     * 对大标题样式的修饰
     */
    public static CellStyle getHeadlineStyle(Workbook wookbook) {
        CellStyle normalStyle = wookbook.createCellStyle();
        normalStyle.setBorderLeft(BorderStyle.THIN);
        normalStyle.setBorderTop(BorderStyle.THIN);
        normalStyle.setBorderRight(BorderStyle.THIN);
        normalStyle.setBorderBottom(BorderStyle.THIN);
        normalStyle.setAlignment(HorizontalAlignment.CENTER);
        normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        normalStyle.setWrapText(true);
        normalStyle.setFont(getFont(wookbook));

        return normalStyle;
    }


    public static Font getFont(Workbook wookbook) {
        Font fontStyle = wookbook.createFont();
        fontStyle.setFontName("Arial");
        fontStyle.setFontHeightInPoints((short) 16);
        fontStyle.setBold(true);
        return fontStyle;
    }
}
