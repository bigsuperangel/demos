package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseHttpServlet;

public class TestServlet extends BaseHttpServlet {

	@Override
	public void init() throws ServletException{
		// TODO Auto-generated method stub
		System.out.println("ddddd");
		addMethods("test1","test2","test3","test4");
		List<String > methods = new ArrayList<String>();
		methods.add("test5");
		addMethods(methods);
	}
	
	public void test1(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		out.print("success");
	}
	
	public void test2(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		try {
			int a = 2/0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("error");
		}
	}
	
	public void test3(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		try {
			if (1==1) {
				throw new Exception("111");
			}
		} catch (Exception e) {
			out.print(e.getMessage());
		}
	}
	
	public void test4(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		try {
			if (1==1) {
				throw new Exception("111");
			}
		} catch (Exception e) {
			out.print(e.getMessage());
		}
	}
	
	public void test5(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		try {
			int a = 2/0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("error");
		}
	}
	
}
