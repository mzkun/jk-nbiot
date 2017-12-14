<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="index_device_register" method="post">
		<table class="tableForm">
			<tr>
				<!-- <th>编号</th>
				<td><input name="id" readonly="readonly" />
				</td> -->
				<br>
				<td align="right">IMEI号</td>
				<td><input name="imei" class="easyui-validatebox" data-options="validType:'length[10,18]',required:true,prompt:'输入IMEI号'" />
				</td>
			</tr>
<!-- 			<tr>
				<td align="right">IMEI号</td>
				<td><input name="nodeId" class="easyui-validatebox" data-options="validType:'length[10,18]',required:true" />
				</td>
			</tr> -->
			
			<tr>
				<td align="right">终端用户</td>
				<td><input name="endUserId" class="easyui-validatebox" data-options="required:false" />
				</td>
			</tr>
<!-- 			<tr>
				<td align="right">超时时间（填0）</td>
				<td><input name="timeout" class="easyui-numberbox" value="0" data-options="required:false" readonly="true"/>
				</td>
			</tr> -->
			
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
