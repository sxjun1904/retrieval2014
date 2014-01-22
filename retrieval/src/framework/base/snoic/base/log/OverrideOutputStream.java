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
package framework.base.snoic.base.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 * 继承OutputStream
 * @author 
 *
 */
public class OverrideOutputStream extends OutputStream {
	
	private FileOutputStream fileOutputStream=null;
	private FileOutputStream fdOut = null;
	private FileOutputStream fdErr = null;
	/**
	 * @return Returns the fileOutputStream.
	 */
	public FileOutputStream getFileOutputStream() {
		return fileOutputStream;
	}

	/**
	 * @return Returns the fdErr.
	 */
	public FileOutputStream getFdErr() {
		return fdErr;
	}

	/**
	 * @param fdErr The fdErr to set.
	 */
	public void setFdErr(FileOutputStream fdErr) {
		this.fdErr = fdErr;
	}

	/**
	 * @return Returns the fdOut.
	 */
	public FileOutputStream getFdOut() {
		return fdOut;
	}

	/**
	 * @param fdOut The fdOut to set.
	 */
	public void setFdOut(FileOutputStream fdOut) {
		this.fdOut = fdOut;
	}

	/**
	 * @param fileOutputStream The fileOutputStream to set.
	 */
	public void setFileOutputStream(FileOutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}
	
	public void write(int b) throws IOException {
		if(fileOutputStream!=null) {
			fileOutputStream.write(b);
		}
		if(fdOut!=null) {
			fdOut.write(b);
		}
		if(fdErr!=null) {
			fdErr.write(b);
		}
	}
	
    public void write(byte b[]) throws IOException {
    	//super.write(b);
		if(fileOutputStream!=null) {
			fileOutputStream.write(b);
		}
		if(fdOut!=null) {
			fdOut.write(b);
		}
		if(fdErr!=null) {
			fdErr.write(b);
		}
    }

    public void write(byte b[], int off, int len) throws IOException {
    	//super.write(b,off,len);
		if(fileOutputStream!=null) {
			fileOutputStream.write(b,off,len);
		}
		if(fdOut!=null) {
			fdOut.write(b,off,len);
		}
		if(fdErr!=null) {
			fdErr.write(b,off,len);
		}
     }
    
    public void flush() throws IOException {
		if(fileOutputStream!=null) {
			fileOutputStream.flush();
		}
		if(fdOut!=null) {
			fdOut.flush();
		}
		if(fdErr!=null) {
			fdErr.flush();
		}
    }
    
    public void close() throws IOException {
		if(fileOutputStream!=null) {
			fileOutputStream.close();
		}
		if(fdOut!=null) {
			fdOut.close();
		}
		if(fdErr!=null) {
			fdErr.close();
		}
    }
}
