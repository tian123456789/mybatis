package com.tgr.springbootmybatis.file.export.service;

import org.apache.poi.ss.usermodel.Workbook;

import com.tgr.springbootmybatis.controller.ResponseResult;

import java.util.Set;

public interface LotteryCustomerService {


    /**
     * Excel导入初始化数据
     * @param workbook
     * @return
     */
    ResponseResult<Set<String>> initByExcel(Workbook workbook);

}
