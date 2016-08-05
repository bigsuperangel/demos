package base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseHttpServlet extends HttpServlet{
	
	protected Map<String, Method> map = new HashMap<String, Method>();
	
	public void addMethod(String method){
		try {
			map.put(method, this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class));
		} catch (Exception e) {
		}
	}
	
	public void addMethods(String... method){
		for (String m : method) {
			addMethod(m);
		}
	}
	
	public void addMethods(List<String> methods){
		if (methods!=null && methods.size()>0) {
			for (String m : methods) {
				addMethod(m);
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String method = request.getParameter("method");
		PrintWriter out = response.getWriter();
		for (String s : map.keySet()) {
			System.out.println(s);
		}
		Method md = map.get(method);
		if (md!=null) {
			try {
				md.invoke(this, request,response);
			} catch (Exception e) {
			}
		}else {
			out.print("请求地址有误");
		}
		
/*		if (AdsUtil.checkNotEmptyOrNull(method)) {
			try {
				Method md = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
				md.invoke(this, request,response);
			} catch (Exception e) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("info", "请求地址有误");
				ResponseUtil.responseError(response, map);
			}
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("info", "请求地址有误");
			ResponseUtil.responseError(response, map);
		}*/
	}
}
