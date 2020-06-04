package com.tgr.springbootmybatis.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplitDateUtil {

	 @SuppressWarnings("all")
	 public static void main(String[] args) throws ParseException {
	 	
		 List<Map<String, String>> dateList = dateSplit(4,"2017-2-11 09:23:59","2018-12-11 22:59:59");
		 System.out.println(dateList.toString());
	 }
	 
	 @SuppressWarnings("deprecation")
	 public static List<Map<String, String>> dateSplit(int number , String start_time , String end_time) 
			 throws ParseException{
		 
		 number = Math.abs(number);
		 
	     start_time = start_time.trim();
	     end_time = end_time.trim();
	     
	     Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start_time);
	     Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time);
	     
	     Calendar cStart = Calendar.getInstance();
	     cStart.setTime(startDate);
	     Calendar cEnd = Calendar.getInstance();
	     cEnd.setTime(endDate);
	     
	     int startYear = cStart.get(Calendar.YEAR)-1962;
	     int endYear = cEnd.get(Calendar.YEAR)-1962;
	     
	     int startMonth = cStart.get(Calendar.MONTH);
	     int endMonth = cEnd.get(Calendar.MONTH);
	     
	     int startTotalMonth = (startYear-1)*12+startMonth;
	     int endTotalMonth = (endYear-1)*12+endMonth;
	     
	     int devidMonth = endTotalMonth-startTotalMonth;

	     BigDecimal big = new BigDecimal(devidMonth);
	     BigDecimal big2 = new BigDecimal(number);
	     
	     BigDecimal useBase = big.divide(big2).setScale(0, java.math.RoundingMode.HALF_UP);
	     int useBaseInt = useBase.intValue();
	     
	     List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	     
	     while(true) {
	    	 
	    	  Map<String, String> map = new HashMap<String,String>();
		      
	    	  String start = cStart.getTime().toLocaleString();
		      cStart.add(Calendar.MONTH, useBaseInt);
		      
		      if(cStart.compareTo(cEnd) == -1 || cStart.compareTo(cEnd) == 0) {/* <=*/
			       
		    	   cStart.add(Calendar.MILLISECOND, -1);
			       String end = cStart.getTime().toLocaleString();
			       
			       if(cStart.get(Calendar.YEAR) == cEnd.get(Calendar.YEAR) 
			    		   && cStart.get(Calendar.MONTH) == cEnd.get(Calendar.MONTH)
			    		   && cStart.get(Calendar.DAY_OF_MONTH) == cEnd.get(Calendar.DAY_OF_MONTH)) {
			    	   
			    	   end = end_time;
			    	   map.put("start", start);
			    	   map.put("end", end);
			    	   list.add(map);
			    	   break;
			       }
			       
			       map.put("start", start);
		    	   map.put("end", end);
		    	   list.add(map);
			       
			       cStart.add(Calendar.MILLISECOND, 1);
		       
		      }else {/* >*/
		    	  
			       String end = cEnd.getTime().toLocaleString();
			       map.put("start", start);
		    	   map.put("end", end);
		    	   list.add(map);
			       break;
		      }
		      
		     }
	     
		 return list;
	 }
}
