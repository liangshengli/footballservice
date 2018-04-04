package com.football.util;

/**
 * Created by mac on 17/9/4.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性

           // connection.setRequestProperty("X-Auth-Token",token);

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！",new Exception(e));
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性

          //  conn.setRequestProperty("X-Auth-Token",token);

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！",new Exception(e));
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        //发送 GET 请求
   /*     String s=HttpRequest.sendGet("http://controller:9696/v2.0/ports", "fixed_ips=ip_address=172.16.1.5","gAAAAABZra-FaOWkFRExwB0w1CWiS0NkJ6sR0W9ZJzyFJUcAVs73ZDP9khVjGCIqaKiEEjyB1tTpuFOz-umXvvtzh2p5klfFSYJieyKocJ6B8eXTBHzK6OLjz7itxmST12iJPUN2iNOVsfXEwnbgLSkQ6FVYOXDtixbLmXpYAjLk1QoXT7ntKBk");
        System.out.println(s);

        JSONObject jobj= JSON.parseObject(s);
        String portsStr=jobj.get("ports").toString();

        JSONArray portArray = JSON.parseArray(portsStr);

        JSONObject ss = JSON.parseObject(portArray.get(0).toString());
        String id = ss.get("id").toString();
        String network_id = ss.get("network_id").toString();
        System.out.println(id);
        System.out.println(network_id);*/


        //JSONObject portjson = JSON.parseObject(portsStr);

        //List<Map<String,Object>> missionList = JSON.parseObject(s, new TypeReference<ArrayList<Map<String,Object>>>(){});


        //String port_id = portjson.get("id").toString();
        //String network_id = portjson.get("network_id").toString();
        //System.out.println(port_id);
        //System.out.println(network_id);

        //发送 POST 请求
       // String sr=HttpRequest.sendPost("http://controller:9696/v2.0/floatingips", "{\n" +
//                "\t\"floatingip\":{\n" +
//                "\t\t\"floating_network_id\":\"dbfec69f-52ed-4d4f-8858-24b4ea167d6a\",\n" +
//                "\t\t\"port_id\":\"aaf98416-f66c-4a3e-882a-371d02528c72\",\n" +
//               // "\t\t\"fixed_ip_address\":\"172.16.1.5\",\n" +
//                "\t\t\"floating_ip_address\":\"10.40.32.159\"\n" +
//                "\t}\n" +
//                "}","gAAAAABZrZuflykpkiDYMklNkCYV7F5wM1pM-GPvq-Vkbuh_WVirI7BcHfC4KiGJJL7SozVnyXlQaVQIinckcmn_iSay8rY6BJi8apkkQ2AsNIzqAtCoo_qa7XZWoxQZuBmw5Qt9P4RKVjVmMYir-ax6yw93923qLYhvKiy4UhHqPTraRA4G5lU");
//        System.out.println(sr);


//        Map m = new HashMap();
//        Map m1 = new HashMap();
//        m1.put("floating_network_id","bfec69f-52ed-4d4f-8858-24b4ea167d6a");
//        m1.put("port_id","aaf98416-f66c-4a3e-882a-371d02528c72");
//        m1.put("floating_ip_address","10.40.32.159");
//        m.put("floatingip",m1);
//
//        String jsonparam = JSON.toJSONString(m);
//        System.out.println(jsonparam);



   /*     String ip="10.40.32.157";
        ip=ip.substring(0,ip.lastIndexOf("."));
        System.out.println(ip);*/



       // String s=HttpRequest.sendGet("http://controller:9696/v2.0/floatingips", "floating_ip_address=10.40.32.3","gAAAAABZupx3mkiKy8yaHhuYfyfnem_SM4GJ8zK6iEVuZOBpjAOi6Nqm4SV0bmkPl6Gt3tmw5W6kyjD9BhZDeJwnRICtvVReWU4EAsJTM83sbC3cXupCPUAXTICbiM3cCHdGaDD3OxTLfTPz2j0NcYI5F4uZ8YcHqY62xDQZunXLQkbCzuf6PH0");
       // System.out.println(s);

        String s= HttpRequest.sendGet("http://114.55.232.199:8080/SsG/api/login.action","userName=test1&passWord=202cb962ac59075b964b07152d234b70");
        System.out.println(s);

        //登录接口
//        String s1= HttpRequest.sendPost("http://66.133.89.51/app/member/new_login.php","username=qqqq&passwd=aaa");
//        System.out.println(s1);

        //登录接口
//        String s1= HttpRequest.sendPost("http://114.55.232.199:8080/SsG/api/login.action","userName=User_1&password=123");

    }
}