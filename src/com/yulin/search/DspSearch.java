package com.yulin.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DateUtil;
import util.HttpTool;

public class DspSearch {
	private static final String LOGIN_URL = "http://172.16.16.233:8098/dspApp/login_proxy.jsp"; 
	private static final String USERNAME = "linyu";
	private static final String PASSWORD = "9EwKVyTD";
	private static final String MY_TASK = "http://172.16.16.233:8098/dspApp/project/task_mgt/query_my_task_task.jsp";
	private static final String TASKS = "http://172.16.16.233:8098/dspApp/dhtmlxgrid?is_paging=true&page_count=25&current_page=1&paging_class_name=com.funo.dsp.query.MyTaskQuery&resource_id=13103&include_coo=&status=1,2,3,5&project_id=";
	private static final String ADD_JOB = "http://172.16.16.233:8098/dspApp/project/task_mgt/task_work_time.jsp?task_id=#";
	private static final String ADD_JOB_POST = "http://172.16.16.233:8098/dspApp/project/task_mgt/work_time_submit.jsp";
	private static final String TASK_UPDATE = "http://172.16.16.233:8098/dspApp/project/task_mgt/task_update.jsp?task_id=20141201152624603153&timeTag=#";
	private static final String TASK_UPDATE_POST = "http://172.16.16.233:8098/dspApp/project/task_mgt/task_feedback_submit.jsp";
	private static final String STATUS_BEGIN = "2";  //已开始
	private static final String STATUS_FINISH = "3";  //已完成
	private static final String STATUS_NO_BEGIN = "1"; //未开始
	private static final String STATUS_PAUSE = "6"; //暂停　
	
	
	public void login() throws Exception{
		print("login...........");
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", USERNAME);
		map.put("password", PASSWORD);
		HttpTool.post(LOGIN_URL, map);
	}
	
	//我的任务列表
	public String mytask() throws Exception{
		print("mytask.................");
		return HttpTool.get(TASKS);
	}
	
	//开始任务
	public void doTask()throws Exception{
		print("dotask................");
		Document document = Jsoup.parse(mytask());
		Elements elements = document.select("rows").first().children();  //获取所有的任务列表
		for (Element ele : elements) {
			String time = ele.child(3).text();
			String startTime = time.substring(0,10);  //项目开始时间
			String endTime = time.substring(14); 
			String taskId = ele.child(8).text();  //任务ID
			String taskContent = ele.child(9).text(); //任务内容
			String status = ele.child(7).text(); //任务状态
			String operateId = ele.child(14).text(); 
			if (status.equals("已完成") || status.equals("暂停")) {  //对已完成的不做处理
				print("任务"+taskContent + status+"跳过");
				continue;
			}
			String beginRemark = "开始"+taskContent;
			String finishRemark = "完成"+taskContent;
			print(startTime+ ":"+endTime+":"+taskId+":"+taskContent);
			List<String> list = DateUtil.daysBetweenTwoDay(startTime, endTime);
			print("开始任务:"+taskContent);
			// 更新开始状态
			taskUpdate(taskId, operateId, "null", STATUS_BEGIN, startTime, endTime, beginRemark);
			for (int i = 0; i < list.size(); i++) {
				//更新内容
				addJob(taskId, list.get(i), taskContent, operateId);
			}
			// 更新结束状态
			taskUpdate(taskId, operateId, "null", STATUS_FINISH, startTime, endTime, finishRemark);
		}
	}
	
	//更新任务状态
	public void taskUpdate(String taskId,String operator_id,String oper_type,String status,String real_begin_date
			,String real_end_date,String remark)throws Exception{
		TimeUnit.SECONDS.sleep(3);
		print("更新状态"+taskId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("task_id", taskId);
		map.put("oper_type", oper_type);
		map.put("real_begin_date", real_begin_date);
		map.put("real_end_date", real_end_date);
		map.put("remark", remark);
		map.put("operator_id", operator_id);
		map.put("status", status);
		HttpTool.post(TASK_UPDATE_POST, map);
	}
	
	//更新任务内容
	public void addJob(String taskId,String workDate,String taskContent,String operator_id)throws Exception{
//		String jobUrl = ADD_JOB.replace("#", taskId);
//		String result = HttpTool.get(jobUrl);
//		Document document = Jsoup.parse(result);
//		String operator_id = document.select("input[name=operator_id]").first().attr("value");
//		String oper_type = document.select("input[name=oper_type]").first().attr("value");
		TimeUnit.SECONDS.sleep(3);
		print("更新任务内容"+workDate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("task_id", taskId);
		map.put("oper_type", "null");
		map.put("work_date", workDate);
		map.put("work_time", "8");
		map.put("remark", taskContent);
		map.put("operator_id", operator_id);
		HttpTool.post(ADD_JOB_POST, map);
	}
	
	public void print(String str){
		System.out.println(str);
	}
	
}
