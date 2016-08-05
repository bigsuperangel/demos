package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.HttpTool;
import util.HttpUtil;

public class Kindle114 {
	private static final String USERNAME_STRING = "nimabibi";
	private static final String PASSWORD_STRING = "asdfghjkl";
	private static final String REFER_STRING = "http://www.kindle114.com/forum-2-1.html";
	private static final String LOGIN_URL = "http://www.kindle114.com/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=#&inajax=1";
	private static final String PICK_STRING = "http://www.kindle114.com/home.php?mod=spacecp&ac=favorite&type=forum&id=2&handlekey=favoriteforum";
	private static final String HOST_STRING = "http://www.kindle114.com/";

	public static void main(String[] args) throws Exception {
		HttpUtil httpUtil = new HttpUtil();
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
/*		HttpEntity entity = response.getEntity();
		String ans = EntityUtils.toString(entity, "utf-8");
		System.out.println(ans);*/
		result = httpUtil.get(REFER_STRING);
//		result= httpUtil.get(PICK_STRING);
		System.out.println(result);
//		document = Jsoup.parse(result);
//		Element el = document.select(".mb_1").first();
//		String str = el.child(3).attr("onclick");
//		str = HOST_STRING + str.substring(str.indexOf(",")+2,str.length()-3);
//		result = HttpTool.get(str);
//		System.out.println(result);
	}
}
