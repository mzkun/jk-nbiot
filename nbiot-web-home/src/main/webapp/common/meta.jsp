<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- [jQuery]  -->
<script type="text/javascript" src="<%=path %>/js/easyui/jquery-1.8.0.min.js"></script>
<!-- [EasyUI] -->
<script type="text/javascript" src="<%=path %>/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui/demo/demo.css">

<!-- [扩展JS] --> 
<script type="text/javascript" src="<%=path %>/js/extJs.js"></script>
<script type="text/javascript" src="<%=path %>/js/common.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css">
  
<script type="text/javascript">
var path = "${path}";
</script>

