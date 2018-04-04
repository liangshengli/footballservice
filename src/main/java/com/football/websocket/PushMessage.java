package com.football.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket服务
 * @author  :  liangsl
 * @time   :  2017.06.30
 */
//指定WebSocket连接的地址，和前端的new WebSocket(".../websocket")相对应
//@ServerEndpoint(value = "/pushMessage", configurator = HttpSessionConfigurator.class)
@ServerEndpoint(value = "/pushMessage/{userid}")
// 用springboot 启动项目时 放开@Compinent注释
@Component
//由于要保存会话，spring boot默认为单例，难以操作，声明为protoType避免错误
@Scope("Prototype")
public class PushMessage {

    private static int onlineCount = 0; //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static CopyOnWriteArraySet<PushMessage> webSocketSet = new CopyOnWriteArraySet<PushMessage>();
    private Session session;    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String userid;      //用户id
    private String username;      //用户名
    private HttpSession httpSession;    //request的session

    private static List list = new ArrayList<>();   //在线列表,记录用户名称
    private static Map routetab = new HashMap<>();  //用户名和websocket的session绑定的路由表

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("userid") String userid, Session session, EndpointConfig config){
        System.out.println("userid==============="+userid);
        this.session = session;
        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1;
        routetab.put(userid, session);   //将用户名和session绑定到路由表
        for(Object k : routetab.keySet()){
            System.out.println(k.toString());        }
//        String message = getMessage("[" + username + "]加入聊天室,当前在线人数为"+getOnlineCount()+"位", "notice",  list);
//        broadcast(message);     //广播
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        list.remove(userid);        //从在线列表移除这个用户
        routetab.remove(userid);
        String message = getMessage("[" + username +"]离开了聊天室,当前在线人数为"+getOnlineCount()+"位", "notice", list);
        broadcast(message);         //广播
    }

    /**
     * 接收客户端的message,判断是否有接收人而选择进行广播还是指定发送
     * @param _message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String _message) {


        JSONObject chat = JSON.parseObject(_message);
        JSONObject message = JSON.parseObject(chat.get("message").toString());
//        if(message.get("to") == null || message.get("to").equals("")){      //如果to为空,则广播;如果不为空,则对指定的用户发送消息
//            broadcast(_message);
//        }else{
            String [] userlist = message.get("to").toString().split(",");
            String [] message_ids = message.get("message_id").toString().split(",");
            //singleSend(_message, (Session) routetab.get(message.get("from")));      //发送给自己,这个别忘了
            for(int i=0;i<userlist.length;i++){
                String user = userlist[i];
                String message_id=message_ids[i];
                if(!user.equals(message.get("from"))){
                    if(routetab.get(user)!=null){
                        singleSend(_message, (Session) routetab.get(user));     //分别发送给每个指定用户
                    }else{
                        //更改推送状态为未推送
//                        Message message1 = new Message();
//                        message1.setMessage_id(message_id);
//                        message1.setStatus("0");
//                        messageDao.updStatus(message1);
                    }
                }
            }
//        }
    }

    /**
     * 发生错误时调用
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        error.printStackTrace();
    }

    /**
     * 广播消息
     * @param message
     */
    public void broadcast(String message){
        for(PushMessage chat: webSocketSet){
            try {
                chat.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 对特定用户发送消息
     * @param message
     * @param session
     */
    public void singleSend(String message, Session session){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 组装返回给前台的消息
     * @param message   交互信息
     * @param type      信息类型
     * @param list      在线列表
     * @return
     */
    public String getMessage(String message, String type, List list){
        JSONObject member = new JSONObject();
        member.put("message", message);
        member.put("type", type);
        member.put("list", list);
        return member.toString();
    }

    public  int getOnlineCount() {
        return onlineCount;
    }

    public  void addOnlineCount() {
        PushMessage.onlineCount++;
    }

    public  void subOnlineCount() {
        PushMessage.onlineCount--;
    }
}
