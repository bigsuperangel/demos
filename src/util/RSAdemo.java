package util;

import java.security.InvalidKeyException;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  

import com.google.gson.Gson;
  

/**
 * 
		(二)、非对称加密 
		与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥（publickey）和私有密钥 。公开密钥与私有密钥是一对，如果用公开密钥对数据
		进行加密，只有用对应的私有密钥才能解密；如果用私有密钥对数据进行加密，那么只有用对应的公开密钥才能解密。因为加密和解密使用的是两个不同的
		密钥，所以这种算法叫作非对称加密算法。 
		常用的非对称加密算法
		
		RSA 公钥加密算法
		DSA算法
		关于RSA算法的demo如下
 */
public class RSAdemo {  
      
    /** 
     * 加密 
     * @param publicKey 
     * @param srcBytes 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    protected byte[] encrypt(RSAPublicKey publicKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{  
        if(publicKey!=null){  
            //Cipher负责完成加密或解密工作，基于RSA  
            Cipher cipher = Cipher.getInstance("RSA");  
            //根据公钥，对Cipher对象进行初始化  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] resultBytes = cipher.doFinal(srcBytes);  
            return resultBytes;  
        }  
        return null;  
    }  
      
    /** 
     * 解密  
     * @param privateKey 
     * @param srcBytes 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    protected byte[] decrypt(RSAPrivateKey privateKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{  
        if(privateKey!=null){  
            //Cipher负责完成加密或解密工作，基于RSA  
            Cipher cipher = Cipher.getInstance("RSA");  
            //根据公钥，对Cipher对象进行初始化  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] resultBytes = cipher.doFinal(srcBytes);  
            return resultBytes;  
        }  
        return null;  
    }  
    
    public String de(RSAPrivateKey privateKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{ 
    	return decrypt(privateKey,srcBytes).toString();
    }
  
    /** 
     * @param args 
     * @throws NoSuchAlgorithmException  
     * @throws BadPaddingException  
     * @throws IllegalBlockSizeException  
     * @throws NoSuchPaddingException  
     * @throws InvalidKeyException  
     */  
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {  
    	RSAdemo rsa = new RSAdemo();  
        String msg = "www.suning.com/index.jsp";  
        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        //初始化密钥对生成器，密钥大小为1024位  
        keyPairGen.initialize(1024);  
        //生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        //得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();   
        System.out.println("11111"+new Gson().toJson(privateKey));
        //得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();  
        System.out.println("22222"+new Gson().toJson(publicKey));
          
        //用公钥加密  
        byte[] srcBytes = msg.getBytes();  
        byte[] resultBytes = rsa.encrypt(publicKey, srcBytes);  
          
        //用私钥解密  
        byte[] decBytes = rsa.decrypt(privateKey, resultBytes);  
          
        System.out.println("明文是:" + msg);  
        System.out.println("加密后是:" + new String(resultBytes));  
        System.out.println("解密后是:" + new String(decBytes));  
        
    }  
  
}  
