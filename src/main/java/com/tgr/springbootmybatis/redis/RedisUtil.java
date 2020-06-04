package com.tgr.springbootmybatis.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author tgr redsi工具类
 */
@Component
public class RedisUtil {
	private final String FUTURE_URLS = "future_urls";
	private final String OK_URLS = "ok_urls";
	private final String FAIL_URLS = "fail_urls";
	private final String DOWNLOADING_URLS = "downloading_urls";

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 添加到将来集合 sorted_set : 不重复,有序，使用时间戳作为score。 以系统时间作为score，实现队列效果，并且去重.
	 * 集合中的key是http://www.sohu.com/1/2.html#n , n表示连接级别
	 */
	public void addToFutures(String url, int n) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(url, FUTURE_URLS);
			jedis.zadd(key, System.currentTimeMillis(), url + "#" + n);
		} finally {
			jedis.close();
		}

	}

	/**
	 * 操纵组合url
	 */
	public void addToFutures(String comboUrl) {
		String url = new UrlUtil().extractUrl(comboUrl);
		int level = new UrlUtil().extractLevel(comboUrl);
		addToFutures(url, level);

	}

	/**
	 * 添加到ok集合 Set : 不重复，无序
	 */
	public void addToOks(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String url = new UrlUtil().extractUrl(comboUrl);
			String key = getKeyName(url, OK_URLS);
			jedis.sadd(key, comboUrl);
		} finally {
			jedis.close();
		}

	}

	public void addToOks(String url, int level) {
		addToOks(url + "#" + level);
	}

	/**
	 * 累加失败次数到失败集合 hash ： key(url) - value(次数) hincr
	 */
	public Long incrToFails(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, FAIL_URLS);
			Long L = jedis.hincrBy(key, comboUrl, 1);
			return L;
		} finally {
			jedis.close();
		}

	}

	public Long incrToFails(String url, int level) {
		return incrToFails(url + "#" + level);
	}

	/**
	 * 添加到正在下载集合 Set : 不重复， 无序
	 *
	 */
	public void addToDownloadings(String url, int level) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(url, DOWNLOADING_URLS);
			jedis.sadd(key, url + "#" + level);
		} finally {
			jedis.close();
		}

	}

	public void addToDownloadings(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, DOWNLOADING_URLS);
			jedis.sadd(key, comboUrl);
		} finally {
			jedis.close();
		}

	}

	/**
	 * 从将来集中剪切出下一个comboUrl地址 返回组合url
	 */
	public String getNextUrl(String url) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(url, FUTURE_URLS);
			// 启动事务
			Transaction tx = jedis.multi();
			// 查询最上面的元素
			Response<Set<String>> list = tx.zrange(key, 0, 0);
			// 将其删除
			tx.zremrangeByRank(key, 0, 0);
			// 提交事务
			tx.exec();
			// get()方法需要在exec之后访问
			Set<String> urls = list.get();
			if (urls != null && !urls.isEmpty()) {
				return urls.iterator().next();
			}
			return null;
		} finally {
			jedis.close();
		}

	}

	/**
	 * 获得key的名称
	 */
	public String getKeyName(String url, String keyType) {
		String domain = SiteUtil.getDomianName(url);
		return domain + "_" + keyType;
	}

	/**
	 * 判断将来集中是否存在指定url
	 */
	public boolean existsInFutures(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, FUTURE_URLS);
			boolean b = jedis.zrank(key, comboUrl) == null ? false : true;
			return b;
		} finally {
			jedis.close();
		}

	}

	public boolean existsInFutures(String url, int level) {
		return existsInFutures(url + "#" + level);
	}

	/**
	 * 判断ok集中是否存在指定url
	 */
	public boolean existsInOks(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, OK_URLS);
			boolean b = jedis.sismember(key, comboUrl);
			return b;
		} finally {
			jedis.close();
		}

	}

	public boolean existsInOks(String url, int level) {
		return existsInOks(url + "#" + level);

	}

	/**
	 * 判断失败集中是否存在指定url
	 */
	public boolean existsFails(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, FAIL_URLS);
			boolean b = jedis.hexists(key, comboUrl);
			return b;
		} finally {
			jedis.close();
		}

	}

	public boolean existsFails(String url, int level) {
		return existsFails(url + "#" + level);
	}

	/**
	 * 判断下载集中是否存在指定url
	 */
	public boolean existsDownloadinds(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, DOWNLOADING_URLS);
			boolean b = jedis.sismember(key, comboUrl);
			return b;
		} finally {
			jedis.close();
		}

	}

	public boolean existsDownloadinds(String url, int level) {
		return existsDownloadinds(url + "#" + level);
	}

	/**
	 * 获取指定url地址的下载失败次数
	 */
	public int getFailCounts(String comboUrl) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(comboUrl, FAIL_URLS);
			String count = jedis.hget(key, comboUrl);
			return count == null ? 0 : Integer.parseInt(count);
		} finally {
			jedis.close();
		}

	}

	public int getFailCounts(String url, int level) {
		return getFailCounts(url + "#" + level);
	}

	/**
	 * 从下载集合中删除url
	 */
	public void delFromDownloadings(String url, int level) {
		Jedis jedis = getJedis();
		try {
			String key = getKeyName(url, DOWNLOADING_URLS);
			jedis.srem(key, url + "#" + level);
		} finally {
			jedis.close();
		}

	}

	public Jedis getJedis() {
		return jedisPool.getResource();
	}

}
