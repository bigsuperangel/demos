package util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
	Java 加密解密之对称加密算法PBE
	PBE是一种基于口令的加密算法，使用口令代替其他对称加密算法中的密钥，其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。 
	
	PBE算法是对称加密算法的综合算法，常见算法PBEWithMD5AndDES,使用MD5和DES算法构建了PBE算法。将盐附加在口令上，通过消息摘要算法经过迭代获得构建密钥的基本材料，构建密钥后使用对称加密算法进行加密解密。
	 
	JDK对DESede算法的支持
	 
	算法/密钥长度/默认密钥长度：
	1.PBEWithMD5AndDES/56/56
	2.PBEWithMD5AndTripleDES/112,168/168
	3.PBEWithSHA1AndDESede/112,168/168
	4.PBEWithSHA1AndRC2_40/40 to 1024/128
	工作模式：CBC
	填充方式：PKCS5Padding
 * 
 */
public class PBECoder {

	public static final String ALGORITHM = "PBEWITHMD5andDES";
	
	public static final int ITERATION_COUNT = 100;
	
	/**
	 * 初始盐<br/>
	 * 盐的长度必须为8位
	 * @return byte[] 盐 
	 * @throws Exception
	 */
	public static byte[] initSalt() throws Exception{
		//实例化安全随机数
		SecureRandom random = new SecureRandom();
		//产出盐
		return random.generateSeed(8);
	}
	
	/**
	 * 生成base64加密后的盐
	 * @return
	 * @throws Exception
	 */
	public static String initBase64Salt()throws Exception{
		return Base64.encode(initSalt());
	}
	
	/**
	 * 转换密钥
	 * 
	 * @param password	密码
	 * @return Key 密钥
	 */
	private static Key toKey(String password) throws Exception{
		//密钥材料
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		//实例化
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		//生成密钥
		return keyFactory.generateSecret(keySpec);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @param salt  盐
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{
		//转换密钥
		Key key = toKey(password);
		//实例化PBE参数材料
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		//实例化
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//初始化
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		//执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param password	口令
	 * @param base64Salt  盐 base64加密
	 * @return string	加密数据  base64
	 * @throws Exception
	 */
	public static String encrypt(String data,String password,String base64Salt) throws Exception{
		byte[] salt = Base64.decode(base64Salt);
		return Base64.encode(encrypt(data.getBytes(), password, salt));
	}
	
	/**
	 * 解密
	 * 
	 * @param data	待机密数据
	 * @param key	密钥
	 * @param salt  盐
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,String password,byte[] salt)throws Exception{
		//转换密钥
		Key key = toKey(password);
		//实例化PBE参数材料
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		//实例化
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//初始化
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		//执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * 
	 * @param base64Data	待解密数据 base64
	 * @param password	口令
	 * @param base64Salt  盐 base64
	 * @return string	解密数据
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
		System.out.println("salt："+showByteArray(salt));
		//这里的password需要是ASCII码，不然会报异常
		String password = "1111";
		System.out.println("口令："+password);
		
		String data ="PBE数据";
		System.out.println("加密前数据: string:"+data);
		System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));
		System.out.println();
		byte[] encryptData = encrypt(data.getBytes(), password,salt);
		String base64data = Base64.encode(encryptData);
		System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));
		System.out.println("加密后数据: hexStr:"+Hex.encodeHexStr(encryptData));
		System.out.println("加密后数据base64:"+base64data);
		System.out.println();
		byte[] d = Base64.decode(base64data);
		byte[] s = Base64.decode(base64salt);
		byte[] decryptData = decrypt(d, password,s);
		System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
		System.out.println("解密后数据: string:"+new String(decryptData));*/
		
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
