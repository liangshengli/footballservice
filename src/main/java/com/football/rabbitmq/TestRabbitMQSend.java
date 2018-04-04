package com.football.rabbitmq;

import com.football.util.DateUtils;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by mac on 17/11/11.
 */
public class TestRabbitMQSend {


    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception
    {
        /**
         * 创建连接连接到MabbitMQ
         *//*
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("10.211.55.8");
        factory.setUsername("liangsl");
        factory.setPassword("123564081");
        //创建一个连接
        Connection connection = factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();
        //指定一个队列 [*] Waiting for messages. To exit press CTRL+C

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送的消息
        String message = "hello world5555555!";
        //往队列中发出一条消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] 发送 '" + message + "'");
        //关闭频道和连接
        channel.close();
        connection.close();*/

        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String queryString="中超队长来了";

        String json = "{\"uuid\":\"" + id + "\",\"message\":{\"datetime\":\""+ DateUtils.getCurrentDate()+"\"，\"queryString\":\"" + queryString + "\"}}";

        TestRabbitMQSend.sendMQ("/",
                "football",
                "football",
                "10.40.1.173",
                "football_service",
                "footballqueue",
                json);

    }


    public static void sendMQ(
            String mqVHost,
            String mqUserName,
            String mqPassword,
            String mqHost,
            String exchangeName,
            String queueName,
            String json) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(mqVHost);
        factory.setUsername(mqUserName);
        factory.setPassword(mqPassword);

        Connection rbConnection = null;

        rbConnection = factory.newConnection(makeAddressCluster(mqHost));

        Channel channel = rbConnection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", true);
        channel.queueDeclare(queueName, true, false, false, null);

        channel.basicPublish(
                "",
                queueName,
                null,
                json.getBytes());

        channel.close();
        rbConnection.close();
    }

    private static Address[] makeAddressCluster(String hosts) {
        String[] hostsArray = hosts.split(",");
        Address[] addrArray = new Address[hostsArray.length];

        for (int i = 0; i < hostsArray.length; i++) {
            Address address = new Address(hostsArray[i]);
            addrArray[i] = address;
        }

        return addrArray;
    }



}
