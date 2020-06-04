
package com.tgr.springbootmybatis.NumericGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.RedisService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ContractNumberGenerator {

	
	private ContractRepository contractRepository;

	@Autowired
	private RedisService<Integer> redisService;

	public ContractNumberGenerator(RedisConnectionFactory factory) {
		RedisService<Integer> redis = new RedisService<Integer>(factory);
		redis.afterPropertiesSet();
		redisService = redis;
	}

	public String get() {
		String no = _getNo();
		while (contractRepository.existsByCtrtNo(no)) {
			no = _getNo();
		}
		return no;
	}

	private String _getNo() {

		String prefix = "ContractNo:";
		String date = new SimpleDateFormat("yyMMdd").format(new Date());
		String key = prefix + date;

		Integer value = redisService.opsForValue().get(key);
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
	
	class ContractRepository{
		public boolean existsByCtrtNo(String no) {
			return true;
		}
	}
}
