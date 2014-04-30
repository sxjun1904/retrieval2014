package frame.redis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * 类HessianUtil.java的实现描述：hessian操作对象的序列化和反序列化的工具类。
 * 
 * @author liulin 2012-11-29 上午10:55:02
 */
public class HessianUtil {

    private static final Logger logger = Logger.getLogger(HessianUtil.class);

    /**
     * 序列对象到文件.
     * 
     * @param obj 要序列化的对象
     * @param fileName 要序列化的文件名.
     * @throws IOException
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        if (obj == null) throw new NullPointerException();
        File file = new File(fileName);

        // 如果父目录不存在创建目录.
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = new FileOutputStream(file);
        HessianOutput ho = new HessianOutput(fos);
        ho.writeObject(obj);
        ho.flush();
        ho.close();
        fos.close();
    }

    /**
     * 将对象序列化成字节数组.
     * 
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Serializable obj) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    /**
     * 将Map中的Value对象，序列化，key保持不变。
     * 
     * @param map
     * @return
     * @throws IOException
     */
    public static Map<byte[], byte[]> serialize(Map<String, Serializable> map) throws IOException {
        if (map == null) {
            throw new NullPointerException();
        }

        Map<byte[], byte[]> nMap = new HashMap<byte[], byte[]>();
        for (Entry<String, Serializable> entry : map.entrySet()) {
            nMap.put(entry.getKey().getBytes(), serialize(entry.getValue()));
        }
        return nMap;
    }
    
    /**
     * 将Map中的Value对象，序列化，key保持不变。
     * 
     * @param map
     * @return
     * @throws IOException
     */
    public static Map<Double, byte[]> serializeZ(Map<String, Serializable> map) throws IOException {
        if (map == null) {
            throw new NullPointerException();
        }

        Map<Double, byte[]> nMap = new HashMap<Double, byte[]>();
        for (Entry<String, Serializable> entry : map.entrySet()) {
            nMap.put(Double.parseDouble(entry.getKey()), serialize(entry.getValue()));
        }
        return nMap;
    }

    /**
     * 根据字节流，返序列化.
     * 
     * @param by 要返序列化的字节数组
     * @return object 返序化的对象
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T deserialize(byte[] by) throws IOException {
        if (by == null) {
            throw new NullPointerException();
        }
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        Object obj = hi.readObject();
        hi.close();
        is.close();
        return (T) obj;
    }
    
    /**
     * 返序列化列表中的bean.
     * 
     * @param rlist
     * @return
     * @throws IOException
     */
    public static <T extends Object> List<T> deserialize(List<byte[]> rlist) throws IOException {
        if (rlist == null) {
            throw new NullPointerException();
        }

        List<T> list = new ArrayList<T>();
        for (byte[] bs : rlist) {
            if (bs == null) {
                list.add(null);
                continue;
            }
            @SuppressWarnings("unchecked")
            T obj = (T) deserialize(bs);
            list.add(obj);
        }

        return list;

    }

    /**
     * 返序列化集合中的bean.
     * 
     * @param rlist
     * @return
     * @throws IOException
     */
    public static <T extends Object> List<T> deserializeZ(Set<byte[]> rSet) throws IOException {
        if (rSet == null) {
            throw new NullPointerException();
        }

        List<T> list = new ArrayList<T>();
        for (byte[] bs : rSet) {
            if (bs == null) {
                list.add(null);
                continue;
            }
            @SuppressWarnings("unchecked")
            T obj = (T) deserialize(bs);
            list.add(obj);
        }
        return list;
    }

    /**
     * 根据字节流，返序列化Map
     * 
     * @param map
     * @return
     * @throws IOException
     */
    public static Map<String, Serializable> deserialize(Map<byte[], byte[]> map) throws IOException {
        if (map == null) {
            throw new NullPointerException();
        }

        Map<String, Serializable> dMap = new HashMap<String, Serializable>();

        for (Entry<byte[], byte[]> entry : map.entrySet()) {
            Serializable obj = deserialize(entry.getValue());
            dMap.put(new String(entry.getKey()), obj);
        }

        return dMap;

    }

