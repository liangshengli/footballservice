package com.football.rabbitmq;

import com.rabbitmq.client.*;

/**
 * Created by mac on 17/11/11.
 */
public class TestRabbitMQRecv {


    //队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception
    {
        /*//打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("liangsl");
        factory.setPassword("123564081");
        factory.setHost("10.211.55.8");
        //factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true)
        {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] 接收 '" + message + "'");
        }*/



        recvMQ("/",
                "football",
                "football",
                "10.40.1.173",
                "football_service",
                "footballqueue");



    }


    private static String recvMQ(
            String mqVHost,
            String mqUserName,
            String mqPassword,
            String mqHost,
            String exchangeName,
            String queueName) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(mqVHost);
        factory.setUsername(mqUserName);
        factory.setPassword(mqPassword);

        Connection rbConnection = null;

        rbConnection = factory.newConnection(makeAddressCluster(mqHost));

        Channel channel = rbConnection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", true);
        channel.queueDeclare(queueName, true, false, false, null);


        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(queueName, true, consumer);
        while (true)
        {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] 接收 '" + message + "'");
        }




    }

    public static Address[] makeAddressCluster(String hosts) {
        String[] hostsArray = hosts.split(",");
        Address[] addrArray = new Address[hostsArray.length];

        for (int i = 0; i < hostsArray.length; i++) {
            Address address = new Address(hostsArray[i]);
            addrArray[i] = address;
        }

        return addrArray;
    }

}
