$(function() {
	$('#admin_url_datagrid').datagrid({
		url : 'urlAction/getUrlList',
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
			title : '平台',
			field : 'platformDes',
			width : 150
		}, {
			title : '功能码',
			field : 'fun_code',
			width : 80
		}, {
			title : '功能描述',
			field : 'fun_desc',
			width : 80
		}, {
			title : '访问地址',
			field : 'url',
			width : 400
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
		toolbar : '#admin_url_toolbar'
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
	$('#admin_url_datagrid').datagrid('load',$("#searchForm").serializeJson());
}

var flag = true;
//增加url
function urlAdd() {
	$('<div/>').dialog({
		id : 'addDialog',
		href : 'jsp/urlAdd.jsp',
		width : 400,
		height : 200,
		modal : true,
		title : '增加地址',
		buttons : [ {
			text : '确定',
			iconCls : 'icon-add',
			handler : function() {
				if(flag==true){
					flag = false;
					var d = $(this).closest('.window-body');
					
					$('#index_url_register').form('submit', {
						url : 'urlAction/addUrl',
						success : function(result) {
							$("#addDialog").dialog('destroy');
							$('#admin_url_datagrid').datagrid("load");
							$.messager.show({
								title : '提示',
								msg : result
							});
							flag = true;
						},
						error:function(){
							flag = true;
						}
					});
				}
				
			}
		} ],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}

function edit() {
	var platformDes = null;
	var fun_code = null;
	var fun_desc = null;
	var url = null;
	var id = null;
	var selectedRows = $("#admin_url_datagrid").datagrid('getSelections');
	//console.info(selectedRows.length);
	if (selectedRows.length != 1) {
		$.messager.alert({
			title : '提示',
			msg : '请选中一行进行设置!'
		})
		$('#admin_url_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		return;
	} else {
		platformDes = selectedRows[0].platformDes;
		fun_code = selectedRows[0].fun_code;
		fun_desc = selectedRows[0].fun_desc;
		url = selectedRows[0].url;
		id = selectedRows[0].id;
	}
	
	$('<div/>').dialog({
		href : 'jsp/editUrl.jsp?platformDes='+platformDes +'&fun_code=' + fun_code + '&fun_desc=' + fun_desc + '&url=' + url + '&id=' + id,
		id : 'editUrl',
		width : 600,
		height : 220,
		modal : true,
		title : '修改地址',
		buttons : [ {
			text : '确认',
			iconCls : 'icon-ok',
			handler : function() {
				$('#admin_editUrl_Form').form('submit', {
					url : 'urlAction/editUrl',
					success : function(result) {
						$("#editUrl").dialog('destroy');
						$('#admin_url_datagrid').datagrid('reload');
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
		},
		onLoad:function(){
			$("input[name=platformDes]").val(platformDes);
			$("input[name=fun_code]").val(fun_code);
			$("input[name=fun_desc]").val(fun_desc);
			$("input[name=url]").val(url);
			$("input[name=id]").val(id);
			//alert("ddd");
		}
	});
}
