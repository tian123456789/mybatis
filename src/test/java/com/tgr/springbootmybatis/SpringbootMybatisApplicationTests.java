package com.tgr.springbootmybatis;

import java.text.DecimalFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.tgr.springbootmybatis.NumericGenerator.DefaultRandomNumberGenerator;
import com.tgr.springbootmybatis.util.card.GenerateID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringbootMybatisApplication.class})
public class SpringbootMybatisApplicationTests {

	@Autowired
	private DefaultRandomNumberGenerator defaultRandomNumberGenerator;
	
	@Autowired
	private GenerateID CardGenerateID;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test() {
		System.err.println(defaultRandomNumberGenerator.getNewString());
	}
	
	public static void main(String[] args) throws Exception {
		/*String s = "111";
		System.err.println(moneyFormat(s));*/
		
		 String s = null;
		 
		 try {
			 Assert.notNull("", "为空");
			 Assert.hasText("", "无值");
			 System.err.println(1111);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String moneyFormat(Object money) throws Exception {
        if (money == null){
            return "0.00";
        }
        
        Double moneyInt = (double) 0;
        
        if(money instanceof Long) {
        	moneyInt = ((Long) money).doubleValue();
        }else if(money instanceof Integer) {
        	moneyInt = ((Integer) money).doubleValue();
        }else if(money instanceof String) {
        	moneyInt = Double.valueOf((String) money);
        }else {
        	throw new Exception();
        }
       
        DecimalFormat df=new DecimalFormat("0.00");
      
        String moneyStr = df.format(moneyInt/100);
        return moneyStr;
    }
	
	@Test
	public void testCardId() {
		System.err.println(CardGenerateID.nextId());
	}
	
}
