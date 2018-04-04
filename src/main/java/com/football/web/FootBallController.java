package com.football.web;

import com.football.common.ConfigBean;
import com.football.domain.ResAndRepMess;
import com.football.service.ResAndRepMessService;
import com.football.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

/**
 * Created by lenovo on 2018-3-23.
 */
@Controller
@RequestMapping(value = "/footballapi")
public class FootBallController {

    @Autowired
    private ResAndRepMessService resAndRepMessService;

    @Autowired
    public ConfigBean configBean;




    /**
     * @Description:获取今日推荐数据
     * @param:[salt]
     * @return java.lang.String
     * @Date:  2018-3-23 11:15上午
     * @Author:王陈
     *
     */
    @RequestMapping("/gettoday")
    @ResponseBody
    public  String gettoday(String token){
        String salt = getsalt (token);
        String response = HttpRequest.sendGet(configBean.getUrlandport()+configBean.getTodaymess(),"salt="+salt);
        resAndRepMessService.save(HelperClazz.setobj(configBean.getUrlandport()+configBean.getTodaymess()+"?salt="+salt,response,""));
        return  response;
    }



    /**
     * @Description:获取历史推荐查询
     * @param:[token, targetPage, search（以联赛名称模糊查询）]
     * @return java.lang.String
     * @Date:  2018-3-28 9:43上午
     * @Author:王陈
     *
     */
    @RequestMapping("/getHistory")
    @ResponseBody
    public  String getHistory(String token,String targetPage,String search){
        String salt = getsalt (token);
        String response = HttpRequest.sendGet(configBean.getUrlandport()+configBean.getHistorymess(),"salt="+salt+"&targetPage="+targetPage);
        resAndRepMessService.save(HelperClazz.setobj(configBean.getUrlandport()+configBean.getHistorymess()+"?salt="+salt+"&targetPage="+targetPage,response,""));
        return  response;
    }



    private String  getsalt (String token){
        Jedis jedis =  RedisProvider.getJedis();
        return new String(jedis.get(token.getBytes()));
    }

}
