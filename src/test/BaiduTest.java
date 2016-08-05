package test;

import util.BaiduSignUp;
import util.SignUpTool;


public class BaiduTest {
	public static String username = "bigsuperangel";
	public static String passwd = "asdfghjkl";
	public static int mInterval = 30;

	public static void main(String[] args) {
		boolean isSignup = false;
		boolean isLogin = false;
		SignUpTool tool = new BaiduSignUp();
		// SignUpTool tool = new BBSLogin();
		while (true) {
			try {
				isLogin = tool.login(username, passwd);
				isSignup = tool.signUp();
				if (isLogin && isSignup) {
					// 签到成功则三小时后再签到
					System.out.println("continue after three hours...");
					Thread.sleep(3 * 60 * 60 * 1000);
				} else {
					// 签到失败则30分钟后再次签到
					System.out.println("continue after " + mInterval + " minites...");
					Thread.sleep(mInterval * 60 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
