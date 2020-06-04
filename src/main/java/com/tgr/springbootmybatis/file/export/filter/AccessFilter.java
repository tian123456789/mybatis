package com.tgr.springbootmybatis.file.export.filter;

import com.alibaba.fastjson.JSONObject;
import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.component.BlackListUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据后台查询权限校验，对于非法请求，将限制访问
 */
@Component
@ServletComponentScan
@WebFilter(filterName = "accessFilter", urlPatterns = "/export/*")
public class AccessFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Value("${data.export.accessToken}")
    private String accessToken = "abcd1234qwer";

    @Value("${data.download.accessToken}")
    private String downloadToken = "wcptbtptp";

    @Value("${nginx.context.path}")
    private String nginxContextPath = "";

    private Set<String> securityUrls;//安全访问的URL,需提供口令

    @Override
    public void init(FilterConfig filterConfig) {
        securityUrls = new HashSet<>();
        securityUrls.add(nginxContextPath+"/export/filesystem/download");
        securityUrls.add(nginxContextPath+"/export/modifyDataById");
        securityUrls.add(nginxContextPath+"/export/syslog");
        logger.info("安全访问的URL:"+securityUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        String ip = BlackListUtil.getRemoteIp(request);
        logger.info("remoteIp----"+ip);
        logger.info("url----"+url);
        String access = request.getParameter("access");

        //判断黑名单
        if(BlackListUtil.onAccessUrlAllowed(ip,url)){
            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(JSONObject.toJSONString(ResponseResult.getForbiddenErrorResult("请求频繁，请稍后重试")));
            return;
        }

        //返回权限不足
        if(!StringUtils.equals(access,accessToken)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(ResponseResult.getForbiddenErrorResult("请求权限不足")));
//            response.setStatus(HttpStatus.FORBIDDEN.value());
            BlackListUtil.addBlackIp(ip,url);
            return;
        }

        //请求关键接口，需提供额外口令
        if(securityUrls.contains(url)){
            String token = request.getParameter("token");
            if(!StringUtils.equals(token,downloadToken)){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONObject.toJSONString(ResponseResult.getForbiddenErrorResult("口令错误，请稍后重试")));
                BlackListUtil.addBlackIp(ip,url);
                return;
            }
        }

        request.setAttribute("nginxContextPath",this.nginxContextPath);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
