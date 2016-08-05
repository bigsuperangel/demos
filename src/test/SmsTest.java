package test;

import java.net.URI;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;


public class SmsTest {
	public static void main(String[] args) throws Exception {
//		String str = "请点击链接完成支付：https://wap.cebbank.com/pwap/P.do?d=GE22JRV215B11M3 。[光大银行]";
//		String sms = str.substring(str.indexOf("=")+1,str.indexOf("。"));
//		System.out.println(sms);
		
		
		String string = "http://www.kindle114.com/thread-9661-1-1.html";
//		URI uri = new URI(string);
//		System.out.println(uri.getScheme());
//		System.out.println(uri.getHost());
//		System.out.println(uri.getPath());
//		System.out.println(uri.getSchemeSpecificPart());
//		System.out.println(uri.getPort());
//		System.out.println(uri.getUserInfo());
//		System.out.println(uri.getAuthority());
//		System.out.println(uri.getFragment());
//		System.out.println(uri.getRawSchemeSpecificPart());
		
//		Class cls = Class.forName("java.net.URI");
//		Constructor con = cls.getConstructor(String.class);
//        Object obj = con.newInstance(string);
//		Method[] methods = cls.getMethods();
//		for (Method method : methods) {
//			if (method.getName().startsWith("get")) {
//				System.out.println(method.getName() + ":"+method.invoke(obj));
//			}
//		}
		
		URI uri = (URI) ConstructorUtils.invokeConstructor(URI.class, string);
		Object str = MethodUtils.invokeMethod(uri, "getPort", null);
		System.out.println(str);
		
		string = StringUtils.join(new String[] { "Hello", "World" }, ","); 
		System.out.println(string);
		
		Map<String, String> map = Maps.newHashMap();
	}
}
