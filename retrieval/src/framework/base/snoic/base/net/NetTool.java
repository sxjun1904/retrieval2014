/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import framework.base.snoic.base.util.file.FileHelper;
/**
 * NetTool
 * @author 
 *
 */
public class NetTool {
	private static FileHelper snoicsFileHelper=new FileHelper();
	private static int defaultConnectionTimeOut=10000;
	/**
	 * 把一个网络文件保存成本地文本文件
	 * 
	 * @param urlStr
	 *            网络文件路径
	 * @param filename
	 *            当前保存的完整路径
	 * @return boolean  是否操作成功,返回true,操作成功
	 */
	public static boolean getUrlToFile(String urlStr, String filename) {
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		BufferedReader bufferedReader = getUrlToBufferReader(urlStr);
		if(bufferedReader==null){
			return false;
		}else{
			snoicsFileHelper.bufferedReaderToFile(bufferedReader,filename);
			return true;
		}
	}
	
	/**
	 * 把一个网络文件保存成本地文本文件
	 * 
	 * @param urlStr
	 *            网络文件路径
	 * @param filename
	 *            当前保存的完整路径
	 * @param connectionTimeOut
	 * @return boolean  是否操作成功,返回true,操作成功
	 */
	public static boolean getUrlToFile(String urlStr, String filename,int connectionTimeOut) {
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		BufferedReader bufferedReader = getUrlToBufferReader(urlStr,connectionTimeOut);
		if(bufferedReader==null){
			return false;
		}else{
			snoicsFileHelper.bufferedReaderToFile(bufferedReader,filename);
			return true;
		}
	}
	
	/**
	 * 把一个网络文件保存成本地文本文件
	 * 
	 * @param urlStr
	 *            网络文件路径
	 * @param filename
	 *            当前保存的完整路径
	 * @param charsetName
	 * @return boolean  是否操作成功,返回true,操作成功
	 */
	public static boolean getUrlToFile(String urlStr, String filename,String charsetName) {
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		BufferedReader bufferedReader = getUrlToBufferReader(urlStr,charsetName);
		if(bufferedReader==null){
			return false;
		}else{
			snoicsFileHelper.bufferedReaderToFile(bufferedReader,filename);
			return true;
		}
	}
	
	/**
	 * 把一个网络文件保存成本地文本文件
	 * 
	 * @param urlStr
	 *            网络文件路径
	 * @param filename
	 *            当前保存的完整路径
	 * @param charsetName
	 * @param connectionTimeOut
	 * @return boolean  是否操作成功,返回true,操作成功
	 */
	public static boolean getUrlToFile(String urlStr, String filename,String charsetName,int connectionTimeOut) {
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		BufferedReader bufferedReader = getUrlToBufferReader(urlStr,charsetName,connectionTimeOut);
		if(bufferedReader==null){
			return false;
		}else{
			snoicsFileHelper.bufferedReaderToFile(bufferedReader,filename);
			return true;
		}
	}
	
	/**
	 * 把URL读入BufferedReader
	 * @param urlStr
	 * @return BufferedReader
	 */
	public static BufferedReader getUrlToBufferReader(String urlStr){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(defaultConnectionTimeOut);
			BufferedReader bufferedReader = null;
			if(conn.getContentEncoding()!=null){
				bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),conn.getContentEncoding()));
			}else{
				bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			return bufferedReader;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把URL读入BufferedReader
	 * @param urlStr
	 * @param connectionTimeOut
	 * @return BufferedReader
	 */
	public static BufferedReader getUrlToBufferReader(String urlStr,int connectionTimeOut){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeOut);
			BufferedReader bufferedReader = null;
			if(conn.getContentEncoding()!=null){
				bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),conn.getContentEncoding()));
			}else{
				bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			return bufferedReader;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把URL读入BufferedReader
	 * @param urlStr
	 * @param charsetName
	 * @param connectionTimeOut
	 * @return BufferedReader
	 */
	public static BufferedReader getUrlToBufferReader(String urlStr,String charsetName,int connectionTimeOut){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeOut);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),charsetName));
			return bufferedReader;
		} catch (MalformedURLException e) {
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 把URL读入BufferedReader
	 * @param urlStr
	 * @param charsetName
	 * @return BufferedReader
	 */
	public static BufferedReader getUrlToBufferReader(String urlStr,String charsetName){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(defaultConnectionTimeOut);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),charsetName));
			return bufferedReader;
		} catch (MalformedURLException e) {
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 把URL读入DataInputStream
	 * @param urlStr
	 * @return DataInputStream
	 */
	public static DataInputStream getUrlToDataInputStream(String urlStr){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(defaultConnectionTimeOut);
			DataInputStream dataInputStream = new DataInputStream(conn.getInputStream());
			return dataInputStream;
		} catch (MalformedURLException e) {
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 把URL读入DataInputStream
	 * @param urlStr
	 * @param connectionTimeOut
	 * @return DataInputStream
	 */
	public static DataInputStream getUrlToDataInputStream(String urlStr,int connectionTimeOut){
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeOut);
			DataInputStream dataInputStream = new DataInputStream(conn.getInputStream());
			return dataInputStream;
		} catch (MalformedURLException e) {
			//System.out.println("输入的网址错误!" + urlStr + "\r\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			//System.out.println("I/O出错!" + e.getMessage());
			return null;
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 把一个网络文件保存成本地二进制文件
	 * @param urlStr
	 * @param filename
	 * @return getUrlToFileAsDataInputStream
	 */
	public static boolean getUrlToFileAsDataInputStream(String urlStr, String filename){
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		DataInputStream dataInputStream = getUrlToDataInputStream(urlStr);
		if(dataInputStream==null){
			return false;
		}else{
			snoicsFileHelper.dataInputStreamToFile(dataInputStream,filename);
			return true;
		}
	}
	
	/**
	 * 把一个网络文件保存成本地二进制文件
	 * @param urlStr
	 * @param filename
	 * @param connectionTimeOut
	 * @return getUrlToFileAsDataInputStream
	 */
	public static boolean getUrlToFileAsDataInputStream(String urlStr, String filename,int connectionTimeOut){
		if(!snoicsFileHelper.isFile(filename)){
			snoicsFileHelper.createFile(filename);
		}
		DataInputStream dataInputStream = getUrlToDataInputStream(urlStr,connectionTimeOut);
		if(dataInputStream==null){
			return false;
		}else{
			snoicsFileHelper.dataInputStreamToFile(dataInputStream,filename);
			return true;
		}
	}
	
	/**
	 * 测试是否为有效的URL
	 * @param urlString
	 * @return boolean
	 */
	public static boolean isValidUrl(String urlString){
		boolean flag=false;
		
		try{
			URL url = new URL(urlString);
			url.openStream();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}
		
		return flag;
	}
	
	/**
	 * 连接上URL
	 * @param urlStr
	 * @param connectionTimeOut
	 */
	public static void openUrl(String urlStr,int connectionTimeOut) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeOut);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 连接上URL
	 * @param urlStr
	 */
	public static void openUrl(String urlStr) {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(defaultConnectionTimeOut);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//NetTool.getUrlToFileAsDataInputStream("http://192.168.0.1/survey/piechartN2.class","c:/piechartN2.class");
		boolean flag=NetTool.getUrlToFileAsDataInputStream("http://192.168.0.1/admin/upload_file/img/2006-05/1148031337011.jpg","c:/1.jpg");
		System.out.println("flag="+flag);
	}
}
