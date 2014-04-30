package frame.base.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import frame.retrieval.engine.common.RetrievalUtil;


public class RSAUtil {
	
	private static Log log=RetrievalUtil.getLog(RSAUtil.class);
	private static final String lic = "retrieval.lic";
	private static final String key = "private.key";
	
	public static void main(String[] args) {
		System.out.println(PathUtil.getPath());
    	String deadline = "2014-5-27 19:28:00";
    	String pathlic = PathUtil.getPath()+lic;
    	String pathkey = PathUtil.getPath()+key;
    	String productname = "全文检索";
		generateLicence(productname,deadline,pathlic,pathkey);
        checkLicence(pathlic,pathkey);
    } 
	
	
	public static void generateLicence(String productname,String deadline){
		String pathlic = PathUtil.getPath()+lic;
    	String pathkey = PathUtil.getPath()+key;
    	generateLicence(productname,deadline,pathlic,pathkey);
	}
	
	public static boolean checkLicence() {
		String pathlic = PathUtil.getPath()+lic;
    	String pathkey = PathUtil.getPath()+key;
		return checkLicence(pathlic,pathkey);
	}
	
	public static void generateLicence(String productname,String deadline,String pathlic,String pathkey){
		String[] strs = gen_RSA(1024,pathkey);
		String e = strs[0]; 
        String n = strs[1]; 
        String d = strs[2];
        
        //不过期时间
        long _deadline=  new DateTime().getLongTime(deadline);
        
    	String mingwen="{productname:'"+productname+"',deadline:'"+_deadline+"'}";
        String miwen = Enc_RSA(mingwen, e, n);
        String str2 = Dec_RSA(miwen, d, n); 
        System.out.println("解密后明文\n"+str2);
        
        ObjectOutputStream oos = null;
        try {
			//保存licence
			oos = new ObjectOutputStream(new FileOutputStream(new File(pathlic))); 
            oos.writeObject(miwen); 
            oos.flush(); 
			oos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Map<String,Object> getLicenceInfo(){
		String pathlic = PathUtil.getPath()+lic;
    	String pathkey = PathUtil.getPath()+key;
    	return getLicenceInfo(pathlic,pathkey);
	}
	
	public static Map<String,Object> getLicenceInfo(String pathlic,String pathkey){
		//licFile存放密文
        String licFile = pathlic;
        //秘钥，用于解密密文
        String privateKeyFile = pathkey;
        
        //得到解密的内容
        String licence = decrypt(licFile, privateKeyFile);
        if (licence != null) { 
        	Map<String,Object> map = new HashMap<String,Object>();
            JSONObject json = JSONObject.fromObject(licence); 
            String productname = json.getString("productname"); 
            //转换成时间
            long deadline = Long.parseLong(json.getString("deadline")); 
            map.put("productName", productname);
            map.put("deadline", deadline);
            return map;
        } else { 
        	return null;
        } 
	}

    /**
     * 检查licence是否到期
     */
    public static boolean checkLicence(String pathlic,String pathkey) {
    	//licFile存放密文
        String licFile = pathlic;
        //秘钥，用于解密密文
        String privateKeyFile = pathkey;
    	
        //得到解密的内容
        String licence = decrypt(licFile, privateKeyFile);
        if (licence != null) { 
            JSONObject json = JSONObject.fromObject(licence); 
            String productname = json.getString("productname"); 
            //转换成时间
            long deadline = Long.parseLong(json.getString("deadline")); 
            log.debug(productname+deadline);
            String companyName = ""; 
            if ((!companyName.equals(productname)) && (deadline < System.currentTimeMillis())) { 
            	log.debug("系统未授权或已过期，请联系作者进行注册，谢谢！"); 
                return false;
            }else{
            	log.debug("系统授权，谢谢！");
            	return true;
            } 
        } else { 
        	log.debug("系统未授权或已过期，请联系作者进行注册，谢谢！");
        	return false;
        } 
    }
    /**
     * 解密文件 得到json格式的内容
     * @param licFile 密文
     * @param privateKeyFile 秘钥
     * @return 返回内容格式如下 { name:'',deadline:'1371362586906'}
     */
    public static String decrypt(String licFile, String privateKeyFile) { 
        try { 
        	ObjectInputStream b1 = new ObjectInputStream(new FileInputStream(licFile));
        	String miwen=(String) b1.readObject();
            BigInteger c = new BigInteger(miwen);
            
            FileInputStream f = new FileInputStream(privateKeyFile); 
            ObjectInputStream b = new ObjectInputStream(f); 
            RSAPrivateKey privateKey = (RSAPrivateKey) b.readObject(); 
            BigInteger d = privateKey.getPrivateExponent(); 
            BigInteger n = privateKey.getModulus(); 
            
            //解密
            BigInteger m = c.modPow(d, n);             
            byte[] mt = m.toByteArray(); 
            return new String(mt, "utf-8"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return null; 
    } 

    /**
     * 生成并秘钥对
     * @param keylen	输入密钥长度 
     * @return	返回公钥和私钥
     */
    public static Object[] get_RSA(int keylen) {
    	Object[] output = new Object[2];
        try { 
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); 
            kpg.initialize(keylen); // 指定密钥的长度，初始化密钥对生成器 
            KeyPair kp = kpg.generateKeyPair(); // 生成密钥对 
            //公钥
            RSAPublicKey puk = (RSAPublicKey) kp.getPublic();
            //私钥
            RSAPrivateCrtKey prk = (RSAPrivateCrtKeyImpl) kp.getPrivate();
            output[0] = puk; 
            output[1] = prk; 
        } catch (Exception ex) { 
        } 
        return output;
    }    
    
    
    /**
     * 生成秘钥对，返回能用于加解密的字符串数组，秘钥对不会被保存
     * @param keylen	输入密钥长度
     * @return	用于加解密的字符串数组
     */
    public static String[] gen_RSA(int keylen,String pathkey) {
        String[] output = new String[5]; // 用来存储密钥的  e n d p q
        try { 
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); 
            kpg.initialize(keylen); // 指定密钥的长度，初始化密钥对生成器 
            KeyPair kp = kpg.generateKeyPair(); // 生成密钥对 
            //公钥
            RSAPublicKey puk = (RSAPublicKey) kp.getPublic();
            //私钥
            RSAPrivateCrtKey prk = (RSAPrivateCrtKeyImpl) kp.getPrivate();
            
            ObjectOutputStream oos = null;
            try {
    			//保存私钥
    			oos = new ObjectOutputStream(new FileOutputStream(new File(pathkey))); 
                oos.writeObject(prk); 
                oos.flush(); 
    			oos.close();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
            
            
			BigInteger e = puk.getPublicExponent(); 
            BigInteger n = puk.getModulus(); 
            BigInteger d = prk.getPrivateExponent(); 
            BigInteger p = prk.getPrimeP(); 
            BigInteger q = prk.getPrimeQ();			
            output[0] = e.toString(); 
            output[1] = n.toString(); 
            output[2] = d.toString(); 
            output[3] = p.toString(); 
            output[4] = q.toString(); 
        } catch (Exception ex) { 
        } 
        return output; 
    }

    /**
     * 加密，在RSA公钥中包含有两个整数信息：e和n。对于明文数字m,计算密文的公式是m的e次方再与n求模。
     * @param mingwen  明文
     * @param eStr	e
     * @param nStr	n
     * @return 返回密文
     */
    public static String Enc_RSA(String mingwen, String eStr, String nStr) { 
        String miwen = new String(); 
        try { 
            BigInteger e = new BigInteger(eStr); 
            BigInteger n = new BigInteger(nStr); 
            byte[] ptext = mingwen.getBytes("utf-8"); 
            BigInteger m = new BigInteger(ptext); 
            BigInteger c = m.modPow(e, n); 
            miwen = c.toString(); 
        } catch (Exception ex) { 
        } 
        return miwen; 
    } 

    /**
     * 解密
     * @param miwen
     * @param dStr	d
     * @param nStr	n
     * @return
     */
    public static String Dec_RSA(String miwen, String dStr, String nStr) { 
        String mingwen = null; 
        try { 
            BigInteger d = new BigInteger(dStr);// 获取私钥的参数d,n 
            BigInteger n = new BigInteger(nStr); 
            BigInteger c = new BigInteger(miwen); 
            BigInteger m = c.modPow(d, n);// 解密明文 
            byte[] mt = m.toByteArray();// 计算明文对应的字符串并输出 
            mingwen= new String(mt, "utf-8"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return mingwen; 
    } 
}