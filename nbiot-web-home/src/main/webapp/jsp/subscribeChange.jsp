<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="admin_subscribeChange_Form" method="post">
		<table class="tableForm">
			<tr>
				<th align="right">通知类型</th>
				<td>
					<select id="notifyType" name="notifyType" style="width: 100%; height: 23">
						<option value="bindDevice" selected>绑定设备</option>
						<option value="deviceDataChanged">设备数据上报</option>
						<option value="deviceDeleted">删除设备</option>
						<option value="commandRsp">命令响应</option>
						<option value="ruleEvent">规则事件</option>
						<option value="deviceDatasChanged">批量设备数据上报</option>
					</select> 
				<!-- <input name="notifyType" readonly="readonly" style="width: 260px" /> -->
				</td>
			</tr>
			<tr></tr>
			<tr>
				<th align="right">回调地址</th>
				<td>
					<input name="callBackUrl" style="width: 260px" data-options="required:true" class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<td align="right">所属平台</td>
				<td>
					<select id="source" style="width: 170px" name="source" class="easyui-combobox" url="platformAction/getPlatformList" valueField="code" textField="platformDes" >
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="left" style="font-size:2px;color:red">请谨慎操作，回调地址如果填写错误会导致通讯失败！！</td>
			</tr>
		</table>
	</form>
</div>