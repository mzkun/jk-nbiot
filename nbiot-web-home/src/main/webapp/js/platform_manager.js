$(function() {
	$('#admin_platform_datagrid').datagrid({
		url : 'platformAction/getPlatformPage',
		fit : true,
		fitColumns : true,
		border : true,
		pagination : true,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : true,
		//sortName : 'createtime',
		//sortOrder : 'desc',
		//checkOnSelect : false,
		//selectOnCheck : false,
		//nowrap : false,
		columns : [ [ {
			title : '编号',
			field : 'id',
			width : 150,
			checkbox : true
		}, {
			title : '平台编码',
			field : 'code',
			width : 100
		}, {
			title : '平台',
			field : 'platform',
			width : 100
		}, {
			title : '位置',
			field : 'location',
			width : 100
		}, {
			title : 'accessToken',
			field : 'accessToken',
			width : 220
		}, {
			title : '应用Id',
			field : 'app_key',
			width : 220
		}, {
			title : '密钥',
			field : 'secret',
			width : 220
		}, {
			title : '创建时间',
			field : 'gmt_create',
			width : 150,
			formatter : function(val) {
				return jsonTimeStamp(val);
			}
		},/* {
			title : '修改时间',
			field : 'gmt_modified',
			width : 150,
			formatter : function(val) {
				return jsonTimeStamp(val);
			}
		}*/ ] ],
		toolbar : '#admin_platform_toolbar'
	});
});
//格式化时间
function jsonTimeStamp(milliseconds) {
	if (milliseconds != "" && milliseconds != null && milliseconds != "null") {
		var datetime = new Date();
		datetime.setTime(milliseconds);
		var year = datetime.getFullYear();
		var month = datetime.getMonth() + 1 < 10 ? "0"
				+ (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
				: datetime.getDate();
		var hour = datetime.getHours() < 10 ? "0" + datetime.getHours()
				: datetime.getHours();
		var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
				: datetime.getMinutes();
		var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
				: datetime.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute
				+ ":" + second;
	} else {
		return "";
	}

}

function searchFun() {
	$('#admin_platform_datagrid').datagrid('load',$("#searchForm").serializeJson());
}

//增加平台
function platformAdd() {
	$('<div/>').dialog({
		id : 'addDialog',
		href : 'jsp/platformAdd.jsp',
		width : 450,
		height : 240,
		modal : true,
		title : '增加平台',
		buttons : [ {
			text : '确定',
			iconCls : 'icon-add',
			handler : function() {
				var d = $(this).closest('.window-body');
				$('#index_platform_register').form('submit', {
					url : 'platformAction/addPlatform',
					success : function(result) {
						$("#addDialog").dialog('destroy');
						$('#admin_platform_datagrid').datagrid("load");
						$.messager.show({
							title : '提示',
							msg : result
						});
					}
				});
			}
		} ],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}

function refreshToken() {
	var selectedRows = $("#admin_platform_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选择一条进行刷新!'
		})
		$('#admin_platform_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		return;
	}
	var code=selectedRows[0].code;
	$.ajax({
//		type : "POST",
		url : "deviceAction/loginIot?code="+code,
		success : function(data) {
			$.messager.alert('提示', data);
			$('#admin_platform_datagrid').datagrid('reload');
		}
	});
}