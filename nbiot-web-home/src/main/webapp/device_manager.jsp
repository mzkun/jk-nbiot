<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="meta2.jsp"%>
<script type="text/javascript" src="js/device_manager.js" charset="utf-8"></script>
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
					<td>IMEI号:</td>
					<td>
						<input id="imei" name="imei" class="easyui-textbox"/>
					</td>
					<td>
						<a href="javascript:void(0);" onclick="searchFun();"  class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div region="center">
		<table id="admin_device_datagrid"></table>
		<div id="admin_device_toolbar" style="display: none;">
			<a href="javascript:void(0);" onclick="admin_device_appendFun();" class="easyui-linkbutton" 
				data-options="iconCls:'icon-add',plain:true" style="float: left;">注册设备</a>
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0);" onclick="admin_device_setDeviceInfo();" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" style="float: left;">设置设备信息</a>
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0);" onclick="admin_device_subscribeChange();" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" style="float: left;">订阅设备变更</a>
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0);" onclick="search_device_status();" class="easyui-linkbutton"
				data-options="plain:true,iconCls:'icon-help'" style="float: left;">查询设备激活状态</a>
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0);" onclick="admin_device_removeDevice();" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true" style="float: left;">删除设备</a>
			<!-- <a href="javascript:void(0);" onclick="admin_device_capabilities();" class="easyui-linkbutton"
			   data-options="iconCls:'icon-help',plain:true" >查询设备能力</a> -->
			<a href="javascript:void(0);" onclick="admin_device_add_batch();" class="easyui-linkbutton"
				data-options="iconCls:'icon-redo',plain:true" >批量导入</a>
			<div class="datagrid-btn-separator"></div>
		</div>
	</div>
	
</body>
</html>