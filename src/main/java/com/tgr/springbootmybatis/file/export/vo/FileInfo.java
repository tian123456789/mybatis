package com.tgr.springbootmybatis.file.export.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FileInfo {

    public String path;

    public FileInfo parent;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    /**
     * 0：文件夹，1：文件
     */
    public Integer fileType;

    /**
     * 文件大小
     */
    public Long fileSize;

    /**
     * 是否隐藏文件
     */
    public boolean isHidden = false;


    public FileInfo() {
    }

    public FileInfo(String path, boolean isFile,boolean isHidden, Long fileSize) {
        this.path = path;
        this.fileType = isFile ? 1 : 0;
        this.isHidden = isHidden;
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileInfo getParent() {
        return parent;
    }

    public void setParent(FileInfo parent) {
        this.parent = parent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
