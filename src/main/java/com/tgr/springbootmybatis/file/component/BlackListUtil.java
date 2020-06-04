package com.tgr.springbootmybatis.file.component;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * 黑名单工具类
 */
@Component
public class BlackListUtil {

    private static Logger logger = LoggerFactory.getLogger(BlackListUtil.class);

    //冻结时间(分钟)
    private final static int TIME_LIMIT =  1;

    //最大尝试次数
    private final static long MOST_TIMES_IN_TIME_LIMIT = 3;

    private final static String REDIS_PRE = "redis_black_";

    private static RedisService<String> redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        BlackListUtil.redisService = redisService;
    }

    /**
     * 获取客户端请求的ip地址
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 添加ip地址到黑名单，对于黑名单的ip，将封禁1分钟禁止访问指定的url
     * @param ip 客户端ip
     * @param url 客户端非法请求的url
     */
    public static void addBlackIp(String ip,String url) {
        redisService.opsForValue().set(REDIS_PRE + ip, url, TIME_LIMIT, TimeUnit.MINUTES);
    }

    /**
     * ip地址是否在黑名单之内
     * @param ip 客户端ip
     * @return
     */
    public static boolean isBlackIp(String ip) {
        Object obj = redisService.opsForValue().get(REDIS_PRE + ip);
        return obj != null;
    }

    /**
     * ip地址是否可以访问指定的url
     * @param ip 客户端ip
     * @param url 客户端请求的url
     * @return
     */
    public static boolean onAccessUrlAllowed(String ip, @NotNull String url) {
        String obj = redisService.opsForValue().get(REDIS_PRE + ip);
        return StringUtils.equals(url,obj);
    }
}
