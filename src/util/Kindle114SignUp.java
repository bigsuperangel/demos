package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Kindle114SignUp implements SignUpTool{

	private HttpUtil httpUtil;
	private static final String USERNAME_STRING = "bigsuperangel";
	private static final String PASSWORD_STRING = "asdfghjkl";
	private static final String REFER_STRING = "http://www.kindle114.com/forum-2-1.html";
	private static final String LOGIN_URL = "http://www.kindle114.com/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=#&inajax=1";
	//收藏
	private static final String PICK_STRING = "http://www.kindle114.com/home.php?mod=spacecp&ac=favorite&type=forum&id=2&handlekey=favoriteforum";
	
	public Kindle114SignUp(){
		httpUtil = new HttpUtil(); 
	}
	
	@Override
	public boolean login(String username, String passwd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login() {
		try {
			String result = httpUtil.get("http://www.kindle114.com/member.php?mod=logging&action=login");
			Document document = Jsoup.parse(result);
			Element formhash_Element = document.select("input[name=formhash]").first();
			String formhash = formhash_Element.attr("value");
			System.out.println(formhash);
			String formAction = document.select("form").attr("action");
			String loginhash = formAction.substring(formAction.lastIndexOf("=") + 1);
			System.out.println(loginhash);
			String loginUrl = LOGIN_URL.replace("#", loginhash);
			System.out.println(loginUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", USERNAME_STRING));
			params.add(new BasicNameValuePair("password", PASSWORD_STRING));
			params.add(new BasicNameValuePair("formhash", formhash));
			params.add(new BasicNameValuePair("referer", REFER_STRING));
			params.add(new BasicNameValuePair("questionid", "0"));
			params.add(new BasicNameValuePair("loginsubmit", "true"));
			HttpEntity he = new UrlEncodedFormEntity(params, "GBK");
			;
			HttpResponse response = httpUtil.post(loginUrl, he);
			// 获得跳转的网址
//		Header[] locationHeader = response.getAllHeaders();
//		for (Header header : locationHeader) {
//			System.out.println(header.getValue());
//		}
			System.out.println(response.getStatusLine().getStatusCode());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean signUp() {
		// TODO Auto-generated method stub
		return false;
	}

}
