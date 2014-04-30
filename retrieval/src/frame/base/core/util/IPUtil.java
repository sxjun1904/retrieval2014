package frame.base.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class IPUtil {
	/**
	 * 获取本地ip地址
	 * @return
	 */
	public String getLinuxlocalIp() {
		return getLinuxlocalIp(false);
	}
	
	/**
	 * 获取本地ip地址
	 * @param allips true返回本机对应的多个ip false 返回单个ip
	 * @return
	 */
	public String getLinuxlocalIp(boolean allips) {
		StringBuffer ip_sb = new StringBuffer();
			try {
				Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				while (allNetInterfaces.hasMoreElements()) {
					NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
					Enumeration addresses = netInterface.getInetAddresses();
					boolean isfind = false;
					while (addresses.hasMoreElements()) {
						ip = (InetAddress) addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							if(!allips){
								//System.out.println("本机名"+ip.getLocalHost());
								ip_sb.append(ip.getLocalHost());
								isfind = true;
								break;
							}else{
								//System.out.println("本机的IP = " + ip.getHostAddress());
								ip_sb.append(ip.getHostAddress()+"/");
							}
							
						}
					}
					if(isfind)
						break;
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return ip_sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(new IPUtil().getLinuxlocalIp(true));
	}
}
