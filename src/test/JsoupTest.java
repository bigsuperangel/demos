package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest {
	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
        map.put("j_username", "10086admin");
        map.put("j_password", "10086");
        //如果有跳转（302），会自动跳转
        Document doc = Jsoup.connect("http://172.16.16.180:8880/fss").data(map)
                .timeout(3000).post();
        //获取网页的标题
        String title = doc.title();
        System.out.println(title);

        
        doc.getElementById("jt").attr("value", "10086");
        
        //获取body中的非格式的内容
        String body = doc.body().html();
        System.out.println(body);
        System.out.println();
	}
}
