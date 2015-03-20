package org.apache.activemq.web;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author sxjun 2014-3-11
 * 监控activemq的连接
 */
public class InitConnThread  implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(InitConnThread.class);
	private String connectorName;
	 // tcp 地址
    //public static final String BROKER_URL = "tcp://localhost:61616";
    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    //public static final String DESTINATION = "EMsgServerTopic";
    public static final String DESTINATION = "EpointMsgServerTopic";
	
	public InitConnThread(String connectorName) { 
        this.connectorName = connectorName; 
    } 

	@Override
	public void run() {
		BrokerFacade broker = new SingletonBrokerFacade();
		List<String> loginList = new LinkedList<String>();
		
		
		try {
			while(true){
				try {
					LOG.debug("==>开始循环=>");
					List<String> midList = new LinkedList<String>();
					List<String> logoutList = new LinkedList<String>();
					
					Iterator it = broker.getConnections(connectorName).iterator();
					while (it.hasNext()) {
						String conName = (String) it.next();
						ConnectionViewMBean con = broker.getConnection(conName);
						
						if(con!=null){
							if(isNumeric(con.getClientId())){
								LOG.info("==>[connectorName:"+connectorName+";conName:"+conName+";ClientId:"+con.getClientId()+";UserName:"+con.getUserName()+"]");	
								midList.add(con.getClientId());//在线的clientId
							}
						}
						else
							LOG.info("==>连接为空,conName:"+conName);
					}
					logoutList.addAll(loginList);
					logoutList.removeAll(midList);//得到不在离线的用户
					
					//doing something start for logout users
					if(logoutList!=null && logoutList.size()>0){
						for(String logout : logoutList){
							LOG.info("==>检测到即时通讯下线的用户ID:"+logout);
							String json = logout(logout);
							send(json);
						}
						LOG.debug("==>logout user num :"+logoutList.size());
					}
					//doing something end for logout users
					loginList = midList;//得到在线的用户
					Thread.sleep(1000*3);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param logoutList
	 * @return {"users":[{"logout":[{"userid":"用户唯一标识","logintype":"2"}]}]}
	 */
	public String logout(String logout){
		LOG.debug("==>logout clientId :"+logout);
		String json ="{\"users\":{\"logout\":"
		+"{\"userid\":\""+logout+"\",\"logintype\":\"2\"}"
		+ "}}";
		LOG.debug("==>logout json :"+json);
		return json;
	}
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	
	/**
	 * 发送消息
	 * @param message
	 * @throws Exception
	 */
	public void send(String message) throws Exception{
		 TopicConnection connection = null;
	        TopicSession session = null;
	        try {
	        	LOG.info("==>send message");
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
	            publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
	            sendMessage(session, publisher,message);
	            // 提交会话
	            session.commit();
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	            //throw e;
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
	
	public static void sendMessage(TopicSession session, TopicPublisher publisher,String message) throws Exception {
            TextMessage msg = session.createTextMessage();
            //msg.setStringProperty("clientId", "testConn6");
            msg.setText(message);
            publisher.send(msg);
    }
	
	public static void main(String[] args) {
		if(isNumeric("5000"))
			System.out.println("yes");
		else
			System.out.println("no");
		
		System.out.println(String.format("%,09d", -77777)); 
	}
	
}
