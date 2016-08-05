package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 用于登录百度贴吧账号并签到的类
 * 
 * @author jingchen
 * 
 */
public class BaiduSignUp implements SignUpTool {
	// 登录链接
	private String mLoginUrl = null;
	// 登录后跳转的页面
	private String mIndexUrl = null;
	// 获取网页中的相对路径拼接上这个头部构成完整请求路径
	private String mUrlHead = null;
	// 是否需要验证码
	private boolean isAuth = false;

	private HttpUtil httpUtil;

	// 关注的贴吧
	private List<String> mLikeBars;
	// 关注的贴吧首页
	private List<String> mLikeBarsUrls;

	public BaiduSignUp() {
		mLikeBars = new ArrayList<String>();
		mLikeBarsUrls = new ArrayList<String>();
		httpUtil = new HttpUtil();
		mLoginUrl = "http://wappass.baidu.com/passport/login";
		mIndexUrl = "http://tieba.baidu.com/mo";
		mUrlHead = "http://tieba.baidu.com";
	}

	public boolean login(String username, String passwd) {
		isAuth = false;
		mLikeBars.clear();
		mLikeBarsUrls.clear();
		print("login...");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", passwd));
		HttpEntity he;
		try {
			he = new UrlEncodedFormEntity(params, "UTF-8");
			HttpResponse hr = httpUtil.post(mLoginUrl, he);
			String firstresult = EntityUtils.toString(hr.getEntity());
			if (firstresult.contains("verifycode")) {
				// 在异地登录或者登录频繁会出现验证码
				isAuth = true;
				print("需要验证码");
				return false;
			} else if (firstresult.contains("error_area")) {
				print("密码错误");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		print("登录成功");
		return true;
	}

	public boolean signUp() {
		if (isAuth) {
			print("需要验证码");
			return false;
		}
		print("signUp...");
		if (mLikeBars.size() != 0) {
			mLikeBars.clear();
			mLikeBarsUrls.clear();
		}
		if (!getLikeBars())
			return false;
		try {
			for (int i = 0; i < mLikeBars.size(); i++) {
				String barview = getWebContent(mLikeBarsUrls.get(i));
				if (!barview.contains("sign"))
					print(mLikeBars.get(i) + "已签到");
				else {
					Elements signurl = Jsoup.parse(barview).getElementsByAttributeValueMatching("href", ".*sign.*");
					getWebContent(mUrlHead + signurl.attr("href"));
					print(mLikeBars.get(i) + "签到成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			print("fail in signUp!");
			return false;
		}
		return true;
	}

	/**
	 * 获取关注的贴吧
	 * 
	 * @return
	 */
	private boolean getLikeBars() {
		print("getLikeBars...");
		String indexresult = getWebContent(mIndexUrl);
		if (indexresult == null)
			return false;
		Document document = Jsoup.parse(indexresult);
		Elements likebars = document.select("div.my_love_bar a");
		for (Element e : likebars) {
			mLikeBarsUrls.add(mUrlHead + e.attr("href"));
			mLikeBars.add(e.text());
		}
		if (mLikeBars.size() == 0)
			return false;
		return true;
	}

	/**
	 * 获取网页内容
	 * 
	 * @param url
	 *            链接地址
	 * @return 网页内容
	 */
	private String getWebContent(String url) {
		print("getWebContent...");
		String result = null;
		try {
			result = httpUtil.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public void print(String s) {
		System.out.println(s);
	}

	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}
}
