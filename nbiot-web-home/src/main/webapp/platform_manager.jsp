<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="meta2.jsp"%>
<script type="text/javascript" src="js/platform_manager.js" charset="utf-8"></script>
<title>NB-IOT</title>
</head>
<body class="easyui-layout">

	<div region="north" style="padding: 5px 5px">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>平台:</td>
					<td>
						<select id="code" style="width: 170px" name="code" editable="false" class="easyui-combobox" url="platformAction/getPlatformSelect" valueField="code" textField="platformDes" >
						</select>
					</td>
					<td>
						<a href="javascript:void(0);" onclick="searchFun();"  class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" >
		<table id="admin_platform_datagrid" border="true"></table>
		<div id="admin_platform_toolbar" style="display: none;">
			<a href="javascript:void(0);" onclick="platformAdd();" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" style="float: left;">新增</a>
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0);" onclick="refreshToken();" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true">刷新accessToken</a>
			<!-- <div class="datagrid-btn-separator"></div> -->
			<!-- <input id="admin_platform_searchbox" class="easyui-searchbox" style="width: 150px;"
				data-options="searcher:function(value,name){$('#admin_platform_datagrid').datagrid('load',{platform:value});},prompt:'输入平台名称'">
			</input>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="$('#admin_platform_datagrid').datagrid('load',{});$('#admin_platform_searchbox').searchbox('setValue','');">清空条件</a> -->
		</div>
		<div></div>
	</div>
</body>
</html>
