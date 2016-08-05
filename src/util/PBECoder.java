package util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
	Java ���ܽ���֮�ԳƼ����㷨PBE
	PBE��һ�ֻ��ڿ���ļ����㷨��ʹ�ÿ�����������ԳƼ����㷨�е���Կ�����ص����ڿ������û��Լ��ƹܣ��������κ�����ý�壻������������������ǽ����Σ��Ӵն��ؼ��ܵȷ�����֤���ݵİ�ȫ�ԡ� 
	
	PBE�㷨�ǶԳƼ����㷨���ۺ��㷨�������㷨PBEWithMD5AndDES,ʹ��MD5��DES�㷨������PBE�㷨�����θ����ڿ����ϣ�ͨ����ϢժҪ�㷨����������ù�����Կ�Ļ������ϣ�������Կ��ʹ�öԳƼ����㷨���м��ܽ��ܡ�
	 
	JDK��DESede�㷨��֧��
	 
	�㷨/��Կ����/Ĭ����Կ���ȣ�
	1.PBEWithMD5AndDES/56/56
	2.PBEWithMD5AndTripleDES/112,168/168
	3.PBEWithSHA1AndDESede/112,168/168
	4.PBEWithSHA1AndRC2_40/40 to 1024/128
	����ģʽ��CBC
	��䷽ʽ��PKCS5Padding
 * 
 */
public class PBECoder {

	public static final String ALGORITHM = "PBEWITHMD5andDES";
	
	public static final int ITERATION_COUNT = 100;
	
	/**
	 * ��ʼ��<br/>
	 * �εĳ��ȱ���Ϊ8λ
	 * @return byte[] �� 
	 * @throws Exception
	 */
	public static byte[] initSalt() throws Exception{
		//ʵ������ȫ�����
		SecureRandom random = new SecureRandom();
		//������
		return random.generateSeed(8);
	}
	
	/**
	 * ����base64���ܺ����
	 * @return
	 * @throws Exception
	 */
	public static String initBase64Salt()throws Exception{
		return Base64.encode(initSalt());
	}
	
	/**
	 * ת����Կ
	 * 
	 * @param password	����
	 * @return Key ��Կ
	 */
	private static Key toKey(String password) throws Exception{
		//��Կ����
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		//ʵ����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		//������Կ
		return keyFactory.generateSecret(keySpec);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param salt  ��
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{
		//ת����Կ
		Key key = toKey(password);
		//ʵ����PBE��������
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		//ʵ����
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//��ʼ��
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param password	����
	 * @param base64Salt  �� base64����
	 * @return string	��������  base64
	 * @throws Exception
	 */
	public static String encrypt(String data,String password,String base64Salt) throws Exception{
		byte[] salt = Base64.decode(base64Salt);
		return Base64.encode(encrypt(data.getBytes(), password, salt));
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param salt  ��
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,String password,byte[] salt)throws Exception{
		//ת����Կ
		Key key = toKey(password);
		//ʵ����PBE��������
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		//ʵ����
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//��ʼ��
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	/**
	 * ����
	 * 
	 * @param base64Data	���������� base64
	 * @param password	����
	 * @param base64Salt  �� base64
	 * @return string	��������
	 * @throws Exception
	 */
	public static String decrypt(String base64Data,String password,String base64Salt)throws Exception{
		byte[] salt = Base64.decode(base64Salt);
		byte[] data = Base64.decode(base64Data);
		return new String(decrypt(data, password, salt));
	}
	
	private static String  showByteArray(byte[] data){
		if(null == data){
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for(byte b:data){
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		/*byte[] salt = initSalt();
		String base64salt = Base64.encode(salt);
		System.out.println("base64salt:" + Base64.encode(salt));
		System.out.println("apache base64salt :"+new String(org.apache.commons.codec.binary.Base64.encodeBase64(salt)));
		System.out.println("salt��"+showByteArray(salt));
		//�����password��Ҫ��ASCII�룬��Ȼ�ᱨ�쳣
		String password = "1111";
		System.out.println("���"+password);
		
		String data ="PBE����";
		System.out.println("����ǰ����: string:"+data);
		System.out.println("����ǰ����: byte[]:"+showByteArray(data.getBytes()));
		System.out.println();
		byte[] encryptData = encrypt(data.getBytes(), password,salt);
		String base64data = Base64.encode(encryptData);
		System.out.println("���ܺ�����: byte[]:"+showByteArray(encryptData));
		System.out.println("���ܺ�����: hexStr:"+Hex.encodeHexStr(encryptData));
		System.out.println("���ܺ�����base64:"+base64data);
		System.out.println();
		byte[] d = Base64.decode(base64data);
		byte[] s = Base64.decode(base64salt);
		byte[] decryptData = decrypt(d, password,s);
		System.out.println("���ܺ�����: byte[]:"+showByteArray(decryptData));
		System.out.println("���ܺ�����: string:"+new String(decryptData));*/
		
		String base64Salt = initBase64Salt();
		System.out.println("base64Salt: "+base64Salt);
		String pwd = "";
		String data = "";
		String base64Result = encrypt(data, pwd, base64Salt);
		System.out.println("base64Result :"+base64Result);
		String original = decrypt(base64Result, pwd, base64Salt);
		System.err.println("original result: "+original);
		
		String result = "qiOBz+PqkqD4lh3nFqbPyg==";
		original = decrypt(result, pwd, base64Salt);
		System.out.println(original);
	} 
}
