package framework.retrieval.engine.index.all.database.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import framework.retrieval.engine.index.all.database.IBigDataOperator;

public class BigDataOperator implements IBigDataOperator {

	@Override
	public String getStringFromBlob(Blob blob) {
		String blobStr = "";
		InputStream inStream = null;
		try {
			inStream = blob.getBinaryStream();
			byte[] bytes = new byte[inStream.available()];
			inStream.read(bytes);
			blobStr = new String(bytes);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return blobStr;
	}

	@Override
	public String getStringFromClob(Clob clob) {
		String clobStr = "";
		Reader inStream=null;
		try {
			inStream = clob.getCharacterStream();
			char[] ch = new char[(int)clob.length()];
			inStream.read(ch);
			clobStr = new String(ch);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}finally{
		    if(inStream!=null){
		        try {
                    inStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
		    }
		}
		return clobStr;
	}
	
	@Override
	public String getStringFromBlob(byte[] bytes) {
		return new String(bytes);
	}

	@Override
	public String getStringFromClob(char[] ch) {
		return new String(ch);
	}

}
