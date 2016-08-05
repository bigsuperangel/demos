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
		(��)���ǶԳƼ��� 
		��ԳƼ����㷨��ͬ���ǶԳƼ����㷨��Ҫ������Կ��������Կ��publickey����˽����Կ ��������Կ��˽����Կ��һ�ԣ�����ù�����Կ������
		���м��ܣ�ֻ���ö�Ӧ��˽����Կ���ܽ��ܣ������˽����Կ�����ݽ��м��ܣ���ôֻ���ö�Ӧ�Ĺ�����Կ���ܽ��ܡ���Ϊ���ܺͽ���ʹ�õ���������ͬ��
		��Կ�����������㷨�����ǶԳƼ����㷨�� 
		���õķǶԳƼ����㷨
		
		RSA ��Կ�����㷨
		DSA�㷨
		����RSA�㷨��demo����
 */
public class RSAdemo {  
      
    /** 
     * ���� 
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
            //Cipher������ɼ��ܻ���ܹ���������RSA  
            Cipher cipher = Cipher.getInstance("RSA");  
            //���ݹ�Կ����Cipher������г�ʼ��  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] resultBytes = cipher.doFinal(srcBytes);  
            return resultBytes;  
        }  
        return null;  
    }  
      
    /** 
     * ����  
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
            //Cipher������ɼ��ܻ���ܹ���������RSA  
            Cipher cipher = Cipher.getInstance("RSA");  
            //���ݹ�Կ����Cipher������г�ʼ��  
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
        //KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        //��ʼ����Կ������������Կ��СΪ1024λ  
        keyPairGen.initialize(1024);  
        //����һ����Կ�ԣ�������keyPair��  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        //�õ�˽Կ  
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();   
        System.out.println("11111"+new Gson().toJson(privateKey));
        //�õ���Կ  
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();  
        System.out.println("22222"+new Gson().toJson(publicKey));
          
        //�ù�Կ����  
        byte[] srcBytes = msg.getBytes();  
        byte[] resultBytes = rsa.encrypt(publicKey, srcBytes);  
          
        //��˽Կ����  
        byte[] decBytes = rsa.decrypt(privateKey, resultBytes);  
          
        System.out.println("������:" + msg);  
        System.out.println("���ܺ���:" + new String(resultBytes));  
        System.out.println("���ܺ���:" + new String(decBytes));  
        
    }  
  
}  
