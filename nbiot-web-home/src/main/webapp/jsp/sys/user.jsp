<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/global.jsp" %>
<html>
<head>
<%@ include file="/common/meta2.jsp"%>
<script type="text/javascript" src="${path}/js/sys/user.js" charset="utf-8"></script>
<title>NB-IOT</title>
</head>
<body class="easyui-layout">
	 <div region="north" style="padding: 5px 5px">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>用户名</td>
					<td>
						<input type="text"  name="userNo"  style="width:150px;"/>
					</td>
					<td>  姓名</td>
					<td>
						<input type="text"  name="userName"  style="width:150px;"/>
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchFun();" style="width:60px">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="cleanFun();" style="width:60px">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div region="center">
		<table id="user_datagrid"></table>
		<div id="user_toolbar" style="display: none;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="userAdd()">增加</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="userEnable(true)">启用</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true" onclick="userEnable(false)">禁用</a>
			<%--<a href="javascript:void(0);" onclick="userAdd();" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" style="float: left;"></a>
			<div class="datagrid-btn-separator"></div>--%>
		</div>
	</div>
	
</body>
</html>