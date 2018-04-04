package com.football.util;

import com.football.domain.ResAndRepMess;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by kzhou on 2015/4/22.
 */
public class HelperClazz {
    private static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain; charset=utf8";
    private static final String DEFAULT_HTML_CONTENT_TYPE = "text/html; charset=utf8";
    private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json; charset=utf8";

    //获取当前日期
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    //获取当前时间
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static int getCurrentPage(HttpServletRequest request){
        int offset = Integer.parseInt(request.getParameter("offset")==null?"0":request.getParameter("offset"));
        int limit = Integer.parseInt(request.getParameter("limit")==null?"10":request.getParameter("limit"));
        int currentPage = offset/limit+1;
        return currentPage;
    }
    public static int getPageSize(HttpServletRequest request){
        return Integer.parseInt(request.getParameter("limit") == null ? "10" : request.getParameter("limit"));
    }


    //校验值是否存在
    public static int chkIsExist(JdbcTemplate jdbcTemplate, String table, String col, String colVal){
        final StringBuilder sql = new StringBuilder();
        sql.append(" select count(*) as c from ");
        sql.append(table);
        sql.append(" where ").append(col).append(" = ").append("'" + colVal + "'");
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    //url中文编码
    public static String encode(String s){
        try {
            return URLEncoder.encode(s, "utf8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    //url中文解码
    public static String decode(String s){
        try {
            return URLDecoder.decode(s, "utf8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    //获得登陆信息id
    //flag==0 is ID;flag==1 is Name;flag==2 is admintype[1段管理员2车队管理员]
    public static String getLoginInfo(HttpServletRequest request, int idx){
        HttpSession session = request.getSession();
        String loginInfo = (String)session.getAttribute("examLogin");
//        Cookie current = getLoginCookie(request);

        if(loginInfo != null){
            String[] loginInfos = loginInfo.split("@");
            return HelperClazz.decode(loginInfos[idx]);
        }else{
            return "";
        }

    }

    //获得登陆信息cookie
//    private static Cookie getLoginCookie(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        Cookie current = null;
//        for(Cookie cookie :cookies){
//            if(cookie.getName().equals(BaseController.LOGIN_COOKIE_NAME)){
//                current = cookie ;
//                break;
//            }
//        }
//        return current;
//    }
    //response返回内容
    public static void renderText(HttpServletResponse response, String text) {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(DEFAULT_TEXT_CONTENT_TYPE);
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null)
                writer.close();
        }
    }

    //response返回内容
    public static void renderJson(HttpServletResponse response, String text) {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(DEFAULT_JSON_CONTENT_TYPE);
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null)
                writer.close();
        }
    }

    //生成随机名称，用于上传文件命名
    public static String getRandomName() {
        Random r = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        StringBuffer sb = new StringBuffer();
        sb.append(r.nextInt(100));
        sb.append(r.nextInt(100));
        sb.append("_");
        sb.append(sdf.format(new Date()));
        sb.append("_");
        sb.append(r.nextInt(100));
        sb.append(r.nextInt(100));
        return sb.toString();
    }

    //获得文件名后缀
    public static String getFileExt(String fileName){
        int idx = fileName.lastIndexOf(".");
        String ext = null;
        if(idx != -1){
            ext = fileName.substring(idx + 1);
            if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")){
                return ext;
            }
        }
        return ext;
    }

    public static boolean isEmpty(Object o){
        return StringUtils.isEmpty(o);
    }

    //获得web项目根目录
    public static String getWebRootPath(HttpServletRequest request){
        ServletContext context = request.getServletContext();
        return context.getRealPath("/");
    }

    //获得downloadFilePath
    public static String getDownloadFilePath(HttpServletRequest request){
        return getWebRootPath(request) + File.separator + "download_files";
    }
    //获得uploadFilePath
    public static String getUploadFilePath(HttpServletRequest request){
        return getWebRootPath(request) + File.separator + "upload_files";
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /***
     * 返回前台html内容
     * @param response
     * @param text
     */
    public static void renderHtml(HttpServletResponse response, String text) {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(DEFAULT_HTML_CONTENT_TYPE);
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null)
                writer.close();
        }
    }


    public static ResAndRepMess setobj(String res,String rep,String username){
        ResAndRepMess rrm = new ResAndRepMess();
        rrm.setRequestmess(res);
        rrm.setResponsemess(rep);
        rrm.setUsername(username);
        rrm.setDate(DateUtils.getCurrentDate());
        return  rrm;
    }

    /**
     * 存token信息至redis
     * @param token token
     * @param seconds 超时时间
     */
    public static void setTokenToRedis(String token,String username,int seconds){

        Jedis jedis =  RedisProvider.getJedis();

        jedis.set(token.getBytes(), username.getBytes());

        jedis.expire(token,seconds);

        RedisProvider.returnResource(jedis);

    }


}
