package util;

public interface SignUpTool {
    /**
     * 用户登录
     * 
     * @param username
     *            用户名
     * @param passwd
     *            密码
     * @return 登录成功返回true，失败则返回false
     */
    boolean login(String username, String passwd);

    boolean login();
    /**
     * 签到
     * 
     * @return 签到成功返回true，失败则返回falses
     */
    boolean signUp();
}

