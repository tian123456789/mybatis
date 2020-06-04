package com.tgr.springbootmybatis.file.component;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 批量压缩文件
     *
     * @param srcfile 源文件
     * @param zipfile 压缩后目标文件
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipfile));
            for (int i = 0; i < srcfile.length; ++i) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {

                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        } catch (IOException e) {
            logger.error("文件压缩失败", e);
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException localIOException2) {
                    logger.error("文件压缩失败", localIOException2);
                }
        }
    }

    /**
     * 批量压缩文件
     *
     * @param srcFiles 源文件
     * @param out      目标输出流
     * @throws Exception
     */
    public static void zipFiles(List<String> srcFiles, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        ZipOutputStream zipOut = null;

        try {
            zipOut = new ZipOutputStream(out);
            for (int i = 0; i < srcFiles.size(); ++i) {
                File file = new File(srcFiles.get(i));
                FileInputStream in = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                zipOut.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            logger.error("文件压缩失败", e);
            throw new Exception("文件压缩失败:" + e.getMessage());
        } finally {
            if (zipOut != null)
                try {
                    zipOut.close();
                } catch (IOException localIOException2) {
                    logger.error("文件压缩失败", localIOException2);
                }
        }

    }

    /**
     * 压缩文件
     * @param srcFile 源文件路径
     * @param out 输出流
     * @throws Exception
     */
    public static void zipFile(String srcFile, OutputStream out) throws Exception {
        File file = new File(srcFile);
        Assert.isTrue(file.isFile(),"源文件是目录，不可压缩操作");
        zipFile(file, out);
    }

    /**
     * 压缩文件
     * @param srcFile 源文件
     * @param out 输出流
     * @throws Exception
     */
    public static void zipFile(File srcFile, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(out);
            FileInputStream in = new FileInputStream(srcFile);
            zipOut.putNextEntry(new ZipEntry(srcFile.getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                zipOut.write(buf, 0, len);
            }
            zipOut.closeEntry();
            in.close();
        } catch (Exception e) {
            logger.error("文件压缩失败", e);
            throw new Exception("文件压缩失败:" + e.getMessage());
        } finally {
            if (zipOut != null)
                try {
                    zipOut.close();
                } catch (IOException localIOException2) {
                    logger.error("文件压缩失败", localIOException2);
                }
        }

    }

    /**
     * 批量下载并压缩文件
     *
     * @param urls 文件下载的URL
     * @param out  目标输出流
     * @throws Exception
     */
    public static void zipFileFromUrl(List<String> urls, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        ZipOutputStream zipOut = null;

        try {
            zipOut = new ZipOutputStream(out);
            for (int i = 0; i < urls.size(); ++i) {
                String url = urls.get(i);
                String name = StringUtils.substringAfterLast(url, "/");
                InputStream in = readFromUrl(url);

                zipOut.putNextEntry(new ZipEntry(name));
                int len;
                while ((len = in.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                zipOut.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            logger.error("文件压缩失败", e);
            throw new Exception("文件压缩失败:" + e.getMessage());
        } finally {
            if (zipOut != null)
                try {
                    zipOut.close();
                } catch (IOException localIOException2) {
                    logger.error("文件压缩失败", localIOException2);
                }
        }

    }

    /**
     * 下载远程文件到输入流
     *
     * @param url 远程文件URL
     * @return
     */
    public static InputStream readFromUrl(String url) {
        InputStream inputStream;
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        client.getHttpConnectionManager().getParams().setSoTimeout(100000);
        GetMethod get = new GetMethod(url);
        try {
            client.executeMethod(get);
            inputStream = get.getResponseBodyAsStream();
            return inputStream;
        } catch (IOException e) {
            logger.error("读取URL[" + url + "]数据失败", e);
            throw new RuntimeException("读取URL[" + url + "]数据失败", e);
        }

    }

}
