<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div align="center">
	<form id="admin_setDevice_Form" method="post">
		<table class="tableForm">
			<tr>
				<th align="right">deviceId</th>
				<td><input name="deviceId" readonly="readonly" style="width: 260px" value="<%=request.getParameter("deviceId")%>" /></td>
			</tr>
			<tr>
				<th align="right">厂商ID(manufacturerId)</th>
				<td><input name="manufacturerId" style="width: 260px" data-options="required:true" class="easyui-validatebox"  value="JK-300349" readonly="readonly"/></td>
			</tr>
			<tr>
				<th align="right">设备类型(deviceType)</th>
				<td><input name="deviceType" style="width: 260px" data-options="required:true" class="easyui-validatebox"  value="GasMeter" readonly="readonly"/></td>
			</tr>
			<tr>
				<th align="right">协议类型(protocolType)</th>
				<td><input name="protocolType" style="width: 260px" data-options="required:true" class="easyui-validatebox"  value="CoAP" readonly="readonly"/></td>
			</tr>
			<tr>
				<th align="right">设备型号(model)</th>
				<td><input name="model" style="width: 260px" data-options="required:true" class="easyui-validatebox"  value="JW-G2.5" readonly="readonly"/></td>
			</tr>
			<input name="platform" type="hidden" value="<%=request.getParameter("platform")%>"  />			
			<!-- <tr>
				<th align="right">厂商名(manufacturerName)</th>
				<td><input name="manufacturerName" style="width: 260px" class="easyui-validatebox"  value="" /></td>
			</tr>
			<tr>
				<th align="right">应用ID(appId)</th>
				<td><input name="appId" style="width: 260px" class="easyui-validatebox"  value="" /></td>
			</tr>
			
			<tr>
				<th align="right">设备名称(name)</th>
				<td><input name="name" style="width: 260px" class="easyui-validatebox"  value="" /></td>
			</tr>
			<tr>
				<th align="right">设备冻结状态(mute)</th>
				<td><select name="mute" style="width: 265px">
					<option value="">--请选择--</option>
					<option value="true">是</option>
					<option value="false">否</option>
				</select>
			</tr>
			<tr>
				<th align="right">终端用户(endUser)</th>
				<td><input name="endUser" style="width: 260px" class="easyui-validatebox"  value="" /></td>
			</tr>
			<tr>
				<th align="right">设备位置(location)</th>
				<td><input name="location" style="width: 260px" class="easyui-validatebox"  value="" /></td>
			</tr> -->
			
		</table>
	</form>
</div>