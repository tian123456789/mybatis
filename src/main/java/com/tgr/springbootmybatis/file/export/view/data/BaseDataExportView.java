package com.tgr.springbootmybatis.file.export.view.data;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.file.component.AbstractExcelExportController;
import com.tgr.springbootmybatis.file.component.ExcelStyleUtil;
import com.tgr.springbootmybatis.file.component.ExportContextVo;

import java.util.List;

@Component
public class BaseDataExportView extends AbstractExcelExportController {

    private List columns;

    protected void initContext(ExportContextVo cv) {
        cv.setFileName("导出数据");
        cv.setTitleName("导出数据详情");
    }

    public void initColumn(List columns) {
        this.columns = columns;
    }

    @Override
    protected void setColumn(Workbook workbook, ExportContextVo cv) {
        CellStyle style = ExcelStyleUtil.getCellStyle(workbook);//指定单元格样式
        if (columns != null) {
            for (Object column : this.columns) {
                cv.nextColumn(column.toString(), column.toString(), 18 * 270, style);
            }
        }
    }

}
