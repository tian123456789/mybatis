package com.tgr.springbootmybatis.file.component;

import org.apache.poi.ss.usermodel.CellStyle;

public class Column {
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 此列对应的属性名称
	 */
	private String valueName;
	/**
	 * 单元格宽度
	 */
	private Integer cellWidth;
	/**
	 * 单元格格式
	 */
	private CellStyle cellStyle;

	private String comment;

	public Column(String columnName, String valueName, Integer cellWidth, CellStyle cellStyle, String comment) {
		this.columnName = columnName;
		this.valueName = valueName;
		this.cellWidth = cellWidth;
		this.cellStyle = cellStyle;
		this.comment = comment;
	}

	public Column(String columnName, String valueName, Integer cellWidth, String comment) {
		this.columnName = columnName;
		this.valueName = valueName;
		this.cellWidth = cellWidth;
		this.comment = comment;
	}

	public Column(String columnName, String valueName, Integer cellWidth, CellStyle cellStyle) {
		this.columnName = columnName;
		this.valueName = valueName;
		this.cellWidth = cellWidth;
		this.cellStyle = cellStyle;
	}

	public Column(String columnName, String valueName, Integer cellWidth) {
		this.columnName = columnName;
		this.valueName = valueName;
		this.cellWidth = cellWidth;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public Integer getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(Integer cellWidth) {
		this.cellWidth = cellWidth;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
