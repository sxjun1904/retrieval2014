package org.apache.activemq.web;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
 
/**
 * <b>function:</b> Queue 方式消息发送者
 * @author hoojo
 * @createDate 2013-6-19 下午04:34:36
 * @file QueueSender.java
 * @package com.hoo.mq.topic
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class TopicSender {
    
    // 发送次数
    public static final int SEND_NUM = 1;
    // tcp 地址
    public static final String BROKER_URL = "failover://tcp://192.168.181.80:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "EpointMsgServerTopic";
    
    /**
     * <b>function:</b> 发送消息
     * @author hoojo
     * @createDate 2013-6-19 下午12:05:42
     * @param session 会话
     * @param publisher 发布者
     * @throws Exception
     */    
    public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception {
//        for (int i = 0; i < SEND_NUM; i++) {
            String message = "{\"Users\":{\"Login\":{\"userid\":\"9988\",\"logintype\":\"2\"}}}";//上线
//            String message = "{\"Users\":{\"Logout\":{\"userid\":\"9988\",\"logintype\":\"2\"}}}";//下线
//            String message = "{\"Chat\":{\"SendMessage\":{\"msgid\":\"a36a0ad9-d1a0-4a09-a6e7-2aea787e41e6\",\"msgtype\":\"1\",\"content\":\"[Msg_Record]20140430161110_3-9b650daf30594d30b841849a73ddc437.mp3[/Msg_Record]\",\"sender_userid\":\"9988\",\"receiver_userid\":\"9987\",\"sendtime\":\"2014-04-30 16:11:14\"}}}";//下线
            
           /* MapMessage map = session.createMapMessage();
            map.setString("text", message);
            map.setLong("time", System.currentTimeMillis());
            System.out.println(map);*/
            TextMessage msg = session.createTextMessage();
            msg.setStringProperty("clientId", "EpointMsgServer");
            msg.setText(message);
            /*ObjectMessage tm = session.createObjectMessage();
	        tm.setStringProperty("test", message);
	        tm.setStringProperty("test", message);*/
            publisher.send(msg);
//        }
    }
    
    public static void run() throws Exception {
        
        TopicConnection connection = null;
        TopicSession session = null;
        try {
            // 创建链接工厂
            TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(DESTINATION);
            // 创建消息发送者
            TopicPublisher publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, publisher);
            // 提交会话
            session.commit();
            
        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭释放资源
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
    	/*Connection connection = null;
	    	for(int i=2000;i<3000;i++){
	    		 ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.200.117:61616");
	            
	    		 // 通过工厂创建一个连接
	             connection = factory.createConnection();
	             connection.setClientID(String.valueOf(i));
	             // 启动连接
	             connection.start();
	             System.out.println(i);
	//             connection.stop();
	//             connection.close();
	    			
	    	}*/
    	while(true){
        TopicSender.run();
    	Thread.sleep(5000);
    	}
    	/*TopicSender sm = new TopicSender();
        sm.setupQueueConnection("58.213.119.196", "jmsQueueba20a55a1e7947b7bb97a1b3f50df013");
        // sm.sendAMessage(args[0]);
        sm.sendAMessage("哇哈哈哈，成功了！", "testadapterid");
        sm.stopQueueConnection();*/
    	/*TopicSender sm = new TopicSender();
        sm.testJMSQueueConnectionWithCache("58.213.119.196", "jmsQueueba20a55a1e7947b7bb97a1b3f50df013");*/
    }
    
//    
//    Destination destination ;
//	Connection connection ;
//	Session session ;
//	MessageProducer producer ;
//	
//	public static Connection getConnection(String ip) throws JMSException{
//	    String user = ActiveMQConnection.DEFAULT_USER;         
//	    String password = ActiveMQConnection.DEFAULT_PASSWORD;       
//	    String url = "failover://tcp://"+ip+":1109";
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(      
//                user, password, url);      
//	    Connection connection = connectionFactory.createConnection();
//	    return connection;
//    }
//
//    
//    private void setupQueueConnection(String ip, String queueName) throws JMSException, NamingException {
//
//    	try {
//    		    	connection =getConnection(ip);
//    		    	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//    		    	destination = session.createQueue(queueName);
//    		    	connection.start();
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
//    	    }
//    
//    private void sendAMessage(String msg, String destinationAdapterId) throws JMSException {
//
//		producer = session.createProducer(destination);
//		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//		producer.setTimeToLive(0);//add by sxjun 2014-1-26
//        ObjectMessage tm = session.createObjectMessage();
//        tm.setObject(msg);
//        tm.setStringProperty("adapterId", destinationAdapterId);
//        producer.send(tm);
//        producer.close();
//    }
//    
//    private void stopQueueConnection() throws JMSException {
//        connection.stop();
//        session.close();
//        connection.close();
//    }
//    
//    public boolean testJMSQueueConnectionWithCache(String ip, String queueName) {
//		boolean isconnect = false;
//		try {
//			System.out.println( ">>>1.开始测试连接>>>{远程ip:"+ip+",queue:"+queueName+"}。。。");
//			connection = getConnectionSingle(ip);
//			if(connection!=null){
//				connection.start();
//				connection.stop();
//				isconnect = true;
//				System.out.println(">>>2.测试连接成功>>>{远程ip:"+ip+",queue:"+queueName+"}。。。");
//			}else{
//				isconnect = false;
//				System.out.println("2.>>>ESBMsgRemoteManager-->队列信息：" + ip + "--" + queueName
//					+ "此队列连接不上!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("ESBMsgRemoteManager-->队列信息：" + ip + "--" + queueName
//					+ "此队列不存在!Not Exist!");
//			isconnect = false;
//		}finally{
//			try {
//				if(connection!=null)
//					connection.close();
//			} catch (JMSException e) {
//				e.printStackTrace();
//			}
//		}
//		return isconnect;
//	}
//    
//    public static Connection getConnectionSingle(String ip) throws JMSException{
//		String user = ActiveMQConnection.DEFAULT_USER;         
//	    String password = ActiveMQConnection.DEFAULT_PASSWORD;       
//	    String url = "tcp://"+ip+":1109";
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);      
//	    Connection connection = connectionFactory.createConnection();
//	    return connection;
//	}
}

