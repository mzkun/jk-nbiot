<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="index_platform_register" method="post">
		<table class="tableForm">
			<tr>
				<!-- <th>编号</th>
				<td><input name="id" readonly="readonly" />
				</td> -->
				<br>
				<td align="right">平台编码</td>
				<td><input name="code" class="easyui-validatebox" maxlength="4" data-options="validType:'length[1,4]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">平台</td>
				<td><input name="platform" class="easyui-validatebox" maxlength="20" data-options="validType:'length[1,20]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">位置</td>
				<td><input name="location" class="easyui-validatebox" maxlength="20" data-options="validType:'length[1,20]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">应用Id</td>
				<td><input name="app_key" class="easyui-validatebox" maxlength="50" data-options="validType:'length[1,50]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">密钥</td>
				<td><input name="secret" class="easyui-validatebox" maxlength="50" data-options="validType:'length[1,50]',required:true" />
				</td>
			</tr>
		</table>
	</form>
</div>
