<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>

<title>NB-IOT</title>

<style type="text/css">
/**Head**/
.header {
	width: 100%;
	height: 45px;
	background-color: deepskyblue;
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
		$("[name=aMenu]").click(function(){
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
					<a href="#" class="easyui-menubutton" data-options="menu:'#sysMenu'" title="系统">系统</a>
					<div id="sysMenu" style="width:150px;display:none" >
						<div name="aMenu" data-options="iconCls:'icon-undo'" url="" title="角色管理">角色管理</div>
						<div name="aMenu" data-options="iconCls:'icon-undo'" url="<%=path%>/user/page" title="用户管理">用户管理</div>
					</div>

					<a href="#" class="easyui-menubutton" data-options="menu:'#deviceMenu'" title="设备">设备</a>
					<div id="deviceMenu" style="width:150px;display:none">
						<div name="aMenu" data-options="iconCls:'icon-undo'" url="<%=path%>/deviceAction/device" title="设备管理">设备管理</div>
					</div>

					<a href="#" class="easyui-menubutton" data-options="menu:'#platformMenu'" title="平台">平台</a>
					<div id="platformMenu" style="width:150px;display:none">
						<div name="aMenu" data-options="iconCls:'icon-undo'" url="platform_manager.jsp" title="平台管理">平台管理</div>
					</div>

					<a href="#" class="easyui-menubutton" data-options="menu:'#urlmMenu'" title="地址">地址</a>
					<div id="urlmMenu" style="width:150px;display:none">
						<div name="aMenu" data-options="iconCls:'icon-undo'" url="url_manager.jsp" title="地址管理">地址管理</div>
					</div>
					<%--<a href="#" name="aMenu" class="easyui-linkbutton" data-options="plain:true" url="<%=path%>/deviceAction/device" title="设备管理">设备管理</a>--%>
					<%--<a href="#" name="aMenu"  class="easyui-linkbutton" data-options="plain:true" url="platform_manager.jsp" title="平台管理">平台管理 </a>--%>
					<%--<a href="#" name="aMenu"  class="easyui-linkbutton" data-options="plain:true" url="url_manager.jsp" title="地址管理">地址管理 </a>--%>
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