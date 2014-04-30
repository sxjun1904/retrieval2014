package frame.base.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * @author 
 *
 */
public class ByteUtil {
	private ByteUtil() {

	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
	
	public static byte[] hex2byte(String hs) {
		if (hs == null || hs.equals("")) {
			return null;
		}
		hs = hs.toUpperCase();
		int length = hs.length() / 2;
		char[] hexChars = hs.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String byte2hex2(byte[] bin) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bin.length; ++i) {
			int x = bin[i] & 0xFF, h = x >>> 4, l = x & 0x0F;
			buf.append((char) (h + ((h < 10) ? '0' : 'a' - 10)));
			buf.append((char) (l + ((l < 10) ? '0' : 'a' - 10)));
		}
		return buf.toString();
	}
	
}
