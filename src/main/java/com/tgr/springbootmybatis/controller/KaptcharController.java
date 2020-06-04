package com.tgr.springbootmybatis.controller;

/*import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;*/

//import com.google.code.kaptcha.impl.DefaultKaptcha;


//@Controller 
public class KaptcharController{
	
	/*@Resource 
	private DefaultKaptcha defaultKaptcha;
	
	@RequestMapping("/defaultKaptcha") 
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		byte[] captchaChallengeAsJpeg = null; 
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到 session 中 
			String createText = defaultKaptcha.createText();
			httpServletRequest.getSession().setAttribute("verifyCode", createText); 
			// 使用生产的验证码字符串返回一个BufferedImage 对象并转为byte 写入到byte数组 中 
			BufferedImage challenge = defaultKaptcha.createImage(createText); 
			ImageIO.write(challenge, "jpg", jpegOutputStream); 
		} catch (IllegalArgumentException e) { 
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 定义 response 输出类型为 image/jpeg 类型，使用 response 输出流输出图片的 byte 数 组
		
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray(); 
		httpServletResponse.setHeader("Cache-Control", "no-store"); 
		httpServletResponse.setHeader("Pragma", "no-cache"); 
		httpServletResponse.setDateHeader("Expires", 0); 
		httpServletResponse.setContentType("image/jpeg"); 
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream(); 
		responseOutputStream.write(captchaChallengeAsJpeg); responseOutputStream.flush(); 
		responseOutputStream.close();
	}
	*/
}
