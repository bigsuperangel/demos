package util;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class DateUtil {
	
	private DateUtil(){
	}

	public static final String DAO_SELECT_DATETIME_FORMAT  = "yyyy-mm-dd hh24:mi:ss";                //dao层查找数据库　时间格式
	public static final String DAO_SELECT_DATE_FORMAT      = "yyyy-mm-dd";
	public static final String DATE_FORMAT        = "yyyy-MM-dd HH:mm:ss";                           //java.util的时间格式
	public static final String DATE_FORMAT_TIME   = "HH:mm:ss";                                      //java.util的时间格式
	public static final String DATE_FORMAT_DAY    = "yyyy-MM-dd";                                    //java.util的时间格式
	
	public static final String DATE_FORMAT_DAY_DATE4J = "YYYY-MM-DD";  //date4j格式
	public static final String DATE_FORMAT_DATE4J = "YYYY-MM-DD hh:mm:ss"; //date4j格式
	public static final String DATE_FORMAT_MONTH_DATE4J = "YYYY-MM";//date4j格式
	public static final String DATE_FORMAT_DATE4J_TIME = "YYYYMMDDhhmmsssss";//date4j格式
	
	/**
	 * 把时间格式化成字符串
	 * @param format
	 * @return
	 */
	public static String now2String(String format){
		return DateTime.now(TimeZone.getDefault()).format(format);
	}
	
	/**
	 * 日期格式化成字符�?
	 * @param format
	 * @return
	 */
	public static String today2String(String format){
		return DateTime.today(TimeZone.getDefault()).format(format);
	}
	
	/**
	 * 计算两个日期相差天数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public  static int numDays(String startTime ,String endTime){
		DateTime startDateTime  = new DateTime(startTime);
		DateTime endDateTime = new DateTime(endTime);
		return startDateTime.numDaysFrom(endDateTime);
	}
	
	/**
	 * 获取两个日期中的天数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> daysBetweenTwoDay(String startTime,String endTime){
		List<String> list = new ArrayList<String>();
		DateTime startDateTime  = new DateTime(startTime);
		int numDays = numDays(startTime, endTime);
		for(int i=0;i<=numDays;i++){
			DateTime currentDay = startDateTime.plusDays(i);
			if (isWeek(currentDay)) {
				continue;
			}	
			list.add(currentDay.format(DATE_FORMAT_DAY_DATE4J));
		}
		return list;
	}
	
	/**
	 * 判断是否为周末
	 * @param currentDay
	 * @return
	 */
	public static boolean isWeek(String currentDay){
		DateTime currentDateTime = new DateTime(currentDay);
		return isWeek(currentDateTime);
	}
	
	public static boolean isWeek(DateTime currentDay){
		int dayNum = currentDay.getWeekDay();
		return (dayNum==1 || dayNum==7 ) ? true:false;
	}
	
	public static void main(String[] args) {
		String start = "2014-12-01";
		String end = "2014-12-05";
		String day = "2014-12-06";
		String day2 = "2014-12-07";
		isWeek(day);
		isWeek(day2);
		isWeek(start);
	}
	
	/**
	 * 判断签到签�?时间是否正确
	 * @param standard 基准时间
	 * @param now 当时时间
	 * @return true : 时间超出范围
	 */
	public static boolean checkTime(DateTime standard,DateTime now){
		DateTime up = standard.plus(0, 0, 0, 1, 0, 0, 0, DateTime.DayOverflow.LastDay);
		DateTime down = standard.minus(0, 0, 0, 1, 0, 0, 0, DateTime.DayOverflow.LastDay);
		return now.gt(up) || now.lt(down);
	}
	
	/**
	 * 根据月份获取当月的开始时间及结束时间
	 * @param monthStr
	 * @return
	 */
	public static Map<String, String> getMonth(String monthStr){
		String startTime = null;
		String endTime = null;
		DateTime now = DateTime.today(TimeZone.getDefault());
		String t_month = now.format(DATE_FORMAT_MONTH_DATE4J);  //当月
		if (!"".equals(monthStr)) {  //传上来的不是当月
			t_month = formatMonth(monthStr);  
		}
		startTime = t_month + "-01";
		DateTime month = new DateTime(startTime);
		endTime = month.getEndOfMonth().format(DATE_FORMAT_DAY_DATE4J);
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}
	
	/**
	 * 格式 2012-1-1 --> 2012-01-01
	 * @param month
	 * @return
	 */
	public static String formatMonth(String month){
		if (month.length()==7) {
			return month;
		}
		String[] ss = month.split("-");
		return ss[0]+"-0"+ss[1];
	}
	
}
