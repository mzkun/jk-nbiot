<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="meta2.jsp"%>
<script type="text/javascript" src="js/url_manager.js" charset="utf-8"></script>
<title>NB-IOT</title>
</head>
<body class="easyui-layout">
	<div region="north" style="padding: 5px 5px">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>平台:</td>
					<td>
						<select id="platform" style="width: 170px" name="platform" editable="false" class="easyui-combobox" url="platformAction/getPlatformSelect" valueField="code" textField="platformDes" >
						</select>
					</td>
					<td>
						<a href="javascript:void(0);" onclick="searchFun();"  class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center">
		<table id="admin_url_datagrid"></table>
		<div id="admin_url_toolbar" style="display: none;clear: both;">
			<a href="javascript:void(0);" onclick="urlAdd();" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" style="float: left;">新增</a>
			<a href="javascript:void(0);" onclick="edit();" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" >更新</a>
			<div class="datagrid-btn-separator"></div>
		</div>
		<div style="display: none;clear: both;"></div>
	</div>
	
</body>
</html>