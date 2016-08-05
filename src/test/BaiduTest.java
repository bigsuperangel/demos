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
					// ǩ���ɹ�����Сʱ����ǩ��
					System.out.println("continue after three hours...");
					Thread.sleep(3 * 60 * 60 * 1000);
				} else {
					// ǩ��ʧ����30���Ӻ��ٴ�ǩ��
					System.out.println("continue after " + mInterval + " minites...");
					Thread.sleep(mInterval * 60 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
