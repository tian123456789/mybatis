package com.tgr.springbootmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tgr.springbootmybatis.entity.Dict_C1;
import com.tgr.springbootmybatis.service.Dict_C1Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 大类控制器
 */
@Controller
@RequestMapping("/c1")
public class C1Controller {

	/**
	 * 注入C1Service
	 */
	@Resource
	private Dict_C1Service c1s ;

	@RequestMapping("/list")
	public String all(Model model){
		List<Dict_C1> list = c1s.findAll() ;
		model.addAttribute("c1" , new Dict_C1()) ;
		model.addAttribute("list" , list) ;
		return "c1/c1_list" ;
	}

	/**
	 * 保存新大类
	 */
	@RequestMapping("/save")
	public String save(Dict_C1 c1){
		if(c1.getId() == null){
			c1s.insert(c1);
		}
		else{
			c1s.update(c1);
		}
		return "redirect:/c1/list" ;
	}

	@RequestMapping("/delete")
	public String delete(Integer c1id) {
		c1s.delete(c1id);
		return "redirect:/c1/list";
	}

	@RequestMapping("/edit")
	public String edit(Model m , Integer c1id ){
		Dict_C1 c1 = c1s.findById(c1id) ;
		m.addAttribute("c1" , c1) ;
		m.addAttribute("list" , c1s.findAll());
		return "/c1/c1_list" ;
	}

}
