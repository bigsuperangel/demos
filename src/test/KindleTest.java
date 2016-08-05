package test;

import java.io.File;

import util.HttpTool;

import com.yulin.search.KindleSearch;

public class KindleTest {
	private static final String downloadUrl = "http://www.kindle114.com/forum.php?mod=attachment&aid=MjAwODR8NjBkMjI0NjZ8MTQxOTgzNTUwM3wxNTIxNTZ8OTQ0Mw%3D%3D";
	public static void main(String[] args) throws Exception {
//		System.out.println(HttpTool.get("http://www.kindle114.com/member.php?mod=logging&action=login"));
		KindleSearch search = new KindleSearch();
		String indexString = search.login();
		System.out.println(indexString);
//		search.signIn(indexString);
//		indexString = search.category();
//		indexString = search.article(indexString);
////		System.out.println("article:...................................................."+indexString);
//		search.reply(indexString);
//		HttpTool.downLoad(downloadUrl, "F:\\1.mobi");
	}
}
