package frame.base.core.log;

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
