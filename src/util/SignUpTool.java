package util;

public interface SignUpTool {
    /**
     * �û���¼
     * 
     * @param username
     *            �û���
     * @param passwd
     *            ����
     * @return ��¼�ɹ�����true��ʧ���򷵻�false
     */
    boolean login(String username, String passwd);

    boolean login();
    /**
     * ǩ��
     * 
     * @return ǩ���ɹ�����true��ʧ���򷵻�falses
     */
    boolean signUp();
}

