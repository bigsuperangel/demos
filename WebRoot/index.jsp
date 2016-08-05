<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function obj(name,id){
	this.name=name;
	this.id = id;
}
var key = 11;
var a = new obj('11','11');
var b = new obj('22','22');
var c = new obj('11','11');
var arr = [];
arr.push(a);
arr.push(b);


    function nodup(cs){
			var len = cs.length, c, i, r = cs, cj, ri = -1;

        var d = ++key;
        cs[0]._nodup = d;
        for(i = 1; c = cs[i]; i++){
					console.info(c._nodup);
            if(c._nodup != d){
                c._nodup = d;
            }else{
                r = [];
                for(var j = 0; j < i; j++){
									console.info(cs[j]);
                    r[++ri] = cs[j];
                }
                for(j = i+1; cj = cs[j]; j++){
                    if(cj._nodup != d){
                        cj._nodup = d;
                        r[++ri] = cj;
                    }
                }
                return r;
            }
        }
        return r;
    }


var k = nodup(arr);
	</script>
  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
