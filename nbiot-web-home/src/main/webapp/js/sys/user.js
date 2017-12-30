var dataGrid;
$(function() {
    dataGrid = $('#user_datagrid').datagrid({
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
			title : '用户名',
			field : 'userNo',
			width : 220
		}, {
			title : '姓名',
			field : 'userName',
			width : 150
		}, {
			title : '用户状态',
			field : 'status',
			width : 150,
            formatter : function(value, row, index) {
                if (value == '1') {
                    return '启用';
                } else {
                    return '<span style="color:red">禁用</span>';
                }
            }
		}, {
			title : '创建人',
			field : 'createId',
			width : 150
		}, {
			title : '创建时间',
			field : 'createTime',
			width : 150,
            formatter : function(value, row, index) {
                if (value != null && value.length > 20) {
                    return value.substring(0, 19);
                }
            }
		},/* {
			title : '修改时间',
			field : 'gmt_modified',
			width : 150,
			formatter : function(val) {
				return jsonTimeStamp(val);
			}
		}*/ ] ],
		toolbar : '#user_toolbar'
	});
});

function searchFun() {
	dataGrid.datagrid('options').url = path + '/user/userList';
	dataGrid.datagrid('load', $('#searchForm').form('serialize'));
	dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	// $('#user_datagrid').datagrid('load',$("#searchForm").serializeJson());
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

function cleanFun() {
    $('#searchForm input').val('');
    // dataGrid.datagrid('load', {});
}

function reloads(){
    $('#user_datagrid').datagrid('reload');//刷新
}
//增加平台
function userAdd() {
    $('<div/>').dialog({
        id : 'addUserDialog',
        href : path+'/jsp/sys/userAdd.jsp',
        width : 450,
        height : 240,
        modal : true,
        title : '增加用户',
        buttons : [ {
            text : '确定',
            iconCls : 'icon-add',
            handler : function() {
                var d = $(this).closest('.window-body');
                $('#userAddForm').form('submit', {
                    url : path + '/user/userAdd',
                    success : function(result) {
                        $("#addUserDialog").dialog('destroy');
                        $('#user_datagrid').datagrid("load");
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

function userEnable(isDisable) {

    var rows = dataGrid.datagrid('getSelections');
    //console.info(selectedRows.length);
    if (rows.length != 1) {
        alertMessage('请选择一项', 'error');
        dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
        return;
    }

    var id = rows[0].id;
    var url = path + '/user/enable';
    var tips = '是否要启用当前选中用户？';
    if (!isDisable) {
        url = path + '/user/unable';
        tips = '是否要禁用当前选中用户？';
    }
    alertConfirm(tips, function() {
        $.ajax({
            type : 'post',
            url : url,
            data : {id:id},
            dataType : 'json',
            success : function(resp) {
                if (!resp.result) {
                    alertMessage(resp.message, 'error');
                    return;
                }
                alertMessage(resp.message, 'info');
                dataGrid.datagrid('reload');
            }
        });
    })
    /*$.messager.alertMessage('确认删除', '确认删除设备?', function(r){
        if (r){
            $.ajax({
                url : path + "user/enable?id=" + id,
                success : function(data) {
                    $.messager.alert('提示', data);
                    reloads();
                }
            });
        }
    });*/
}