package util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * ����ԭ��
	DES ʹ��һ�� 56 λ����Կ�Լ����ӵ� 8 λ��żУ��λ��������� 64 λ�ķ����С������һ�������ķ������룬ʹ�ó�Ϊ Feistel �ļ��������н����ܵ��ı���ֳ����롣ʹ������Կ������һ��Ӧ��ѭ�����ܣ�Ȼ���������һ����С�������㣻���Ž��������룬��һ���̻������ȥ�������һ��ѭ����������DES ʹ�� 16 ��ѭ����ʹ������û�����������λ�������ֻ������㡣
	JDK��DES�㷨��֧��
	��Կ���ȣ�56λ 
	����ģʽ��ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128
	��䷽ʽ��Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 */
public class DES {
	
	/**
	 * ��Կ�㷨
	*/
	private static final String KEY_ALGORITHM = "DES";
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
//	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/ISO10126Padding";
	
	
	/**
	 * ��ʼ����Կ
	 * 
	 * @return byte[] ��Կ 
	 * @throws Exception
	 */
	public static byte[] initSecretKey() throws Exception{
		//��������ָ���㷨��������Կ�� KeyGenerator ����
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		//��ʼ������Կ��������ʹ�����ȷ������Կ��С
		kg.init(56);
		//����һ����Կ
		SecretKey  secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * ����base64���ܹ�����Կ
	 * @return
	 * @throws Exception
	 */
	public static String initBase64SecretKey() throws Exception{
		return Base64.encode(initSecretKey());
	}
	
	/**
	 * ת����Կ
	 * 
	 * @param key	��������Կ
	 * @return Key	��Կ
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception{
		//ʵ����DES��Կ����
		DESKeySpec dks = new DESKeySpec(key);
		//ʵ������Կ����
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//������Կ
		SecretKey  secretKey = skf.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param base64encodeKey	base64���ܹ�����Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return string	���ؼ��ܹ���base64��������
	 * @throws Exception
	 */
	public static String encrypt(String data,String base64encodeKey) throws Exception{
		//��ԭ��Կ
		byte[] key = Base64.decode(base64encodeKey);
		return Base64.encode(encrypt(data.getBytes(), key));
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//��ԭ��Կ
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}
	
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//ʹ����Կ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	/**
	 * ����
	 * 
	 * @param data	���������� base64����
	 * @param key	��Կ base64����
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return string	����
	 * @throws Exception
	 */
	public static String decrypt(String base64Data,String base64Key) throws Exception{
		//��ԭ��Կ
		byte[] key = Base64.decode(base64Key);
		byte[] data = Base64.decode(base64Data);
		return new String(decrypt(data, key));
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��������Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//��ԭ��Կ
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}
	
	/**
	 * ����
	 * 
	 * @param data	����������
	 * @param key	��Կ
	 * @param cipherAlgorithm	�����㷨/����ģʽ/��䷽ʽ
	 * @return byte[]	��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//ʹ����Կ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.DECRYPT_MODE, key);
		//ִ�в���
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws Exception {
		String key = initBase64SecretKey();
		System.out.println("������ԿΪ:"+key);
		
		String data ="DES����";
		System.out.println("����ǰ����: string:"+data);
		System.out.println();
		
		String encryptData = encrypt(data,key);
		System.out.println("���ܺ�����: base64:"+encryptData);
		System.out.println();
		
		String decryptData = decrypt(encryptData, key);
		System.out.println("���ܺ�����: string:"+decryptData);
		
	}
}