    /**
     * 根据文件名，返序列化.
     * 
     * @param fileName 要返序列化的文件名.
     * @return object 返序化的对象
     * @throws IOException
     */
    public static Object deserialize(String fileName) throws IOException {
        if (fileName == null) throw new NullPointerException();
        FileInputStream fis = new FileInputStream(new File(fileName));
        HessianInput hi = new HessianInput(fis);
        Object obj = hi.readObject();
        hi.close();
        fis.close();
        return obj;
    }

    /**
     * 递归创建path指定的所有层级的目录。
     * 
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);

        // 寻找父目录是否存在
        File parent = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));

        // 如果父目录不存在，则递归寻找更上一层目录
        if (!parent.exists()) {
            createFile(parent.getPath());

            // 创建父目录
            parent.mkdirs();
        }
        return file;
    }

    /**
     * 获取本地的对象序列化的缓存文件,此对象支持模糊删除,以文件名的前缀做匹配删除.
     * 
     * @param objectFileDirectory 要删除文件的目录。
     * @param fileName 要删除文件的文件名，可以输入文件的前缀名称。
     * @return boolean 是否删除成功, true:成功,false:失败.
     */
    public static boolean removeSerializeLikeFile(String objectFileDirectory, final String fileName) {
        if (StringUtils.isBlank(objectFileDirectory) || StringUtils.isBlank(fileName)) {
            return false;
        }

        // 获取目录的File对象.
        File objDirFile = getObjectFileDir(objectFileDirectory);

        // 获取文件夹下指定以对象文件名为前缀的所有的对象。
        File[] objFiles = objDirFile.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                if (StringUtils.isBlank(name)) {
                    return false;
                }

                /**
                 * 过滤文件名.
                 */
                if (StringUtils.startsWith(name, fileName)) {
                    return true;
                }
                return false;
            }
        });

        if (ArrayUtils.isEmpty(objFiles)) {
            return false;
        }

        // 循环删除本地已经存在的对象文件.
        for (File file : objFiles) {
            boolean isDelSuc = file.delete();
            if (!isDelSuc) {
                file.deleteOnExit();
                logger.warn("removeSerializeLikeFile(),Delete the local cache file failed.[File:" + file + "]");
                continue;
            }
        }

        return true;
    }

    /**
     * 字符串数据转换为字节数组.
     * 
     * @param strArray
     * @return
     */
    public static final byte[][] strArrayToByteArray(String[] strArray) {
        if (ArrayUtils.isEmpty(strArray)) {
            throw new NullPointerException();
        }
        byte[][] bytes = new byte[strArray.length][];
        int i = 0;
        for (String str : strArray) {
            bytes[i] = str.getBytes();
            i++;
        }
        return bytes;
    }

    /**
     * 根据map,转换成字节数组，结构是：K,V = [k,v,k,v]
     * 
     * @param map
     * @return
     * @throws IOException
     */
    public static final byte[][] mapToByteArray(Map<String, Serializable> map) throws IOException {
        if (map == null || map.isEmpty()) {
            throw new NullPointerException("mapToByteArray is null.");
        }

        byte[][] bytes = new byte[map.size() * 2][];

        int i = 0;
        for (Map.Entry<String, Serializable> entry : map.entrySet()) {
            bytes[i] = entry.getKey().getBytes();
            bytes[i + 1] = serialize(entry.getValue());
            i = i + 2;
        }

        return bytes;

    }

    /**
     * 验证有效性.
     * 
     * @param objectFileDirectory 文件夹目录.
     * @return file 文件对象.
     */
    private static final File getObjectFileDir(String objectFileDirectory) {
        File objDirFile = new File(objectFileDirectory);
        if (objDirFile == null || !objDirFile.exists()) {
            return null;
        }
        return objDirFile;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("a", "c");
        map.put("b", "c");
        map.put("c", "c");
        map.put("d", "c");
        map.put("d２２", "c");

        byte[][] bytes = mapToByteArray(map);
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(Arrays.toString(bytes[i]));
        }
    }
}
