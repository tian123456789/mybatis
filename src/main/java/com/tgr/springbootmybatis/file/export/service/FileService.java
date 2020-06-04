package com.tgr.springbootmybatis.file.export.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.file.export.vo.FileInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    public List<FileInfo> getFileList(String parentPath) throws FileNotFoundException {
        logger.info("获取目录信息");
        File parent = new File(parentPath);
        if (!parent.exists()) {
            throw new FileNotFoundException("目录未找到：" + parentPath);
        }

        FileInfo parentInfo = new FileInfo(parent.getPath(), parent.isFile(), parent.isHidden(), parent.length());
        List<FileInfo> fileInfoList = new ArrayList<>();
        File[] files = parent.listFiles();

        for (File file : files) {
            if (!file.isHidden()) {
                FileInfo fileInfo = new FileInfo(file.getPath(), file.isFile(), file.isHidden(), file.length());
                fileInfo.setParent(parentInfo);
                fileInfo.setUpdateTime(new Date(file.lastModified()));
                fileInfoList.add(fileInfo);
            }
        }

        return fileInfoList;
    }

}
