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
 * ���ڵ�¼�ٶ������˺Ų�ǩ������
 * 
 * @author jingchen
 * 
 */
public class BaiduSignUp implements SignUpTool {
	// ��¼����
	private String mLoginUrl = null;
	// ��¼����ת��ҳ��
	private String mIndexUrl = null;
	// ��ȡ��ҳ�е����·��ƴ�������ͷ��������������·��
	private String mUrlHead = null;
	// �Ƿ���Ҫ��֤��
	private boolean isAuth = false;

	private HttpUtil httpUtil;

	// ��ע������
	private List<String> mLikeBars;
	// ��ע��������ҳ
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
				// ����ص�¼���ߵ�¼Ƶ���������֤��
				isAuth = true;
				print("��Ҫ��֤��");
				return false;
			} else if (firstresult.contains("error_area")) {
				print("�������");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		print("��¼�ɹ�");
		return true;
	}

	public boolean signUp() {
		if (isAuth) {
			print("��Ҫ��֤��");
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
					print(mLikeBars.get(i) + "��ǩ��");
				else {
					Elements signurl = Jsoup.parse(barview).getElementsByAttributeValueMatching("href", ".*sign.*");
					getWebContent(mUrlHead + signurl.attr("href"));
					print(mLikeBars.get(i) + "ǩ���ɹ�");
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
	 * ��ȡ��ע������
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
	 * ��ȡ��ҳ����
	 * 
	 * @param url
	 *            ���ӵ�ַ
	 * @return ��ҳ����
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
