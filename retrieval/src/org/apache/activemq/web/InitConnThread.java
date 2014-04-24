package org.apache.activemq.web;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.activemq.broker.region.RegionBroker;
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
    public static final String DESTINATION = "EMsgServerTopic";
	
	public InitConnThread(String connectorName) { 
        this.connectorName = connectorName; 
    } 

	@Override
	public void run() {
		BrokerFacade broker = new SingletonBrokerFacade();
		List<String> loginList = new ArrayList<String>();
		
		
		try {
			while(true){
				List<String> midList = new ArrayList<String>();
				List<String> logoutList = new ArrayList<String>();
				
				Iterator it = broker.getConnections(connectorName).iterator();
				while (it.hasNext()) {
					String conName = (String) it.next();
					ConnectionViewMBean con = broker.getConnection(conName);
					LOG.debug("[connectorName:"+connectorName+";conName:"+conName+";ClientId:"+con.getClientId()+";UserName:"+con.getUserName()+"]");	
					midList.add(con.getClientId());//在线的clientId
				}
				logoutList.addAll(loginList);
				logoutList.removeAll(midList);//得到不在离线的用户
				
				//doing something start for logout users
				if(logoutList!=null && logoutList.size()>0){
					for(String logout : logoutList){
						String json = logout(logout);
						send(json);
					}
					LOG.debug("==>logout user num :"+logoutList.size());
				}
				//doing something end for logout users
				loginList = midList;//得到在线的用户
				Thread.sleep(1000*3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param logoutList
	 * @return {"Users":[{"Logout":[{"userid":"用户唯一标识","logintype":"2"}]}]}
	 */
	public String logout(String logout){
		LOG.debug("==>logout clientId :"+logout);
		String json ="{\"Users\":{\"Logout\":"
		+"{\"userid\":\""+logout+"\",\"logintype\":\"2\"}"
		+ "}}";
		LOG.debug("==>logout json :"+json);
		return json;
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
	
	public static void sendMessage(TopicSession session, TopicPublisher publisher,String message) throws Exception {
            TextMessage msg = session.createTextMessage();
            //msg.setStringProperty("clientId", "testConn6");
            msg.setText(message);
            publisher.send(msg);
    }
}
