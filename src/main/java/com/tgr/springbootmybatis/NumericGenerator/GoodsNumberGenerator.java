
package com.tgr.springbootmybatis.NumericGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.RedisService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class GoodsNumberGenerator {

	//@Autowired
	private GoodsRepository goodsRepository = null;

	@Autowired
	private RedisService redisService;
	
	public GoodsNumberGenerator(RedisConnectionFactory factory) {
		RedisService<Integer> redis = new RedisService<Integer>(factory);
		redis.afterPropertiesSet();
		redisService = redis;
	}

	public String get() {
		String no = _getNo();
		while (goodsRepository.existsByGoodsNo(no)) {
			no = _getNo();
		}
		return no;
	}

	private String _getNo() {

		String prefix = "GoodsNo:";
		String date = new SimpleDateFormat("yyMMdd").format(new Date());
		String key = prefix + date;

		Integer value = (Integer) redisService.opsForValue().get(key);
		if (value == null || value == 0) {
			value = 1;
			redisService.opsForValue().set(key, value, 2, TimeUnit.DAYS);
		} else {
			value++;
			redisService.opsForValue().increment(key, 1);
		}
		String no = date + String.format("%06d", value);
		return no;
	}
	
	class GoodsRepository{
		public boolean existsByGoodsNo(String no) {
			return true;
		}
	}
}
