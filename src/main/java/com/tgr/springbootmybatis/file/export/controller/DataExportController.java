package com.tgr.springbootmybatis.file.export.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.export.enums.UserTableEnum;
import com.tgr.springbootmybatis.file.export.service.DataService;
import com.tgr.springbootmybatis.file.export.service.LotteryCustomerService;
import com.tgr.springbootmybatis.file.export.view.data.BaseDataExportView;
import com.tgr.springbootmybatis.file.util.PageVo;
import com.tgr.springbootmybatis.file.util.Paging;
import com.tgr.springbootmybatis.file.util.SearchFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 后台数据导出接口
 */
@Controller
@RequestMapping("export")
@RequiresRoles("1401")
@SuppressWarnings("all")
public class DataExportController {

    private static Logger logger = LoggerFactory.getLogger(DataExportController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private LotteryCustomerService lotteryCustomerService;

    @RequestMapping("/index")
    public String viewPage(HttpServletRequest request, Map<String, Object> map) {

        String schema = request.getParameter("schema");

//        List<String> tableNames = dataService.getAllTables(dbName);

        UserTableEnum[] tableNames = UserTableEnum.values();

        map.put("tableNames", tableNames);
        map.put("schema", schema);
        map.put("operations", SearchFilter.Operator.values());

        return "data/index.html";
    }

    /**
     * 查询表结构字段列表
     *
     * @param schema
     * @param tableName
     * @return
     */
    @RequestMapping("/tableColumn")
    @ResponseBody
    public ResponseResult tableColumn(String schema, String tableName) {
        return ResponseResult.getResult(dataService.getTableColumn(schema, tableName));
    }

    /**
     * 导出并下载到Excel
     *
     * @param request            请求参数
     * @param baseDataExportView 导出组件
     */
    @RequestMapping(value = "/exportExcel", produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public ModelAndView exportExcel(HttpServletRequest request, BaseDataExportView baseDataExportView) {

        ModelAndView view = new ModelAndView();

        String schema = request.getParameter("schema");
        String tableName = request.getParameter("tableName");
        String condition = request.getParameter("condition");

        List columns = dataService.getTableColumn(schema, tableName);
        baseDataExportView.initColumn(columns);
        PageVo pageVo = new PageVo(1, Integer.MAX_VALUE);
        Paging datas = dataService.getDbDataList(tableName, condition, pageVo);

        view.addObject("list", datas.getContent());
        view.setView(baseDataExportView);
        return view;

    }

    /**
     * 动态查询列表
     *
     * @param request
     * @param tableName
     * @return
     */
    @RequestMapping("/queryDynamic")
    @ResponseBody
    public ResponseResult queryDynamic(HttpServletRequest request, String tableName) {
      /*  Collection<SearchFilter> collections = DynamicSpecifications.genSearchFilter(request);
        return ResponseResult.getResult(dataService.getDbDataList(tableName, collections, false));*/
    	return null;
    }

    /**
     * 查询列表
     *
     * @param request
     * @param tableName
     * @return
     */
    @RequestMapping("/queryDb")
    @ResponseBody
    public ResponseResult queryDbDatas(HttpServletRequest request, PageVo pageVo, String tableName) {
        String condition = request.getParameter("condition");
        return ResponseResult.getResult(dataService.getDbDataList(tableName, condition, pageVo));
    }

    @RequestMapping("/modifyDataById")
    @ResponseBody
    public ResponseResult modifyDataById(HttpServletRequest request, Long id, String tableName, String field, String value) {
        ResponseResult responseResult = dataService.modifyDataById(id, tableName, field, value);
        return responseResult;
    }

    @RequestMapping("/initExcel")
    @ResponseBody
    public ResponseResult<Set<String>> initExcel(@RequestParam(value = "filename") MultipartFile file) throws IOException {
        logger.info("开始导入...");
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            return lotteryCustomerService.initByExcel(workbook);
        } catch (Exception e) {
            logger.error("导入失败",e);
            return ResponseResult.getErrorResult("导入失败："+e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
