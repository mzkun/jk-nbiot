<%@ page language="java" contentType="text/html; charset=utf-8"   isELIgnored="false"  pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <link href="css/alogin.css" rel="stylesheet" type="text/css" /> -->
<title>用户登录</title>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.3/themes/icon.css">
<script type="text/javascript" src="js/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	function loadimage(){
		document.getElementById("randImage").src="images/image.jsp?"+Math.random();
	}
</script>
</head>
<body class="easyui-layout" style="background-color: black">
<div id="w" class="easyui-window" title="登录" modal="true" collapsible="false" minimizable="false"maximizable="false" closable="false" draggable="false" resizable="false" style="width: 500px; height: 300px; padding: 10px;">
        <form id="form1" action="userLogin?flag=1" method="post">
        <table style="width: 100%" cellpadding="5" border="0">
            <tr>
                <td colspan="2">
                	<h2><b><i>金卡NB-IOT平台</i></b></h2> 
                </td>
            </tr>
            <tr>
                <td align="right" style="width: 130px">
                 	用户：
                </td>
                <td>
                	<input id="userName" name="userName" type="text" class="txt" />
                </td>
            </tr>
            <tr>
                <td align="right">
                   	 密码：
                </td>
                <td>
                    <input id="password" name="password" type="password" class="txt" onkeydown= "if(event.keyCode==13){document.getElementById('form1').submit();}"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <a class="easyui-linkbutton" onclick="document.getElementById('form1').submit();">登录</a>
                </td>
            </tr>
            <tr>
            	<td></td>
            	<td>
            		<label style="color: red;">${error }</label> 
            	</td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>