package org.apache.activemq.broker.region.policy;
 
 import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.activemq.broker.jmx.ConnectionViewMBean;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.web.BrokerFacade;
import org.apache.activemq.web.SingletonBrokerFacade;
import org.eclipse.jetty.util.log.Log;
 
 /**
  * ClientIdFilterDispatchPolicy dispatches messages in a topic to a given
  * client. Then the message with a "PTP_CLIENTID" property, can be received by a
  * mqtt client with the same clientId.
  * 
  * @author kimmking (kimmking.cn@gmail.com)
  * @date 2013-12-20
  * 
  * @org.apache.xbean.XBean
  */
 public class ClientIdFilterDispatchPolicy extends SimpleDispatchPolicy {
 
 	public static final String PTP_CLIENTID = "PTP_CLIENTID";
 	public static final String PTP_SUFFIX = ".PTP";
 
 	private String ptpClientId = PTP_CLIENTID;
 	private String ptpSuffix = PTP_SUFFIX;
 
 	public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
 
 		Object _clientId = node.getMessage().getProperty(ptpClientId);
 		ActiveMQDestination _destination = node.getMessage().getDestination();
 
 		int count = 0;
 		for (Subscription sub : consumers) {
 			// Don't deliver to browsers
 			if (sub.getConsumerInfo().isBrowser()) {
 				continue;
 			}
 			// Only dispatch to interested subscriptions
 			if (!sub.matches(node, msgContext)) {
 				sub.unmatched(node);
 				continue;
 			}
 			//add by sxjun 2014-6-4 start
 			Log.debug("sxjun=>_clientId:"+_clientId+";sub.getContext().getClientId():"+sub.getContext().getClientId());
 			if (_clientId != null && _destination.isTopic() &&(sub.getContext().getClientId()).startsWith(((String) _clientId))
 					&& _destination.getQualifiedName().endsWith(this.ptpSuffix)) {
				sub.add(node);
	 			count++;
 			} else {
 				sub.unmatched(node);
 			}
 			//add by sxjun 2014-6-4 end
 			
/* 			if (_clientId != null && _destination.isTopic() && _clientId.equals(sub.getContext().getClientId())
 					&& _destination.getQualifiedName().endsWith(this.ptpSuffix)) {
 				sub.add(node);
 				count++;
 			} else {
 				sub.unmatched(node);
 			}
*/ 		}
 
 		return count > 0;
 	}
 
 	public String getPtpClientId() {
 		return ptpClientId;
 	}
 
 	public void setPtpClientId(String ptpClientId) {
 		this.ptpClientId = ptpClientId;
 	}
 
 	public String getPtpSuffix() {
 		return ptpSuffix;
 	}
 
 	public void setPtpSuffix(String ptpSuffix) {
 		this.ptpSuffix = ptpSuffix;
 	}
 
 }
