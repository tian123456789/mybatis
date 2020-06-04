package com.tgr.springbootmybatis.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.tgr.springbootmybatis.config.MyWebMvcPropertiesClass;
import com.tgr.springbootmybatis.entity.User;

@Controller
public class DashboardController extends BaseController{
	
	@Autowired
	private MyWebMvcPropertiesClass properties;
	
	@Autowired
	private MongoClient mongonClient;
	
	@RequestMapping("/111")
	@ResponseBody
	public String dashboard(Model model) {
		System.err.println("我的属性类名 == "+properties.getName());
		return "111";
	}

	
	@RequestMapping("/index")
	public String dashboard(@ModelAttribute("currentUser") User user,Model model) {
		long userCount = 1;
		model.addAttribute("userCount", 1);
		
		long validCount = 0;
		model.addAttribute("validCount", validCount);
		
		long todayCount = 1;
		model.addAttribute("todayCount", 1);
		
		//用户地区分布
		Map<String, BigInteger> map = new HashMap<String, BigInteger>();
		map.put("北京", new BigInteger("1"));
		map.put("上海", new BigInteger("0"));
		
		
		model.addAttribute("citys", map);
		return "index";
	}

	@RequestMapping("/login")
	public String login(User user,String rememberMe,String code,HttpServletRequest request,Model model) {
		
		//判断验证码是否正确 
		if(!StringUtils.isEmpty(code)){ 
			//取出生成的验证码
			String verifyCode = (String)request.getSession().getAttribute("verifyCode"); 
			if(!code.equals(verifyCode)){ 
				model.addAttribute("msg", "验证码输入有误"); return "login";
			}
		}
		
		//使用shiro进行登录
		Subject subject = SecurityUtils.getSubject();
		
		//使用shiro md5对密码进行加密
		Md5Hash hash = new Md5Hash(user.getPassword(), user.getName(), 2);
				
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),hash.toString());
		
		//设置rememberMe的功能
		if(rememberMe != null && rememberMe.equals("1")) {
			token.setRememberMe(true);
		}
		
		try {
			subject.login(token);
			//登录成功
			User dbUser = (User) subject.getPrincipal();
			request.getSession().setAttribute("userName", dbUser.getName());
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名不存在");
			return "login";
		}catch(IncorrectCredentialsException e) {
			model.addAttribute("msg","密码错误");
			return "login";
		}
		return "redirect:/index";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();//shiro底层删除session的会话信息
		return "redirect:/toLogin";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/unAuth")
	public String unAuth() {
		return "unauth";
	}
	
	@RequestMapping("/mogon")
	@ResponseBody
	private String mogon() {
		return mongonClient.getAddress().getHost()+"hahahaha";
	}
	
	@RequestMapping("/cerateCollection")
	@ResponseBody
	private void createCollection() {
		MongoDatabase dataBase = getMongoDatabase();
		dataBase.createCollection("test_collection2");
	}
	
	@RequestMapping("/createDoc")
	@ResponseBody
	private String createDoc() {
		MongoDatabase dataBase = getMongoDatabase();
		MongoCollection<Document> collection = dataBase.getCollection("test_collection");
		ObjectId id = new ObjectId();
		Document document = new Document("title","MongoDB")
				.append("_id", id)
				.append("description", "apple")
				.append("likes", 10)
				.append("by", "dog");
		List<Document> docs = new ArrayList<Document>();
		docs.add(document);
		
		collection.insertMany(docs);
		
		return id.toHexString();
		
	}
	
	@RequestMapping("/getDoc")
	@ResponseBody
	public String getDoc(@RequestParam String id) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		MongoDatabase dataBase = getMongoDatabase();
		MongoCollection<Document> collection = dataBase.getCollection("test_collection");
		FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(id)));
		MongoCursor<Document> mogoCursor = findIterable.iterator();
		while(mogoCursor.hasNext()) {
			Document d = mogoCursor.next();
			JSONObject jsonObject = new JSONObject(d.toJson());
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	private MongoDatabase getMongoDatabase() {
		return mongonClient.getDatabase("tgr");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
