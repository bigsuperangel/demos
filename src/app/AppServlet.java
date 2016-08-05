package app;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseHttpServlet;

public class AppServlet extends BaseHttpServlet {
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		addMethods("app1","app2","app3");
	}
	
	public void app1(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		out.print("app1");
	}
	
	public void app2(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		out.print("app2");
	}
	
	public void app3(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		out.print("app3");
	}
}
