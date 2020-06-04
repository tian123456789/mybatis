package com.tgr.springbootmybatis.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

//import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tgr.springbootmybatis.mapper.VideoMapper;
import com.tgr.springbootmybatis.pojo.VideoVo;


@Controller
@RequestMapping("/video")
public class VideoController {

	private static Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	@Value("${solr.url}")
	private String solrUrl;
	
	@Value("${solr.core}")
	private String solrCore;
	
	@Autowired
	private VideoMapper videoMapper;
	
	@GetMapping("/videos")
	public String list(Model model){
		logger.debug("视频列表Controller");
		List<VideoVo> videos = videoMapper.findAll();
		/*
		 * paging.currentIndex
		 * paging.totalPages
		 * paging.currentIndex
		 * paging.params
		 * paging.beginIndex
		 * paging.endIndex
		 * */
		Map<String,Integer> paging = new HashMap<String,Integer>();
		paging.put("totalPages", 1);
		paging.put("currentIndex", 1);
		paging.put("params", null);
		paging.put("beginIndex", 1);
		paging.put("endIndex", 10);
		model.addAttribute("paging", paging);
		model.addAttribute("videos", videos);
		logger.debug("视频列表Controller"+videos.toString());
		return "video/video";
	}
	
	@PostMapping("/search")
	public String search(@RequestParam(name = "key") String key,Model model) {
		
		String pattern = "name:%s director:%s actor:%s name_str:%s";
		String query = String.format(pattern, key,key,key,key);
		System.err.println("格式化的查询条件 ::: "+query);
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("q", query);
		//map.put("fl", "id"); 只查询id字段
		map.put("sort", "id desc");//排序
		map.put("df", "name");//默认搜索
		map.put("hl", Boolean.toString(true));
		map.put("hl.field", "name");
		map.put("hl.simple.pre", "<font color='red'>");//"<font color=\"red\">"
		map.put("hl.simple.post", "</font>");
		map.put("start", Integer.toString(0));
		map.put("rows", Integer.toString(30));
		MapSolrParams params = new MapSolrParams(map);
		List<VideoVo> returnMap = new ArrayList<VideoVo>();
		try {
			HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
					.withConnectionTimeout(10000).withSocketTimeout(60000)
					.build();
			
			QueryResponse response = client.query(solrCore, params);
			SolrDocumentList documents = response.getResults();
			Map<String, Map<String, List<String>>> highLightMap = response.getHighlighting();
			
			for(SolrDocument doc : documents) {
				VideoVo vo = new VideoVo();
				//高亮标题
				List<String> nameList = highLightMap.get(doc.get("id")).get("name");
				String highLightName = nameList.get(0);
				vo.setName(highLightName);
				vo.setId(Integer.valueOf(doc.get("id").toString()));
				vo.setActor(doc.get("actor").toString());;
				vo.setArea(doc.get("area").toString());
				vo.setDescription(doc.get("description").toString());
				vo.setDirector(doc.get("director").toString());
				vo.setIsOn(Boolean.valueOf(doc.get("isOn").toString()));
				vo.setUrl("url");
				vo.setYear(doc.get("year").toString());
				returnMap.add(vo);
			}
			
			/*Set<Entry<String, Map<String, List<String>>>> sets =  highLightMap.entrySet();
			Iterator<Entry<String, Map<String, List<String>>>> itr=  sets.iterator();
			while(itr.hasNext()) {
				Entry<String,Map<String,List<String>>> en = itr.next();
				String id = en.getKey();
				documents.get(id);
			}*/
			
			Map<String,Integer> paging = new HashMap<String,Integer>();
			paging.put("totalPages", 20);
			paging.put("currentIndex", 1);
			paging.put("params", null);
			paging.put("beginIndex", 1);
			paging.put("endIndex", 10);
			model.addAttribute("paging", paging);
			model.addAttribute("videos", returnMap);
			logger.debug("视频搜索"+returnMap.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "video/video";
	}
	
	/**
	 * @param model
	 * @return
	 * 上架视频
	 */
	@GetMapping("/push_solr")
	public String pushSolr(Model model,RedirectAttributes attr){
		List<VideoVo> videos = videoMapper.findAll();
		
		int success = 0;
		int fail = 0;
		Collection<SolrInputDocument> docs = new HashSet<SolrInputDocument>();
		for(VideoVo video : videos) {
			SolrInputDocument doc = toSolrInputDocument(video);
			docs.add(doc);
			success++;
		}
		
		//更新solr中数据
		HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
				.withConnectionTimeout(10000).withSocketTimeout(60000)
				.build();
		
		try {
			UpdateResponse res = client.add(solrCore, docs);
			int status = res.getStatus();
			System.err.println("status ::: "+status);
			client.commit(solrCore);
			attr.addFlashAttribute("flash", "上架成功:"+success+" ; 上架失败:"+fail);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		Map<String,Integer> paging = new HashMap<String,Integer>();
		paging.put("totalPages", 1);
		paging.put("currentIndex", 1);
		paging.put("params", null);
		paging.put("beginIndex", 1);
		paging.put("endIndex", 10);
		model.addAttribute("paging", paging);
		model.addAttribute("videos", videos);
		return "redirect:./videos/";
	}
	
	public SolrInputDocument toSolrInputDocument(VideoVo v) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", v.getId());
		doc.addField("name", v.getName());
		doc.addField("url", v.getUrl());
		doc.addField("area", v.getArea());
		doc.addField("year", v.getYear());
		doc.addField("description", v.getDescription());
		doc.addField("director", v.getDirector());
		doc.addField("actor", v.getActor());
		doc.addField("isOn", v.getIsOn());
		
		if(v.getChannelId() != null) {
			doc.addField("channelId", v.getChannelId());
		}
		
		if(v.getProductId() != null) {
			doc.addField("productId", v.getProductId());
		}
		
		if(v.getProvinceId() != null) {
			doc.addField("provinceId", v.getProvinceId());
		}
		
		if(v.getCityId() != null) {
			doc.addField("cityId", v.getCityId());
		}
		
		if(v.getAreaId() != null) {
			doc.addField("areaId", v.getAreaId());
		}
		
		if(!StringUtils.isEmpty(v.getProductsId())) {
			doc.addField("productsId", v.getProductsId());
		}
		
		return doc;
	}
	
	/*public static void main(String[] args) {
		String s = "<font color=\"red\">上海</font>堡垒2000";
		String ss = s.substring(13, 16);
		System.out.println(ss);
	}*/
}
