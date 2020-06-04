package com.tgr.springbootmybatis.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tgr.springbootmybatis.controller.BaseController;
import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.util.SecurityUtil;


@RestController
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {

	@Value("${file.store.path}")
	private String fileStorePath;

	@PostMapping(value = "/pic")
	public ResponseResult<String> pic(HttpServletRequest req, //
			@RequestParam(name = "file", required = false) MultipartFile multipartFile) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String contentType = multipartFile.getContentType();
			if (contentType != null && contentType.startsWith("image/")) {
				String imageFilename = "upload_"
						+ SecurityUtil.signatureByMD5(multipartFile.getOriginalFilename()).toLowerCase()
						+ System.currentTimeMillis() + ".png";
				File file = Paths.get(fileStorePath, "upload", imageFilename).toFile();
				try {
					multipartFile.transferTo(file);
					String url = "/upload/" + imageFilename;
					return ResponseResult.getResult(url);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}

		return ResponseResult.getErrorResult("上传失败");
	}

	@PostMapping(value = "/file")
	public ResponseResult<String> file(HttpServletRequest req, 
			@RequestParam(name = "file", required = false) MultipartFile multipartFile) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String originalFilename = multipartFile.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			if (index > -1) {
				String filename = originalFilename.substring(0, index);
				String extname = originalFilename.substring(index);
				originalFilename = SecurityUtil.signatureByMD5(filename).toLowerCase() + extname;
			} else {
				originalFilename = SecurityUtil.signatureByMD5(originalFilename).toLowerCase();
			}
			String imageFilename = "upload_" + System.currentTimeMillis() + "_" + originalFilename;
			File file = Paths.get(fileStorePath, "upload", imageFilename).toFile();
			try {
				multipartFile.transferTo(file);
				String url = "/upload/" + imageFilename;
				return ResponseResult.getResult(url);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return ResponseResult.getErrorResult("上传失败");
	}
}
