package com.football.web;

import com.alibaba.fastjson.JSONObject;
import com.football.common.ConfigBean;
import com.football.domain.LoginData;
import com.football.service.ResAndRepMessService;
import com.football.util.HelperClazz;
import com.football.util.HttpRequest;
import com.football.util.JsonUtils;
import com.football.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by lenovo on 2018-3-23.
 */

@Controller
@RequestMapping(value = "/loginapi")
public class LoginController {

    @Autowired
    private ResAndRepMessService resAndRepMessService;

    @Autowired
    public ConfigBean configBean;

    /**
     * @Description:调用登录接口
     * @param:[username, password]
     * @return java.lang.String
     * @Date:  2018-3-23 11:11上午
     * @Author:王陈
     *
     */
    @RequestMapping("/login")
    @ResponseBody
    public  void login(HttpServletResponse response,String username, String password){
        password = Md5.MD5(password);
        String responsedata = HttpRequest.sendGet(configBean.getUrlandport()+configBean.getLoginurl(),"userName="+username+"&passWord="+password);
        resAndRepMessService.save(HelperClazz.setobj(configBean.getUrlandport()+configBean.getLoginurl()+"?userName="+username+"&passWord="+password,responsedata,username));
        LoginData loginData = (LoginData) JsonUtils.jsonToObject(responsedata,LoginData.class);
        if("200".equals(loginData.getCode())){
            String token = UUID.randomUUID().toString();
            HelperClazz.setTokenToRedis(token,loginData.getData().get("salt"),60*60*24);
            HelperClazz.renderJson(response, "{\"success\":true,\"token\":\""+ token+"\",\"code\":\"" + loginData.getCode() + "\",\"username\":\"" + username + "\"}");
        }else{
            HelperClazz.renderJson(response, responsedata.replace("}",",\"success\":false}"));
        }
    }

}
