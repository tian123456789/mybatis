package com.tgr.springbootmybatis.file.export.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.export.bean.Customer;
import com.tgr.springbootmybatis.file.export.bean.LotteryCustomer;
import com.tgr.springbootmybatis.file.export.dao.LotteryCustomerRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class LotteryCustomerServiceImpl implements LotteryCustomerService {

    private static Logger logger = LoggerFactory.getLogger(LotteryCustomerServiceImpl.class);

    @Autowired
    private LotteryCustomerRepository lotteryCustomerRepository;

    @Autowired
    private CustomerService customerService;

    private static final Integer LIMIT = 5;

    @Override
    public ResponseResult<Set<String>> initByExcel(Workbook workbook) {

        Set<String> noMatchCustomers = new HashSet<>();
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String customerName = row.getCell(0).getStringCellValue();//抽奖企业
            Double lotteryLimit = row.getCell(1) != null?row.getCell(1).getNumericCellValue():LIMIT;//限定抽奖次数，默认5次
            Customer customer = customerService.findByCustomerName(customerName);
            if (customer != null) {
                if (lotteryCustomerRepository.findByCustomer(customer) == null) {
                    LotteryCustomer lotteryCustomer = new LotteryCustomer();
                    lotteryCustomer.setCustomer(customer);
                    lotteryCustomer.setLotteryLimit(lotteryLimit.intValue());
                    //lotteryCustomerRepository.save(lotteryCustomer);
                } else {
                    //logger.error("企业【" + customer.getName() + "】已经存在");
                }
            } else {
                noMatchCustomers.add(customerName);
            }
        }

        if(noMatchCustomers.isEmpty()){
            return ResponseResult.getResult(noMatchCustomers, "导入成功");
        }else{
            ResponseResult<Set<String>> result = ResponseResult.getResult(noMatchCustomers,"导入失败，部分企业未找到");
            result.setCode(ResponseResult.CODE_INTERNAL_SERVER_ERROR);
            return result;
        }
    }
}
