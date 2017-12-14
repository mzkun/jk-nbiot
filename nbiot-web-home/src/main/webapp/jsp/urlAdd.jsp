<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="index_url_register" method="post">
		<table class="tableForm">
			<tr>
				<!-- <th>编号</th>
				<td><input name="id" readonly="readonly" />
				</td> -->
				<br>
				<td align="right">功能码</td>
				<td><input name="fun_code" class="easyui-validatebox" maxlength="50" data-options="validType:'length[1,50]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">功能描述</td>
				<td><input name="fun_desc" class="easyui-validatebox" maxlength="20" data-options="validType:'length[1,20]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">地址</td>
				<td><input name="url" class="easyui-validatebox" maxlength="100" data-options="validType:'length[1,100]',required:true" />
				</td>
			</tr>
			<tr>
				<td align="right">所属平台</td>
				<td>
					<select id="source" style="width: 170px" name="source" required="true" editable="false" class="easyui-combobox" url="platformAction/getPlatformList" valueField="code" textField="platformDes" >
						
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>
