package util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 加密原理
	DES 使用一个 56 位的密钥以及附加的 8 位奇偶校验位，产生最大 64 位的分组大小。这是一个迭代的分组密码，使用称为 Feistel 的技术，其中将加密的文本块分成两半。使用子密钥对其中一半应用循环功能，然后将输出与另一半进行“异或”运算；接着交换这两半，这一过程会继续下去，但最后一个循环不交换。DES 使用 16 个循环，使用异或，置换，代换，移位操作四种基本运算。
	JDK对DES算法的支持
	密钥长度：56位 
	工作模式：ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128
	填充方式：Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 */
public class DES {
	
	/**
	 * 密钥算法
	*/
	private static final String KEY_ALGORITHM = "DES";
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
//	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/ISO10126Padding";
	
	
	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥 
	 * @throws Exception
	 */
	public static byte[] initSecretKey() throws Exception{
		//返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		//初始化此密钥生成器，使其具有确定的密钥大小
		kg.init(56);
		//生成一个密钥
		SecretKey  secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 生成base64加密过的密钥
	 * @return
	 * @throws Exception
	 */
	public static String initBase64SecretKey() throws Exception{
		return Base64.encode(initSecretKey());
	}
	
	/**
	 * 转换密钥
	 * 
	 * @param key	二进制密钥
	 * @return Key	密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception{
		//实例化DES密钥规则
		DESKeySpec dks = new DESKeySpec(key);
		//实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//生成密钥
		SecretKey  secretKey = skf.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param base64encodeKey	base64加密过的密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return string	返回加密过的base64数据密文
	 * @throws Exception
	 */
	public static String encrypt(String data,String base64encodeKey) throws Exception{
		//还原密钥
		byte[] key = Base64.decode(base64encodeKey);
		return Base64.encode(encrypt(data.getBytes(), key));
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	二进制密钥
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	二进制密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}
	
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据 base64加密
	 * @param key	密钥 base64加密
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return string	明文
	 * @throws Exception
	 */
	public static String decrypt(String base64Data,String base64Key) throws Exception{
		//还原密钥
		byte[] key = Base64.decode(base64Key);
		byte[] data = Base64.decode(base64Data);
		return new String(decrypt(data, key));
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	二进制密钥
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	密钥
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	二进制密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
		//还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws Exception {
		String key = initBase64SecretKey();
		System.out.println("生成密钥为:"+key);
		
		String data ="DES数据";
		System.out.println("加密前数据: string:"+data);
		System.out.println();
		
		String encryptData = encrypt(data,key);
		System.out.println("加密后数据: base64:"+encryptData);
		System.out.println();
		
		String decryptData = decrypt(encryptData, key);
		System.out.println("解密后数据: string:"+decryptData);
		
	}
}


