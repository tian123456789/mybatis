package com.tgr.webneimu.chapter1.sendHttp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Demo1SendHttp {

	/**
	 * 发送http请求
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void send() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=a&rsv_pq=ea42a49d000726fc&rsv_t=444bXc9vT1xp4bbgq%2F5j7Xy60siZ9wPDxTwmNb%2Fnd%2FMozCZuSspw%2BMjBltM&rqlang=cn&rsv_enter=0&rsv_dl=tb&rsv_sug3=2&inputT=1505&rsv_sug4=2383");
		
		httpGet.addHeader("Content-Type", "application/x-www-form-urlemcoded;charset=GBK");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
		httpGet.addHeader("Cookie", "BAIDUID=89D0CBEC48B1A54DAAF44B05CA148F6F:FG=1; BIDUPSID=89D0CBEC48B1A54DAAF44B05CA148F6F; PSTM=1543975125; BD_UPN=12314753; MCITY=-131%3A; sugstore=0; delPer=0; BD_HOME=0; H_PS_PSSID=1463_21090_18559_29522_29720_29567_29220_22159; BD_CK_SAM=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; PSINO=7; COOKIE_SESSION=3522_0_9_5_9_14_0_0_9_7_11_0_0_0_19_0_1570001102_0_1570004605%7C9%230_1_1568787466%7C1; H_PS_645EC=9f6ePMO1G6qVWxEXEG8u0aWqh2z5cWy2QK1jKMZhOaGDACqHa48fRQUCBSk");
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity,"utf-8");
		System.out.println("================================================================");
		System.out.println("status: "+response.getStatusLine());
		System.out.println(content);
		
		
	}
}
