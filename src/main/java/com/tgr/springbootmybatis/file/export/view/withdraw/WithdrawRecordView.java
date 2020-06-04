package com.tgr.springbootmybatis.file.export.view.withdraw;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.file.component.AbstractExcelExportController;
import com.tgr.springbootmybatis.file.component.ExcelStyleUtil;
import com.tgr.springbootmybatis.file.component.ExportContextVo;

/**
 * 推广返利记录导出Excel服务
 */
@Component
public class WithdrawRecordView extends AbstractExcelExportController<WithdrawRecordVo> {

    protected void initContext(ExportContextVo cv) {
        cv.setFileName("推广返利导出记录");
        cv.setTitleName("推广返利记录表");
    }

    @Override
    protected void setColumn(Workbook workbook, ExportContextVo cv) {

        CellStyle style = ExcelStyleUtil.getCellStyle(workbook);//指定单元格样式

        cv.nextColumn("推广人员姓名", "userName",18*270, style);
        cv.nextColumn("手机号", "mobile",18*270, style);
        cv.nextColumn("所属企业", "customerName",18*270, style);
        cv.nextColumn("邀请人姓名", "inviteUserName",18*270, style);
        cv.nextColumn("邀请企业", "inviteCustomerName",18*270, style);
        cv.nextColumn("推广渠道", "channel",18*270, style);
        cv.nextColumn("申请提现时间", "applyTime",18*270, style);
        cv.nextColumn("提现审核时间", "auditTime",18*270, style);
        cv.nextColumn("到账时间", "transferTime",18*270, style);
        cv.nextColumn("提现状态", "state",18*270, style);
        cv.nextColumn("返利金额", "amount",18*270, style);
        cv.nextColumn("失败原因", "failReason",18*270, style);
    }

}
