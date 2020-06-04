package com.tgr.springbootmybatis.file.export.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.component.FileUtil;
import com.tgr.springbootmybatis.file.export.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("export/filesystem")
@RequiresRoles("1401")
public class FileExportController {

    private static Logger logger = LoggerFactory.getLogger(FileExportController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping("/index")
    public String viewPage(HttpServletRequest request, Map<String,Object> map){
        return "data/filesystem.html";
    }

    @RequestMapping("/syslog")
    public String viewDemoPage(HttpServletRequest request, Map<String,Object> map){
        map.put("serverPath",request.getServerName());
        map.put("port",request.getServerPort());
        map.put("path",request.getParameter("currentFile"));
        return "data/log.html";
    }

    /**
     * 获取文件列表
     * @param parentPath
     * @return
     */
    @RequestMapping("/getFileList")
    @ResponseBody
    public ResponseResult getFileList(HttpServletRequest request,@RequestParam("parentPath") String parentPath) throws Exception {
        return ResponseResult.getResult(fileService.getFileList(parentPath));
    }

    /**
     * 压缩文件并下载到本地
     * @param request
     * @param currentFile
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("currentFile") String currentFile) throws IOException {
        File file = new File(currentFile);
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName()+".zip");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            FileUtil.zipFile(currentFile,os);
        } catch (Exception e) {
            logger.error("下载失败",e);
        } finally {
            if(os != null){
                os.flush();
                os.close();
            }
        }

    }
}
