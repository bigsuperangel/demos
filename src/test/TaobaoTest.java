package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
/**
 * @ClassName: Taobao
 * @Description: "一键淘"
 * @author kaedeen.com
 * @date 2013-11-13 下午1:19:28
 * 
 */
public class TaobaoTest {
     
 
    private static String loginUrl = "https://login.taobao.com/member/login.jhtml";
 
    private static String tbToken = null;// 淘宝领金币使用的token
 
    private static DefaultHttpClient httpclient = null;// HttpClient对象
 
    private static HttpResponse response = null;
 
    private static  String userName = "锐秀轩";// 用户名
 
    private static String passWord = "login163";// 密码明文
 
    /**
     * 构造函数
     * 
     * @param userName
     * @param passWord
     */
    public TaobaoTest(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
 
    /**
     * 登陆淘宝
     * 
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public boolean login()  {
 
        if (null != httpclient) {
            return true;
        }
 
        httpclient = new DefaultHttpClient();
        // 设定cookie策略
        HttpClientParams.setCookiePolicy(httpclient.getParams(),
                CookiePolicy.BROWSER_COMPATIBILITY);
        // 登陆使用的表单数据
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
        loginParams.add(new BasicNameValuePair("TPL_username", userName));
        loginParams.add(new BasicNameValuePair("TPL_password", passWord));
         
        //登陆post请求
        HttpPost loginPost = new HttpPost(loginUrl);
        loginPost.addHeader("Referer", loginUrl);
        loginPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        loginPost.addHeader("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2; Tablet PC 2.0)");
        loginPost.addHeader("Host", "login.taobao.com");
         
        try {
             
            loginPost.setEntity(new UrlEncodedFormEntity(loginParams,  HTTP.UTF_8));
             
            //获取登陆应答内容
            response = httpclient.execute(loginPost);
             
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                String redirectUrl=getRedirectUrl();
                if(!"".equals(redirectUrl)){
                    //有重定向说明成功了,获取token
                    getTbToken(redirectUrl);
                }else{
                    System.out.println("登陆请求出错，重定向失败！");
                    return false;
                }
            }else{
                System.out.println("登陆请求出错，post返回状态:"+response.getStatusLine().getStatusCode());
                return false;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            loginPost.abort();
        }
         
        return true;
    }
     
    /**
     * 领取每日金币
     */
    @SuppressWarnings("rawtypes")
    public boolean getEveryDayCoins(){
         
        boolean flag=false;
         
        long t=System.currentTimeMillis();
        long enter_time=t-12345;
         
        String gainCoinUrl="http://vip.taobao.com/home/grant_everyday_coin.htm?t="+String.valueOf(t)
                +"&_tb_token_="+tbToken+"&checkCode=null" +
                "&enter_time="+String.valueOf(enter_time);
         
        HttpGet gainCoinGet1 =new HttpGet(gainCoinUrl);
        HttpResponse gainCoinResponse1;
        try {
            gainCoinResponse1 = httpclient.execute(gainCoinGet1);
            HttpEntity httpEntity = gainCoinResponse1.getEntity(); 
            String responseJsonStr = EntityUtils.toString(httpEntity);//取出应答字符串 
            System.out.println("领取金币应答字符串："+responseJsonStr);
           /* Map map =new Gson().fromJson(responseJsonStr, new TypeToken<Map<String, Object>>() {  
            }.getType());
            int code=(Integer)map.get("code");
            int daysTomorrow=(Integer)map.get("daysTomorrow");
            String coinTomorrow=(String) map.get("coinTomorrow");
            int coinNew=(Integer) map.get("coinNew");
            int coinOld=(Integer) map.get("coinOld");
            int coinGot=coinNew-coinOld;
             
            if(1==code){
                System.out.println("成功领取"+coinGot+"个淘金币，已连领"+daysTomorrow+"天，当前金币数量"+coinNew+"，明天可领"+coinTomorrow);
                flag=true;
            }else if(4==code){
                System.out.println("哦? 需要输入验证码，领个淘金币还这么麻烦！");
            }else if(5==code){
                System.out.println("验证码错误！");
            }else if(6==code){
                System.out.println("这叫神马逻辑，有5个好友的用户才能天天领金币，当前淘金币数量"+coinNew);
            }else if(2==code){
                System.out.println("今天运气不错，已经领了");
                flag=true;
            }else{
                System.out.println("没见过这个code，问问淘宝客服？");
                flag=true;
            }
             
            //以防万一，再执行一次.....
            gainCoinResponse1 = httpclient.execute(gainCoinGet1);*/
             
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            gainCoinGet1.abort();
        }
         
        return flag;
    }
     
    /**
     * 获取淘宝重定向url
     * @return
     */
	private String getRedirectUrl(){
        String redirectUrl="";
        HttpEntity resEntity =  response.getEntity();
        try {
            String bufferPageHtml=EntityUtils.toString(resEntity, HTTP.UTF_8);
            System.out.println(bufferPageHtml);
            Pattern pattern1 = Pattern.compile("gotoURL: = \"(.*)\",");
            Matcher m1 = pattern1.matcher(bufferPageHtml);
            if (m1.find()) {
                redirectUrl=m1.group(1);
                System.out.println("redirectUrl:"+redirectUrl);
            } else {
                System.out.println("获取redirectUrl失败！");
            }
           
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return redirectUrl;
    }
     
    /**
     * 获取淘宝登陆令牌
     * 可以使用两种方式
     * 1.jsoup解析网页获取
     * 2.从httpclient对象的cookie中获取
     * @param redirectUrl
     */
    private void getTbToken(String redirectUrl){
         
        HttpGet itaobaoGet =new HttpGet(redirectUrl);
        try {
            httpclient.execute(itaobaoGet); 
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            itaobaoGet.abort();
        }
         
        CookieStore cookiestore=httpclient.getCookieStore();
        List<Cookie> cookies=cookiestore.getCookies();
            if (cookies.isEmpty()) {    
                System.out.println("cookies is null!");    
            } else {    
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie cookie=cookies.get(i);
                    //System.out.println( cookies.get(i).toString());  
                    if(cookie.getName().equals("_tb_token_")){
                        tbToken=cookie.getValue();
                        System.out.println("淘宝令牌:"+tbToken);
                    }
                }    
            }
    }
    
    public static void main(String[] args) {
    	TaobaoTest taobao=new TaobaoTest(userName,passWord);
        taobao.login();
//        taobao.getEveryDayCoins();
	}
 
}