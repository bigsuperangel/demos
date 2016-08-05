package util;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 通用转换json�?
 * @author linyu
 * @date 2014-8-11
 */
public final class JSON {
	private static final String DATEPATTERN = "yyyy-MM-dd hh:mm:ss";

	  private JSON()
	  {
	    throw new UnsupportedOperationException("非法构�? JSON 对象");
	  }
	  
	  public static String toJson(Object obj){
		  if (obj==null) {
			  throw new IllegalArgumentException("object 参数异常");
		  }
		  return toJson(obj,null);
		}
	  
	  public static String toJson(Object obj , String datePattern){
		  if (datePattern==null) {
			datePattern = JSON.DATEPATTERN;
		}
		  return new GsonBuilder().setDateFormat(datePattern).create().toJson(obj);
	  }
	  
	  public static<T> T fromJson(String jsonStr,Class<?> type){
		  return (T) fromJson(jsonStr, type,null);
	  }
	  
	  public static<T> T fromJson(String jsonStr,Type type){
	        Object obj = null;  
	        Gson gson = new GsonBuilder()  
	                .setDateFormat(JSON.DATEPATTERN).create();  
	        if (gson != null) {  
	            obj = gson.fromJson(jsonStr, type);  
	        }  
		  return (T)obj;
	  }
	  
	    public static <T> T fromJson(String jsonStr, Class<T> cl,  String pattern) {  
	    	if (pattern ==null) {
				pattern = JSON.DATEPATTERN;
			}
	        Object obj = null;  
	        Gson gson = new GsonBuilder()  
	                .setDateFormat(pattern).create();  
	        if (gson != null) {  
	            obj = gson.fromJson(jsonStr, cl);  
	        }  
	        return (T) obj;  
	    }  
	    
	    public static Object getJsonValue(String jsonStr, String key) {  
	        Object rulsObj = null;  
	        Map<?, ?> rulsMap = fromJson(jsonStr,Map.class); 
	        if (rulsMap != null && rulsMap.size() > 0) {  
	            rulsObj = rulsMap.get(key);  
	        }  
	        return rulsObj;  
	    }  
	    
	    public static void main(String[] args) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dfd", 223);
			map.put("id", 3);
			map.put("date",new Date());
			String jsonStr = toJson(map);
			System.out.println(getJsonValue(jsonStr, "date"));
		}
}
