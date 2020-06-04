package com.tgr.springbootmybatis.QRCode;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;


@SuppressWarnings("all")
public class GenearteQRCode {

	public static void main(String[] args) throws Exception {
		
		Date date = new Date(2019, 1, 1);
		String o = "123456789987654321123456789987654321";
		QRContent content = new GenearteQRCode().new QRContent(1, 15, "98765", "56789",date,o.getBytes());
		JSONObject jsonObject = JSONObject.fromObject(content);
		
		String contentString = jsonObject.toString();
		
		byte[] bytes = contentString.getBytes();
		
		String imgPath = "G:/qrCode/inner.png";// 嵌入二维码的图片路径
		String destPath = "G:/qrCode/qrcode/jam.png";// 生成的二维码的路径及名称
		QRCodeUtil.encode(contentString, imgPath, destPath, true);// 生成二维码
		
		String str = QRCodeUtil.decode(destPath);// 解析二维码
		
		System.out.println(str);// 打印出解析出的内容

	}
	
	public class QRContent implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private int version;
		private int length;
		private String private_key;
		private String public_key;
		private Date generateQRCodeDate;
		private byte[] bytes;
		
		public QRContent(int version, int length, String private_key, String public_key, Date generateQRCodeDate,
				byte[] bytes) {
			super();
			this.version = version;
			this.length = length;
			this.private_key = private_key;
			this.public_key = public_key;
			this.generateQRCodeDate = generateQRCodeDate;
			this.bytes = bytes;
		}

		public QRContent() {
			super();
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getPrivate_key() {
			return private_key;
		}

		public void setPrivate_key(String private_key) {
			this.private_key = private_key;
		}

		public String getPublic_key() {
			return public_key;
		}

		public void setPublic_key(String public_key) {
			this.public_key = public_key;
		}

		public Date getGenerateQRCodeDate() {
			return generateQRCodeDate;
		}

		public void setGenerateQRCodeDate(Date generateQRCodeDate) {
			this.generateQRCodeDate = generateQRCodeDate;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
		
		
	}

}
