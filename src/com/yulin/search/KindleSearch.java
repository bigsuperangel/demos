package com.yulin.search;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.HttpTool;

public class KindleSearch {
	private static final String USERNAME_STRING = "nimabibi";
//	private static final String PASSWORD_STRING = "c44a471bd78cc6c2fea32b9fe028d30a";
	private static final String PASSWORD_STRING = "asdfghjkl";
	private static final String REFER_STRING = "http://www.kindle114.com/forum-2-1.html";
	private static final String LOGIN_URL = "http://www.kindle114.com/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=#&inajax=1";
	private static final String PICK_STRING = "http://www.kindle114.com/home.php?mod=spacecp&ac=favorite&type=forum&id=2&handlekey=favoriteforum";
	private static final String HOST_STRING = "http://www.kindle114.com/";
	private static final String SIGN_IN =
		"http://www.kindle114.com/plugin.php?id=ljdaka:daka&action=msg&formhash=#&infloat=yes&handlekey=ljdaka&inajax=1&ajaxtarget=fwin_content_ljdaka";
	private static final String CATEGORY_URL = "http://www.kindle114.com/forum.php?mod=forumdisplay&fid=2&filter=sortid&sortid=1&searchsort=1&geshi=1&page=1";
	private Map<String, String> map = null;

	
	public String login() throws Exception{
		System.out.println("login.....................");
		String result = HttpTool.get("http://www.kindle114.com");
	    result = HttpTool.get("http://www.kindle114.com/member.php?mod=logging&action=login");
		Document document = Jsoup.parse(result);
		Element formhash_Element = document.select("input[name=formhash]").first();
		String formhash = formhash_Element.attr("value");
		Element referer_Element = document.select("input[name=referer]").first();
		String referer = referer_Element.attr("value");
		System.out.println("referer:"+referer);
		System.out.println("formhash:"+formhash);
		String formAction = document.select("form[name=login]").attr("action");
		System.out.println("formAction:"+formAction);
		String loginhash = formAction.substring(formAction.lastIndexOf("=") + 1);
		System.out.println("loginhash:"+loginhash);
		String loginUrl = LOGIN_URL.replace("#", loginhash);
		System.out.println("loginUrl:"+loginUrl);
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", USERNAME_STRING);
		map.put("password", PASSWORD_STRING);
		map.put("formhash", formhash);
		map.put("referer", referer);
		map.put("questionid", "0");
		map.put("loginsubmit", "true");
		map.put("answer", "");
		return HttpTool.post(loginUrl, map);
//		return  HttpTool.get(REFER_STRING);
	}
	
	public void index()throws Exception{
		System.out.println("index....................");
		HttpTool.get(REFER_STRING);
	}
	
	public void pick() throws Exception{
		HttpTool.get(PICK_STRING);
	}
	
	public void signIn(String result){
		System.out.println("signIn......");
		try {
			Document document = Jsoup.parse(result);
			String formhash = document.select("input[name=formhash]").first().attr("value");
//			Element el = document.select(".mb_1").first();
//			String str = el.child(3).attr("onclick");
//			str = HOST_STRING + str.substring(str.indexOf(",")+2,str.length()-3);
//			HttpTool.get(str);
			String url = SIGN_IN.replace("#",formhash);
			HttpTool.get(url);
		} catch (Exception e) {
			
		}
	}
	
	public void categorys(String indexResponse)throws Exception{
		Document document = Jsoup.parse(indexResponse);
		Element categorys = document.select("#thread_types").first();
		Elements elements = categorys.children();
		if (map!=null) {
			map = new HashMap<String, String>();
			for (Element element : elements) {
//				System.out.println("11111"+element.attr("href"));
//				System.out.println("2222"+element.text());
				map.put(element.text(), HOST_STRING+element.attr("href"));
			}
		}
	}
	
	/**
	 * 
	 * @param index
	 * @throws Exception
	 */
	public String category()throws Exception{
		System.out.println("category..................");
		Document document = Jsoup.parse(HttpTool.get(CATEGORY_URL));
		Element categorys = document.select("#thread_types").first();
		Element element = categorys.children().get(4); //取到it
		Element category = element.select("a").first();
		String href = category.attr("href");
		System.out.println("栏目为："+category.text() + "-链接为:"+HOST_STRING + href);
		return HttpTool.get(href);
	}
	
	public String article(String result) throws Exception{
		System.out.println("article..................");
		Document document = Jsoup.parse(result);
		Elements articles = document.select(".xst");
		Element element = articles.first();
		String href = element.attr("href");
		System.out.println("帖子为:"+element.text()  +"-链接为："+HOST_STRING+href);
		return HttpTool.get(HOST_STRING+href);
	}
	
	public String reply(String result) throws Exception{
		System.out.println("reply................");
		Document document = Jsoup.parse(result);
		Element posttimeEle = document.select("input[name=posttime]").first();
		String posttime = posttimeEle.attr("value");
		Element formhashEle = document.select("input[name=formhash]").first();
		String formhash = formhashEle.attr("value");
		Element subjectEle = document.select("input[name=subject]").first();
		String subject = subjectEle.attr("value");
		Element reply = document.select("#fastpostform").first();
		String href = HOST_STRING + reply.attr("action");
		
		String stringEntity = "message=%B8%D0%D0%BB%C2%A5%D6%F7%B7%D6%CF%ED&posttime="+posttime+"&formhash="+formhash+"&usesig=&subject=++";
		System.out.println("回复帖子："+href+"&"+stringEntity);
//		Map<String, String > map = new HashMap<String, String>();
//		map.put("synctosina", "ture");
//		map.put("message", "小生非常谢谢楼主分享小生非常谢谢楼主分享 赞");
//		map.put("posttime", posttime);
//		map.put("formhash", formhash);
//		map.put("subject", subject);
//		return HttpTool.post(href, map);
		return HttpTool.postByStringEntity(href, stringEntity);
	}
}
