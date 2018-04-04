package com.football.web;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.football.common.ConfigBean;
import com.football.domain.MessUser;
import com.football.domain.Message;
import com.football.domain.MessageLog;
import com.football.domain.RecMessage;
import com.football.rabbitmq.TestRabbitMQRecv;
import com.football.rabbitmq.TestRabbitMQSend;
import com.football.service.MessUserService;
import com.football.service.MessageLogService;
import com.football.util.DateUtils;
import com.football.util.JsonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;
import static org.bouncycastle.jcajce.spec.TLSKeyMaterialSpec.MASTER_SECRET;

/**
 * Created by lenovo on 2018-3-28.
 */

@Controller
@RequestMapping(value = "/message")
public class MessageController {


    @Autowired
    private MessageLogService messageLogService;

    @Autowired
    public ConfigBean configBean;

    @Autowired
    private MessUserService messUserService;

    /**
     * @Description:推送信息
     * @param:[mess_value（信息的内容）, mess_users（推送的所有人）]
     * @return java.lang.String
     * @Date:  2018-3-28 17:36下午
     * @Author:王陈
     *
     */
    @RequestMapping("/sendmessage")
    @ResponseBody
    public  String sendmessage(String mess_value,String mess_users) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        Message message = new Message();
        message.setDatetime(DateUtils.getCurrentDate());
        message.setSendString(mess_value);

        //String json = "{\"datetime\":\""+ DateUtils.getCurrentDate()+"\"，\"sendString\":\"" + mess_value + "\"，\"mess_users\":\"" + mess_users + "\"}";
        String  result = "{\"success\":true,\"msg\":\"推送成功！\"}";
        try {
            String[] users = mess_users.split(";");
            for(String user:users){
                TestRabbitMQSend.sendMQ("/",
                        configBean.getMqUserName(),
                        configBean.getMqPassword(),
                        configBean.getMqHost(),
                        configBean.getExchangeName(),
                        user,
                        JSON.toJSONString(message));

                MessageLog messageLog = setmessagelog(message.getDatetime(),message.getSendString());
                setmessuser(user,messageLog);
            }

        } catch (Exception e) {
            result = "{\"success\":false,\"msg\":\"推送失败！\" ("+e.getMessage()+")}";
            e.printStackTrace();
        }
        return result;
    }



    /**
     * @Description:用一句话描述
     * @param:[mqVHost, mqUserName, mqPassword, mqHost, exchangeName, queueName]
     * @return java.lang.String
     * @Date:  2018-3-30 8:58上午
     * @Author:王陈
     *
     */
    @RequestMapping("/recvmessage")
    public @ResponseBody void recvmessage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setUsername(configBean.getMqUserName());
        factory.setPassword(configBean.getMqPassword());

        Connection rbConnection = null;

        rbConnection = factory.newConnection(TestRabbitMQRecv.makeAddressCluster(configBean.getMqHost()));

        Channel channel = rbConnection.createChannel();
        channel.exchangeDeclare(configBean.getExchangeName(), "direct", true);
        channel.queueDeclare(configBean.getQueueName(), true, false, false, null);


        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(configBean.getQueueName(), true, consumer);
        List<MessageLog> list = null;
        PrintWriter out = response.getWriter();
        while (true)
        {

            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(message+"============");

  /*         // Message message1 = JSON.parseObject(message, new TypeReference<Message>() {});

            Message message1 = (Message) JsonUtils.jsonToObject(message,Message.class);
            MessageLog messageLog = setmessagelog(message1.getDatetime(),message1.getSendString());
            String[] users = message1.getMess_users().split(";");
            for(int i=0; i <users.length; i++){
                setmessuser(users[i],messageLog);
            }*/
//            list =  messageLogService.getmessloglist(messageLog);
            out.println("data:" + message);
           // out.println("data:1111");
            out.println();
            out.flush();
            //Thread.sleep(1000*6);
        }
    }


    /**
     * @Description:给messagelog对象赋值
     * @param:[messageanddate, messagecontent]
     * @return com.football.domain.MessageLog
     * @Date:  2018-3-28 17:56下午
     * @Author:王陈
     *
     */
    private MessageLog setmessagelog(String messageanddate,String messagecontent){
        MessageLog messageLog = new MessageLog();
        messageLog.setMessagesenddate(messageanddate);
        messageLog.setMessagecontent(messagecontent);
        MessageLog messageLog1 =  messageLogService.save(messageLog);//保存消息信息
        return  messageLog1;
    }


    /**
     * @Description:给messuser对象赋值
     * @param:[username, mess_id, read_status]
     * @return com.football.domain.MessUser
     * @Date:  2018-3-28 17:57下午
     * @Author:王陈
     *
     */
    private MessUser setmessuser(String username,MessageLog messageLog){
        MessUser messUser = new MessUser();
        messUser.setUsername(username);
        messUser.setCdate(DateUtils.getCurrentDate());
        messUser.setMess_id(messageLog);
        messUser.setRead_status("0");
        messUser =  messUserService.save(messUser);
        return  messUser;
    }

    /**
     * @Description:根据当前登陆人查询下面所有的消息
     * @param:[messUser]
     * @return java.util.List<com.football.domain.MessUser>
     * @Date:  2018-3-30 9:11上午
     * @Author:王陈
     *
     */
    @RequestMapping("/getmessbyuser")
    public @ResponseBody List<MessUser>  getmessbyuser(MessUser messUser) throws Exception {
                  return messUserService.getmessuserlist(messUser);
    }

    @RequestMapping("/test")
    public @ResponseBody List<MessUser>  test() throws Exception {


        JPushClient jpushClient = new JPushClient("b3bb45a7ad7a03387229bc5a", "e356dc8c13116c4bd6711509", null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();
        PushPayload.messageAll("ttttt");

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);
            //LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {

           e.printStackTrace();
            System.out.println("Connection error, should retry later");
            // Connection error, should retry later
            //LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            e.printStackTrace();
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());

            // Should review the error, and fix the request
            //LOG.error("Should review the error, and fix the request", e);
            //LOG.info("HTTP Status: " + e.getStatus());
            //LOG.info("Error Code: " + e.getErrorCode());
            //LOG.info("Error Message: " + e.getErrorMessage());
        }
        return null;
    }

    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("通知");
    }


    public static void main(String[] argv)
    {
        JPushClient jpushClient = new JPushClient("b3bb45a7ad7a03387229bc5a", "e356dc8c13116c4bd6711509", null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();
        //PushPayload.messageAll("ttttt");

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);
            //LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {

            e.printStackTrace();
            System.out.println("Connection error, should retry later");
            // Connection error, should retry later
            //LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            e.printStackTrace();
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());

            // Should review the error, and fix the request
            //LOG.error("Should review the error, and fix the request", e);
            //LOG.info("HTTP Status: " + e.getStatus());
            //LOG.info("Error Code: " + e.getErrorCode());
            //LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

}