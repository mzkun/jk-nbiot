<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="admin_editUrl_Form" method="post">
		<table class="tableForm">
			<tr>
				<th align="right">平台</th>
				<td><input name="platformDes" readonly="readonly" style="width: 260px" /></td>
			</tr>
			<tr>
				<th align="right">功能码</th>
				<td><input name="fun_code" style="width: 260px" data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th align="right">功能描述</th>
				<td><input name="fun_desc" style="width: 260px" data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th align="right">访问地址</th>
				<td><input name="url" style="width: 260px" data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<input name="id" type="hidden" value="<%=request.getParameter("id")%>"  />			
		</table>
	</form>
</div>
