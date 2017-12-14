<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="index_device_batch_register" method="post" enctype="multipart/form-data">
		<br>
		<table class="tableForm">
			<tr>
				<td align="right">所属平台</td>
				<td>
					<select id="source" style="width: 170px" name="source" required="true" editable="false" class="easyui-combobox" url="platformAction/getPlatformList" valueField="code" textField="platformDes" >
					</select>
				</td>
			</tr>
			<tr>
				<br>
			</tr>
			<tr>
				<td align="right">文&nbsp&nbsp件</td>
				<td>
					<input class="easyui-filebox" labelPosition="top" name="file" data-options="prompt:'选择一个文件...'" style="width:100%">
					<!-- <input type="file" name="file" style="width: 250px"> -->
				</td>
			</tr>
			<tr>
				<td colspan="2" align="left" style="font-size:2px;color:red">确保导入文件中每行一个imei号！！</td>
			</tr>
		</table>
	</form>
	
	<!-- <form action="dynamicFields.action?method=uploadFile" method="post" enctype="multipart/form-data"><input type="file" name="myfile" id="myfile" value="" /><br/><input type="submit" value="确认提交"></form> -->
</div>
