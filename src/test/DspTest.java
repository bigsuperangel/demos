package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.yulin.search.DspSearch;

public class DspTest {
	
	public static String readFile() {
		InputStream is = DspTest.class.getResourceAsStream("../xml");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			System.out.println(sb.toString());
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void test() throws Exception{
		DspSearch search = new DspSearch();
		search.login();
		search.doTask();
	}
	
	public static void main(String[] args) throws Exception{
//		DspSearch search = new DspSearch();
//		search.login();
//		search.doTask();
		String result = readFile();
		Document doc = Jsoup.parse(result);
		Elements elements = doc.select("rows").first().children();
		for (Element element : elements) {
//			Attributes attributes = element.attributes(); //取row所有属性
//			for (Attribute attribute : attributes) {
//				System.out.println(attribute.getValue());
//				System.out.println(attribute.getKey());
//				System.out.println(attribute.html());
//			}
			
//			List<Node> nodes = element.childNodes();
//			for (Node node : nodes) {
//				System.out.println("11"+node.nodeName());
//				System.out.println("22"+node.toString());
//				System.out.println("33"+node.outerHtml());
//			}
			List<DataNode> dataNodes = element.dataNodes();
			for (DataNode dataNode : dataNodes) {
				System.out.println(dataNode.nodeName());
				System.out.println(dataNode.toString());
				System.out.println(dataNode.outerHtml());
			}
			
		}
	}
	
	
}
