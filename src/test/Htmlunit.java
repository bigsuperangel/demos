package test;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Htmlunit {
	public static void main(String[] args) throws IOException {
		WebClient webClient =  new WebClient();//����WebClient
        HtmlPage page = webClient.getPage("http://172.16.16.180:8880/fss");    //�򿪰ٶ�
        
        //���nameΪ"��½"��htmlԪ��
        HtmlElement jt = page.getElementById("jt");
        jt.setAttribute("jt", "10086");
        //���nameΪ"username"��htmlԪ��
        HtmlElement yh = page.getElementById("yh"); 
        yh.setAttribute("yh", "admin");
        //���idΪ"password"��htmlԪ��
        HtmlElement jp = (HtmlElement) page.getElementById("j_password");
        jp.setAttribute("j_password", "10086");

        //���nameΪ"��½"��Ԫ�� 
        HtmlElement submitEle = page.getElementByName("denglu"); 
        //�������½��
        page = submitEle.click();
        String result = page.asXml();//���click()���htmlҳ�棨������ǩ��
        System.out.println(result);
        if(result.contains("��½�ɹ���")){
             System.out.println("��½�ɹ�");     
        }else{
             System.out.println("��½ʧ��");
        }
	}
}
