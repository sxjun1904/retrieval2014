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
package framework.base.snoic.base.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author: 
 * 
 * History: 2006-7-12  Created.
 */

public class JarUtils {
	
	/**
	 * 从Jar包获取InputStream
	 * @param fileName
	 * @param theClass
	 * @return InputStream
	 */
	public InputStream getInputStreamFromJar(final String fileName,Class theClass){
		InputStream inputStream = theClass.getResourceAsStream(fileName);
		return inputStream;
	}
	
	/**
	 * 从Jar包获取Image
	 * @param imageFileName
	 * @param theClass
	 * @return Image
	 */
	public Image getImageFromJar(String imageFileName, Class theClass) {
		Image image = null;
		InputStream inputStream = getInputStreamFromJar(imageFileName,theClass);
		ByteArrayOutputStream byteArrayOutputStream = null;
		if (inputStream != null) {
			byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				final byte[] bytes = new byte[1024];
				int read = 0;
				while ((read = inputStream.read(bytes)) >= 0) {
					byteArrayOutputStream.write(bytes, 0, read);
				}
				image = Toolkit.getDefaultToolkit().createImage(byteArrayOutputStream.toByteArray());
			} catch (IOException exception) {
				exception.printStackTrace();
			}finally{
				if(inputStream!=null){
					try{
						inputStream.close();
						inputStream=null;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(byteArrayOutputStream!=null){
					try{
						byteArrayOutputStream.close();
						byteArrayOutputStream=null;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

	/**
	 * 从jar包获取文本信息
	 * @param filename
	 * @param theClass
	 * @return String
	 */
	public String getTextFromJar(String filename, Class theClass) {
		String text = "";
	    
		InputStream inputStream = getInputStreamFromJar(filename,theClass);
		BufferedReader bufferedReader = null;
		if (inputStream != null) {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String s;
				while ((s = bufferedReader.readLine()) != null) {
					text += s + "\n";
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}finally{
				if(bufferedReader!=null){
					try{
						bufferedReader.close();
						bufferedReader=null;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(inputStream!=null){
					try{
						inputStream.close();
						inputStream=null;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		return text;
	}
}
