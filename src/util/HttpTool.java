package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;

public class HttpTool {
	private static final String CHARSET = "utf-8";
	private static HttpClient httpClient;
	private static BasicCookieStore cookieStore;
	private static HttpContext context;

	static {
		httpClient = HttpClients.createDefault();
		cookieStore = new BasicCookieStore();
		context = new BasicHttpContext();
	}

	public static String post(String url, Map<String, String> rowParams) throws Exception {
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        // 用逗号分隔显示可以同时接受多种编码
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		String result = null;
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 请求参数
		for (String key : rowParams.keySet()) {
			formparams.add(new BasicNameValuePair(key, rowParams.get(key)));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, CHARSET);
		System.out.println("1111111"+EntityUtils.toString(entity));
		// 创建POST请求
		post.setEntity(entity);
		context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		// 发送请求
		HttpResponse response = httpClient.execute(post, context);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
        {
			System.out.println("redirect...........");
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
            Header locationHeader = response.getFirstHeader("Location");
            String location = null;
            if (locationHeader != null)
            {
                location = locationHeader.getValue();
                result = post(location, rowParams);
//                Header hostHeader = post.getFirstHeader("Host");
//                System.out.println(hostHeader==null);
//                String redirect = hostHeader.getValue() + "/"+location;
//                System.out.println("链接跳转:"+redirect);
//                result=  get(location);//用跳转后的页面重新请求。  
            }
        }
        else if(statusCode == HttpStatus.SC_OK)
        {
        	result= printResponse(response);
        }
        else {
			throw new RuntimeException("请求失败");
		}
		return result;
		
	}
	
	public static String postByStringEntity(String url, String stringEntity) throws Exception {
		HttpPost post = new HttpPost(url);
		String redirectUrl = "http://"+ post.getURI().getHost() ;
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        // 用逗号分隔显示可以同时接受多种编码
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		String result = null;
		// 构造最简单的字符串数据   
	     StringEntity reqEntity = new StringEntity(stringEntity,CHARSET);   
	    // 设置类型   
	     reqEntity.setContentType("application/x-www-form-urlencoded");   
	    // 设置请求的数据   
	     post.setEntity(reqEntity);   
		context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		// 发送请求
		HttpResponse response = httpClient.execute(post, context);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
        {
			System.out.println("redirect...........");
            Header locationHeader = response.getFirstHeader("Location");
            String location = null;
            if (locationHeader != null)
            {
                location = locationHeader.getValue();
                String redirect = redirectUrl + "/"+location;
                System.out.println("链接跳转:"+redirect);
                result=  get(redirect);//用跳转后的页面重新请求。   
            }
        }
        else if(statusCode == HttpStatus.SC_OK)
        {
        	result= printResponse(response);
        }
        else {
			throw new RuntimeException("请求失败");
		}
		return result;
		
	}

	public static String get(String url) throws Exception {
		HttpUriRequest httpget = new HttpGet(url);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        // 用逗号分隔显示可以同时接受多种编码
        httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
        httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		// 发送请求，返回响应
		HttpResponse response = httpClient.execute(httpget,context);
		// 打印响应信息
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("请求失败");
		}
		return printResponse(response);
	}
	
	public static void downLoad(String url,String fileName) throws Exception {
//		HttpUriRequest httpget = new HttpGet(url);
		HttpPost httpget = new HttpPost(url);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        // 用逗号分隔显示可以同时接受多种编码
        httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
        httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        httpget.setHeader("Connection", "keep-alive");
//        httpget.setHeader("Accept-Encoding","gzip, deflate,compress,identity");
		// 发送请求，返回响应
		HttpResponse response = httpClient.execute(httpget,context);
		// 打印响应信息
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("请求失败");
		}
		InputStream is = response.getEntity().getContent();
	    try{
	        FileOutputStream fout =new FileOutputStream(fileName);
	        int l = -1;
	        byte[] tmp =new byte[1024];
	        while((l = is.read(tmp)) != -1) {
	            fout.write(tmp,0, l);
	            // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
	        }
	        fout.flush();
	        fout.close();
	    }finally{
	        // 关闭低层流。
	        is.close();
	    }
//		FileUtils.copyInputStreamToFile(is, new File(fileName));
	}
	
	//第一个参数，网络连接；第二个参数，保存到本地文件的地址
	public static void getFile(String url, String fileName) {
			HttpPost get = new HttpPost(url);
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
	        // 用逗号分隔显示可以同时接受多种编码
			get.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
			get.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
			get.setHeader("Connection", "keep-alive");
//			get.setHeader("Accept-Encoding","gzip, deflate,compress,identity");
			FileOutputStream out = null;

			try {
				ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
					public byte[] handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							return EntityUtils.toByteArray(entity);
						} else {
							return null;
						}
					}
				};
				out = new FileOutputStream(fileName);
				byte[] charts = httpClient.execute(get, handler,context);
				out.write(charts);

			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	private static String printResponse(HttpResponse httpResponse) throws Exception {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
//			System.out.println("response length:" + responseString.length());
//			System.out.println("response content:" + responseString);
//			System.out.println("response content:"
//					+ responseString.replace("\r\n", ""));
			return responseString;
		}
		
		return null;
	}
}
