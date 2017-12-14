<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="meta.jsp"%>

<title>NB-IOT</title>

<style type="text/css">
/**Head**/
.header {
	width: 100%;
	height: 45px;
	background-color: #1B1B1B;
	border: none;
	color: white;
}

.header-left {
	width: 250px
}

.header-left img {
	padding-left: 20px;
	padding-right: 10px
}

.header-right {
	width: 45px;
	padding-right: 20px
}
</style>
<script type="text/javascript">
	$(function(){
		
		var tab = $('#showPanel');
		$("a[name=aMenu]").click(function(){
             var isExists = tab.tabs('exists', $(this).attr('title'));
             if (isExists) {
                 tab.tabs('select', $(this).attr('title'));
                 return;
             }

             tab.tabs('add', {
                 title: $(this).attr('title'),
                 closable: true,
                 border: false,
                 //href: $(this).attr('url')
                 content: "<iframe src='" + $(this).attr('url') + "' frameborder='0' style='border:0;width:100%;height:99.5%'></iframe>"
             });

		});
	});
</script>
</head>
<body class="easyui-layout">

	<div region="north" style="background-color: black;" border="false">
		<table class="header">
			<tr>
				<td class="header-left">
					<div style="cursor: pointer">
						<img src="images/favicon.png" alt="" /><span
							style="font-size: 18px">金卡NB-IOT平台</span>
					</div>
				</td>
				<td>
					<a href="#" name="aMenu" class="easyui-linkbutton" data-options="plain:true" url="device_manager.jsp" title="设备管理">设备管理</a> 
					<a href="#" name="aMenu"  class="easyui-linkbutton" data-options="plain:true" url="platform_manager.jsp" title="平台管理">平台管理 </a>
					<a href="#" name="aMenu"  class="easyui-linkbutton" data-options="plain:true" url="url_manager.jsp" title="地址管理">地址管理 </a>
				</td>
			</tr>
		</table>
	</div>

	<div region="center">
		<div id="showPanel" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="欢迎" style="background-color: white;">
				    <span style="color: black;font-size:24px;">  欢迎登录！！</span>
			</div>
		</div>
	</div>

</body>
</html>