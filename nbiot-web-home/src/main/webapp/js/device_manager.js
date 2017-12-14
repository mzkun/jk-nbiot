$(function() {
	$('#admin_device_datagrid').datagrid({
		url : 'deviceAction/getDeviceList',
		fit : true,
		fitColumns : true,
		border : false,
		pagination : true,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : true,
//		sortName : 'gmt_create',
//		sortOrder : 'asc',
		//checkOnSelect : false,
//		selectOnCheck : false,
//		nowrap : false,
		columns : [ [ {
			title : '编号',
			field : 'id',
			width : 150,
			checkbox : true
		}, {
			title : '设备id',
			field : 'deviceId',
			width : 220
		}, {
			title : 'IMEI号',
			field : 'imei',
			width : 150
		}, {
			title : '所属平台',
			field : 'platformDes',
			width : 150
		}, {
			title : 'psk',
			field : 'psk',
			width : 150
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
		toolbar : '#admin_device_toolbar'
	});
});

function searchFun() {
	$('#admin_device_datagrid').datagrid('load',$("#searchForm").serializeJson());
}

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

//注册设备
function admin_device_appendFun() {
	$('<div/>').dialog({
		id : 'registDialog',
		href : 'jsp/registerDevice.jsp',
		width : 400,
		height : 200,
		modal : true,
		title : '注册设备',
		buttons : [ {
			text : '注册',
			iconCls : 'icon-add',
			handler : function() {
				var button = this;
					var d = $(this).closest('.window-body');
					$('#index_device_register').form('submit', {
						url : 'deviceAction/registerDevice',
						
						onSubmit: function(){   //对表单进行校验，如果校验通过，则disable掉保存按钮，如果没有通过，则不用管。
                            var isValid = $(this).form('validate');
                            if (!isValid){
                                return isValid;
                            }else {
                                $(button).linkbutton('disable');
                                return isValid;    // return false will stop the form submission
                            }
						},
						success : function(result) {
							$(button).linkbutton('enable'); 
							$("#registDialog").dialog('destroy');
							$('#admin_device_datagrid').datagrid("load");
							$.messager.show({
								title : '提示',
								msg : result
							});
						},
					});
				}
		} ],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}
//设置设备信息
function admin_device_setDeviceInfo() {
	var selectedRows = $("#admin_device_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选中一行进行设置!'
		})
		$('#admin_device_datagrid').datagrid('uncheckAll').datagrid(
				'unselectAll').datagrid('clearSelections');
		return;
	}
	$('<div/>').dialog({
		href : 'jsp/setDeviceInfo.jsp?deviceId=' + selectedRows[0].deviceId + '&platform=' + selectedRows[0].platform,
		id : 'setDeviceInfo',
		width : 600,
		height : 220,
		modal : true,
		title : '设置设备信息',
		buttons : [ {
			text : '确认',
			iconCls : 'icon-ok',
			handler : function() {
				$('#admin_setDevice_Form').form('submit', {
					url : 'deviceAction/setDeviceInfo',
					success : function(result) {
						$("#setDeviceInfo").dialog('destroy');
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

//订阅设备变更
function admin_device_subscribeChange() {
	/*var selectedRows = $("#admin_device_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选中一行进行设置!'
		})
		$('#admin_device_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		return;
	}*/
	$('<div/>').dialog({
		href : 'jsp/subscribeChange.jsp',
		id : 'subscribeChange',
		width : 400,
		height : 180,
		modal : true,
		title : '订阅设备变更',
		buttons : [ {
			text : '确认',
			iconCls : 'icon-ok',
			handler : function() {
				$('#admin_subscribeChange_Form').form('submit', {
					url : 'deviceAction/subscribeChange',
					success : function(result) {
						$("#subscribeChange").dialog('destroy');
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

//删除设备
function admin_device_removeDevice() {
	
	var selectedRows = $("#admin_device_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选择一条进行删除!'
		})
		$('#admin_device_datagrid').datagrid('uncheckAll').datagrid(
				'unselectAll').datagrid('clearSelections');
		return;
	}
	var deviceId=selectedRows[0].deviceId;
	var platform=selectedRows[0].platform;
	$.messager.confirm('确认删除', '确认删除设备?', function(r){
		if (r){
			$.ajax({
				url : "deviceAction/deleteDevice?deviceId="+deviceId+'&platform='+platform,
				success : function(data) {
					$.messager.alert('提示', data);
					reloads();
				}
			});
		}
	});
	
	$('#admin_device_datagrid').datagrid('uncheckAll').datagrid(
	'unselectAll').datagrid('clearSelections');
}

//查询设备激活状态
function search_device_status(){
	var selectedRows = $("#admin_device_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选择一条记录进行查询!'
		})
		$('#admin_device_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		return;
	}
	var deviceId=selectedRows[0].deviceId;
	var platform=selectedRows[0].platform;
	$.ajax({
		type : "GET",
		url : "deviceAction/searchDeviceStatus?deviceId="+deviceId+'&platform='+platform,
		success : function(data) {
			$.messager.alert('提示', data);
			reloads();
		}
	});
	
	$('#admin_device_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
}

function reloads(){
    $('#admin_device_datagrid').datagrid('reload');//刷新
}
//登录IOT平台获取accessToken
function admin_device_loginIotFun() {
	$.ajax({
		type : "POST",
		url : "deviceAction/loginIot",
		success : function(data) {
			$.messager.alert('提示', data);
		}
	});
}

function admin_device_capabilities() {
    var selectedRows = $("#admin_device_datagrid").datagrid('getSelections');
    //console.info(selectedRows.length);
    if (selectedRows.length != 1) {
        $.messager.alert({
            title : '提示',
            msg : '请选择一条记录进行查询!'
        })
        $('#admin_device_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
        return;
    }
    var deviceId=selectedRows[0].deviceId;
    var platform=selectedRows[0].platform;
    $.ajax({
        type : "GET",
        url : "deviceAction/deviceCapabilities?deviceId="+deviceId+'&platform='+platform,
        success : function(data) {
            $.messager.alert('提示', data);
            reloads();
        }
    });

    $('#admin_device_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
}

//设备批量导入 
function admin_device_add_batch() {
	$('<div/>').dialog({
		id : 'registBatchDialog',
		href : 'jsp/registerBatch.jsp',
		width : 400,
		height : 200,
		modal : true,
		title : '批量导入',
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				var button = this;
					var d = $(this).closest('.window-body');
					$('#index_device_batch_register').form('submit', {
						url : 'deviceAction/registerBatch',
						
						onSubmit: function(){   //对表单进行校验，如果校验通过，则disable掉保存按钮，如果没有通过，则不用管。
                            var isValid = $(this).form('validate');
                            if (!isValid){
                                return isValid;
                            }else {
                                $(button).linkbutton('disable');
                                return isValid;    // return false will stop the form submission
                            }
						},
						success : function(result) {
							$(button).linkbutton('enable'); 
							$("#registBatchDialog").dialog('destroy');
							$('#admin_device_datagrid').datagrid("load");
							$.messager.show({
								title : '提示',
								msg : result
							});
						},
					});
				}
		} ],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}
