package test;

/**
 * @author ╱―★神★―╲
 * @请注意,本程序只适用于常用帐号(无需验证码的)。经常登录的一般都不需要。
 * 		要验证码登录的要取图片,然后带验证码提交，我就懒得写了。
 */
public class Qiandao {
	
	private String auth = null;
	private String uid = null;
	private String ssid = null;
	
	public boolean login(String user,String pwd) {
		String html = Http.post("http://wappass.baidu.com/passport/", "login_username="+user+"&login_loginpass="+pwd+"&aaa=%E7%99%BB%E5%BD%95&login=yes&can_input=0&u=http%3A%2F%2Fwapp.baidu.com%2F&login_start_time="+this.baiduTime() / 1000 +"&tpl=tb&tn=bdIndex&pu=&ssid=&from=&bd_page_type=1&uid=wapp_" + this.baiduTime()+"_623&login_username_input=0&type=","UTF-8");
		auth = Http.mid(html, "auth=","&");
		uid = Http.mid(html, "uid=", "&");
		ssid = Http.mid(html, "ssid=", "\"");
		
		System.out.println("auth="+auth);
		System.out.println("uid="+uid);
		System.out.println("ssid="+ssid);
		
		return  auth != null  && uid !=null && ssid !=null;
	
	}
	
	
	public boolean qiandao(String kw) {
		String tbs = Http.get("http://wapp.baidu.com/f/q-" + ssid + "--" +  uid + "--1-3-0-" +  auth + "-" + uid +"/m?kw=" + kw, "UTF-8");
		tbs = Http.mid(tbs, "tbs\" value=\"","\"");
		
		System.out.println("tbs="+tbs);
		
		if(tbs == null) {
			return false;
		}
	
		String html = Http.get("http://wapp.baidu.com/f/q-" + ssid + "--" + uid + "--1-1-0-" +  auth + "-" +  uid + "/sign?tbs=" +  tbs + "&kw=" +  kw, "UTF-8");
		
	//	System.out.println(html);
	
		return html.indexOf("签到成功") != -1;
		
	}
	
	
	
	/**
	 * @return 取百度官方时间
	 */
	public long baiduTime(){
		return Long.valueOf(Http.mid(Http.get("http://open.baidu.com/special/time/","GBK"), "window.baidu_time(", ")"));
	}
	

	public static void main(String[] args) {
	  Qiandao qd = new Qiandao();
	  qd.login("这里填帐号", "这里填密码");
	  System.out.println(qd.qiandao("这里填贴吧名"));
	}
}
