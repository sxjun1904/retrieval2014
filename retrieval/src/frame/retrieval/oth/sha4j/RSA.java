package frame.retrieval.oth.sha4j;

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

import net.sf.json.JSONObject;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import frame.base.core.util.ByteUtil;
import frame.base.core.util.DateTime;


public class RSA {
	
	public static void main(String[] args) {
    	
		//generateLicence();
        checkLicence();
    } 
	
	public static void generateLicence(){
		String[] strs = gen_RSA(1024);
		String e = strs[0]; 
        //System.out.println("e\n"+e); 
        String n = strs[1]; 
        //System.out.println("n\n"+n); 
        String d = strs[2];
        //System.out.println("d\n"+d); 
        
        //不过期时间
        //long deadline=System.currentTimeMillis()+20000;
        long deadline=  new DateTime().getLongTime("2014-4-26 13:43:00");
        //过期时间
        //long deadline=System.currentTimeMillis();
        
    	String mingwen="{name:'软件名称',deadline:'"+deadline+"'}";
        //System.out.println("加密前明文\n"+mingwen);
        String miwen = Enc_RSA(mingwen, e, n);
        //System.out.println("加密后密文\n"+miwen); 
        String str2 = Dec_RSA(miwen, d, n); 
        System.out.println("解密后明文\n"+str2);
        
        ObjectOutputStream oos = null;
        try {
			//保存licence
			oos = new ObjectOutputStream(new FileOutputStream(new File("D:/mobile.lic"))); 
            oos.writeObject(miwen); 
            oos.flush(); 
			oos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

    /**
     * 检查licence是否到期
     */
    public static void checkLicence() {
    	//licFile存放密文
        String licFile = "D:/mobile.lic";
        //秘钥，用于解密密文
        String privateKeyFile = "D:/private.key";
    	
        //得到解密的内容
        String licence = decrypt(licFile, privateKeyFile);
        if (licence != null) { 
            JSONObject json = JSONObject.fromObject(licence); 
            String name = json.getString("name"); 
            //转换成时间
            long deadline = Long.parseLong(json.getString("deadline")); 
            System.out.println(name+deadline);
            String companyName = ""; 
            if ((!companyName.equals(name)) && (deadline < System.currentTimeMillis())) { 
                System.out.println("系统未授权或已过期，请联系作者进行注册，谢谢！"); 
            }else{
            	System.out.println("系统授权，谢谢！");
            } 
        } else { 
        	System.out.println("系统未授权或已过期，请联系作者进行注册，谢谢！"); 
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
    public static String[] gen_RSA(int keylen) {
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
    			oos = new ObjectOutputStream(new FileOutputStream(new File("D:/private.key"))); 
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