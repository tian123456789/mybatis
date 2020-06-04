package com.tgr.springbootmybatis.file.component;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ArrayList;
import java.util.List;

public class ExportContextVo {
    /**
     * 文件名（此字段不能为空，默认为‘数据导出’）
     */
    private String fileName = "数据导出";
    /**
     * excel标题名称（此字段可以不赋值，默认）
     */
    private String titleName;
    /**
     * sheet大小,用于分页时，设置每页的大小
     */
    private Integer sheetSize;
    /**
     * sheet最大记录条数，默认10000
     */
    private Integer maxSheetSize = 10000;
    /**
     * 从map中获取需要展示数据list的key，默认为list
     */
    private String listKey = "list";
    /**
     * 配置列list
     */
    private List<Column> columnList = new ArrayList<Column>();
    /**
     * 是否添加标题,默认为添加
     */
    private boolean titleFlag = true;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitleName() {
        if (titleName == null) {
            titleName = fileName;
        }
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Integer getSheetSize() {
        return sheetSize;
    }

    public void setSheetSize(Integer sheetSize) {
        this.sheetSize = sheetSize;
    }

    public Integer getMaxSheetSize() {
        return maxSheetSize;
    }

    public void setMaxSheetSize(Integer maxSheetSize) {
        this.maxSheetSize = maxSheetSize;
    }

    public String getListKey() {
        return listKey;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public boolean isTitleFlag() {
        return titleFlag;
    }

    public void setTitleFlag(boolean titleFlag) {
        this.titleFlag = titleFlag;
    }

    /**
     * Description: 添加列，无样式
     */
    public void nextColumn(String columnName, String valueName, Integer cellWidth) {
        columnList.add(new Column(columnName, valueName, cellWidth));
    }

    /**
     * Description: 添加列，指定样式
     */
    public void nextColumn(String columnName, String valueName, Integer cellWidth, CellStyle cellStyle) {
        columnList.add(new Column(columnName, valueName, cellWidth, cellStyle));
    }

}
