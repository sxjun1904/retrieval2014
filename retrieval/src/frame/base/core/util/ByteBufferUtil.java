package frame.base.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 
 *  @author:    
 */

public class ByteBufferUtil {
	
	/**
	 * 对象转成ByteBuffer
	 * @param object
	 * @return ByteBuffer
	 * @throws Exception
	 */
	public static ByteBuffer objectToByteBuffer(Object object) throws Exception{
		ByteBuffer byteBuf = null;
		byteBuf = ByteBuffer.wrap(serializer(object));
		byteBuf.rewind();
		return byteBuf;
	}
	
	/**
	 * 将ByteBuffer转成对象
	 * @param byteBuffer
	 * @return Object
	 * @throws Exception
	 */
	public static Object byteBufferToObject(ByteBuffer byteBuffer) throws Exception{
		byte [] bytes = new byte[byteBuffer.limit()];
		byteBuffer.get(bytes);
		Object object=deSerializer(bytes);
		return object;
	}
	
	/**
	 * 将Object转换成byte数组
	 * @param object 需要转换的Object
	 * @return 转换后的byte数组
	 * @throws IOException
	 */
	public static byte[] serializer(Object object) throws IOException {
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream;

		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

			objectOutputStream.writeObject(object);
			objectOutputStream.flush();

			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			if (objectOutputStream != null) {
				objectOutputStream.close();
			}
		}
	}

	/**
	 * 将byte数组转换成Object
	 * @param bytes 需要转换的byte数组
	 * @return 转换后的Object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerializer(byte[] bytes) throws IOException,ClassNotFoundException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		return objectInputStream.readObject();
	}
}

