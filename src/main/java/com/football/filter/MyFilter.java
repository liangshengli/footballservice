package com.football.filter;

import com.football.util.HelperClazz;
import com.football.util.RedisProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter implements Filter {
    Log log = LogFactory.getLog(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init IndexFilter");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","Content-Type,token");

        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");

        String url = request.getServletPath();
        System.out.println("走拦截器了====================");
        // 包含尾缀名的或者为登录相关请求的访问放行
        if (url.matches("\\S*(loginapi|message|pushMessage|pushio)\\S*$"))
        {
            try {
                filterChain.doFilter(request, response);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            // 判断redis中用户是否为空

            String token =  req.getParameter("token");
            System.out.println(token+"===============");
            if(token!=null&&!token.equals("")){
                Jedis jedis =  RedisProvider.getJedis();
                byte[] person  =  jedis.get(token.getBytes());
                if(person!=null){
                    jedis.expire(token,300);
                    String  key  = req.getParameter("token");
                    jedis.expire(key,300);
                    filterChain.doFilter(request, response);

                }else{
                    HelperClazz.renderJson(response, "{\"success\":false,\"msg\":\"登录超时！\"}");
                }
            }else{
                HelperClazz.renderJson(response, "{\"success\":false,\"msg\":\"token有误！\"}");
            }

        }

    }

    @Override
    public void destroy() {

    }
}
