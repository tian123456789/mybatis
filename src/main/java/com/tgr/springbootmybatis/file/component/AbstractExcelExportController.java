package com.tgr.springbootmybatis.file.component;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Excel通用导出组件，简单的导出功能只需扩展此类，并实现抽象方法即可。具体使用可参见下.
 *
 * @author YYJ
 * @see sgcc.sgec.exiuge.admin.controller.WithdrawController#exportRecordList
 */
public abstract class AbstractExcelExportController<T> extends AbstractXlsView {

    protected static final Logger log = LoggerFactory.getLogger(AbstractExcelExportController.class);

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        ExportContextVo cv = new ExportContextVo();
        initContext(cv);
        try {
            @SuppressWarnings("unchecked")
            List<T> list = (List<T>) map.get(cv.getListKey());
            Assert.notNull(cv.getFileName(), "fileName must not be null");
            Assert.notNull(list, "list must not be null");
            createExcel(workbook, list, response, cv);
            endMethod();//自定义导出结束后的动作
        } catch (Exception e) {
            log.error(cv.getFileName() + "导出异常");
            log.error(e.getMessage(), e);
        } finally {

        }
    }

    /**
     * 初始化导出配置信息，包括文件名、最大行数等，不覆盖的话，按默认值设定，参见ExportContextVo
     *
     * @param cv
     */
    protected void initContext(ExportContextVo cv) {

    }

    /**
     * 设置要导出的列
     *
     * @param workbook
     * @param cv
     */
    protected abstract void setColumn(Workbook workbook, ExportContextVo cv);

    /**
     * 创建Excel对象
     *
     * @param workbook
     * @param list
     * @param response
     * @param cv
     * @throws Exception
     */
    public void createExcel(Workbook workbook, List<T> list, HttpServletResponse response, ExportContextVo cv) throws Exception {
        /**
         * 如果未给titleName赋值，则默认与fileName一致
         */
        setColumn(workbook, cv);
        Sheet sheet = null;
        int sheetPage = 1;
        /**
         * 如果人为为sheetSize赋值则进行页数计算
         */
        Integer sheetSize = cv.getSheetSize();
        if (list != null && sheetSize != null) {
            int listSize = list.size();
            if (listSize % sheetSize == 0) {
                sheetPage = listSize / sheetSize;
            } else {
                sheetPage = listSize / sheetSize + 1;
            }
        }
        //title行的样式公用，防止分sheet较多时超过style的最大值
        CellStyle titleStyle = ExcelStyleUtil.getTitleStyle(workbook);
        /**
         * 如果sheetPage>1则进行分页
         */
        List<Column> columnList = cv.getColumnList();
        if (sheetPage > 1) {
            for (int i = 1; i <= sheetPage; i++) {
                sheet = workbook.createSheet(cv.getTitleName() + i);
                List<T> subList = null;
                if (i == sheetPage) {
                    subList = list.subList(sheetSize * (i - 1), list.size());
                } else {
                    subList = list.subList(sheetSize * (i - 1), sheetSize * i);
                }
                this.createSheet(sheet, columnList, subList, titleStyle, cv);
            }
        } else {
            sheet = workbook.createSheet(cv.getTitleName());
            this.createSheet(sheet, columnList, list, titleStyle, cv);
        }
        this.createExcelContentType(workbook, response, cv);
    }

    /**
     * 创建sheet页
     *
     * @param sheet
     * @param columnList
     * @param list
     * @param titleStyle
     * @param cv
     */
    private void createSheet(Sheet sheet, List<Column> columnList, List<T> list, CellStyle titleStyle, ExportContextVo cv) {
        int j = 0;
        //各列所指的属性
        List<String> valueNameList = new ArrayList<String>();
        //创建列标题
        Row row1 = sheet.createRow(j++);
        for (int i = 0; i < columnList.size(); i++) {
            Column myTitle = columnList.get(i);
            Cell cell = row1.createCell(i);
            cell.setCellValue(myTitle.getColumnName());
            cell.setCellStyle(titleStyle);
            sheet.setColumnWidth(i, myTitle.getCellWidth());
            valueNameList.add(myTitle.getValueName());
        }
        //展示记录
        if (list.size() > 0) {
            if (list.size() > cv.getMaxSheetSize()) {
                Row row = sheet.createRow(j++);
                Cell cell = row.createCell(0);
                cell.setCellValue("数据量不能超过" + cv.getMaxSheetSize() + "条,请缩小查询范围!");
                sheet.addMergedRegion(new CellRangeAddress(3, (short) 0, 3, (short) 9));
            } else {
                for (T obj : list) {
                    Row row = sheet.createRow(j++);
                    Map<String, Object> valueMap = this.setValueMap(obj, valueNameList);
                    for (int i = 0; i < columnList.size(); i++) {
                        Column column = columnList.get(i);
                        Cell cell = row.createCell(i);
                        this.setCellValue(cell, valueMap, column);
                    }
                }
            }
        } else {
            Row row = sheet.createRow(j);
            Cell cell = row.createCell(0);
            cell.setCellValue("无数据!");
            sheet.addMergedRegion(new CellRangeAddress(3, (short) 0, 3, (short) 9));
        }
    }

    /**
     * 值转换
     *
     * @param obj
     * @param valueNameList
     * @return
     */
    private Map<String, Object> setValueMap(T obj, List<String> valueNameList) {
        Map<String, Object> valueMap = new HashMap<>();
        try {
            Class<?> clazz = obj.getClass();
            for (String valueName : valueNameList) {
                //若未自定义赋值，则自动为其赋值
                if (!valueMap.containsKey(valueName)) {
                    //若T为Map则直接根据key获取value，否则通过反射进行获取值
                    if (obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> tMap = (Map<String, Object>) obj;
                        valueMap.put(valueName, tMap.get(valueName));
                    } else {
                        //拼装方法名
                        String methodName = "get" + valueName.substring(0, 1).toUpperCase() + valueName.substring(1);
                        Method m = clazz.getMethod(methodName);
                        m.invoke(obj);
                        valueMap.put(valueName, m.invoke(obj));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("自动封装valueMap异常！");
        }
        return valueMap;
    }

    private void setCellValue(Cell cell, Map<String, Object> map, Column column) {
        Object value = map.get(column.getValueName());
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            cell.setCellValue(DateFormatUtils.format((Date) value,"yyyy-MM-dd HH:mm:ss"));
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        }//使用了BigDecimal
        else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        }
        else if (value instanceof BigInteger) {
            cell.setCellValue(((BigInteger) value).longValue());
        }
        else if (value instanceof Boolean) {
            cell.setCellValue(((Boolean) value));
        }
        if (column.getCellStyle() != null) {
            cell.setCellStyle(column.getCellStyle());
        }
    }

    private void createExcelContentType(Workbook workbook, HttpServletResponse response, ExportContextVo cv) throws IOException {
        response.setHeader("Content-disposition", "attachment;filename=" + new String((cv.getFileName() + ".xls").getBytes("gb2312"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel");
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    protected void endMethod() {

    }

}
