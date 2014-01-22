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
package framework.base.snoic.base.util.io;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
/**
 * InputStream操作
 * @author 
 *
 */
public class InputStreamUtil {
	//空InputStream
	private static InputStream EMPTY_ISTREAM = new ByteArrayInputStream(new byte[0]);
	
	private InputStreamUtil(){
		
	}
	
	/**
	 * 字符串转InputStream
	 * @param string
	 * @param charsetName
	 * @return InputStream
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream stringToInputStream(String string,String charsetName) throws UnsupportedEncodingException{
		InputStream  inputStream = null;
		inputStream = new  ByteArrayInputStream(string.getBytes(charsetName));
		return inputStream;
	}
	
	/**
	 * 从InputStream里面取得byte[]
	 * @param inputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream inputStream) throws IOException{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int b;
		while((b=inputStream.read())>=0){
			baos.write(b);
		}
		return baos.toByteArray();
	}
	
	/**
	 * 从InputStream里面取得固定长度的byte[]
	 * @param inputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream inputStream,int length) throws IOException{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int b;
		int i = 0;
		while(((b=inputStream.read())>=0)&&(i<length)){
			baos.write(b);
			i++;
		}
		return baos.toByteArray();
	}
	
	/**
	 * 把InputStream转换成encoding类型的字符串
	 * @param inputStream
	 * @param encoding
	 * @return String
	 */
    public static String getContentsAsString(InputStream inputStream, String encoding){
        String string="";
        try{
            if(encoding!=null) {
            	string=new String(getBytes(inputStream), encoding);
            }else {
            	string=new String(getBytes(inputStream));
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    	return string;
    }
    
	/**
	 * 把InputStream中的固定长度的部分转换成encoding类型的字符串
	 * @param inputStream
	 * @param encoding
	 * @return String
	 */
    public static String getContentsAsString(InputStream inputStream, int length,String encoding){
        String string="";
        try{
            if(encoding!=null) {
            	string=new String(getBytes(inputStream,length), encoding);
            }else {
            	string=new String(getBytes(inputStream,length));
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    	return string;
    }
    
    /**
     * 比较两个InputStream是否完全一样
     * @param inputStream1
     * @param inputStream2
     * @return boolean
     * @throws IOException
     */
    public static boolean compare(InputStream inputStream1, InputStream inputStream2) throws IOException {
		for (int b = 0; b >= 0;){
			if ((b = inputStream1.read()) != inputStream2.read()){
				return false;
			}
		}
		return true;
	}
    
    /**
	 * 关闭InputStream
	 * 
	 * @param inputStream
	 */
    public static void closeInputStream(InputStream inputStream){
    	try{
    		if(inputStream!=null){
    			inputStream.close();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 取得一个空的InputStream
     *
     */
    public static InputStream getEmptyInputStream(){
    	return EMPTY_ISTREAM;
    }
}
