package util;

import java.io.IOException;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	private CloseableHttpClient mHttpClient;
	private CookieStore mCookieStore;
	private HttpContext mContext;
	private HttpPost post;
	private HttpGet get;

	public HttpUtil() {
		mHttpClient = HttpClients.createDefault();
		mCookieStore = new BasicCookieStore();
		mContext = new BasicHttpContext();
	}

	public HttpResponse post(String url, HttpEntity he) throws ClientProtocolException, IOException {
		post = new HttpPost(url);
		post.setEntity(he);
		mContext.setAttribute(HttpClientContext.COOKIE_STORE, mCookieStore);
		
		HttpResponse hr = mHttpClient.execute(post, mContext);
		return hr;
	}

	public String get(String url) throws Exception {
		String result = null;
		get = new HttpGet(url);
		HttpResponse hr = mHttpClient.execute(get, mContext);
//		result = printResponse(hr);
//		return result;
		return EntityUtils.toString(hr.getEntity());
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
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString);
//			System.out.println("response content:"
//					+ responseString.replace("\r\n", ""));
			return responseString;
		}
		return null;
	}
}
