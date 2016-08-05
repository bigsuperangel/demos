package test;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Htmlunit {
	public static void main(String[] args) throws IOException {
		WebClient webClient =  new WebClient();//创建WebClient
        HtmlPage page = webClient.getPage("http://172.16.16.180:8880/fss");    //打开百度
        
        //获得name为"登陆"的html元素
        HtmlElement jt = page.getElementById("jt");
        jt.setAttribute("jt", "10086");
        //获得name为"username"的html元素
        HtmlElement yh = page.getElementById("yh"); 
        yh.setAttribute("yh", "admin");
        //获得id为"password"的html元素
        HtmlElement jp = (HtmlElement) page.getElementById("j_password");
        jp.setAttribute("j_password", "10086");

        //获得name为"登陆"的元素 
        HtmlElement submitEle = page.getElementByName("denglu"); 
        //点击“登陆”
        page = submitEle.click();
        String result = page.asXml();//获得click()后的html页面（包括标签）
        System.out.println(result);
        if(result.contains("登陆成功！")){
             System.out.println("登陆成功");     
        }else{
             System.out.println("登陆失败");
        }
	}
}
