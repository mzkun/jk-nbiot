//菜单管理 start
function mmMenu(){	
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '菜单管理');
	if(isExists){
		tab.tabs('select', '菜单管理'); 
		return;
	}
	
	$.get("showpage.do?pageId=mmMenu", function(data){
		tab.tabs('add',{    
		    title:'菜单管理',    
		    content:data,    
		    closable:true,
		    id:'showmenu'
		});	
		var url = "menudata.do";
		loadPage("menuList",url);
	});
}



function addMenuWin(){
	$('#addMenuWin').dialog({    
	    title: "新增菜单",    
	    width: 400,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=addMenu',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveAndEditMenu();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addMenuWin').window('close'); }}],
	 	onLoad:function(){
	 		intitForm("addmenu.do");
	 		$('#menuTree').tree({    
	 		    url: "parenttree.do",
	 		    lines:true,
	 		   // checkbox:true,
	 			onSelect : function(node){
	 				$("#addMenu").find("#parentId").val(node.id);
	 				$("#addMenu").find("#parentName").val(node.text);
	 			}
	 		}); 
	    }		

	 }); 
}

function editMenuWin(){
	var row = $("#menuList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$('#addMenuWin').dialog({    
	    title: "编辑菜单",    
	    width: 400,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=addMenu',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveAndEditMenu();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addMenuWin').window('close'); }}],
	 	onLoad:function(){
	 		intitForm("editmenu.do");
	 		$("#addMenu").find("#winId").val(row.id);
	 		$("#addMenu").find("#winText").val(row.text); 			   			
	 		$("#addMenu").find("#winOrderId").val(row.orderId);
	 		$("#addMenu").find("#winAttributes").val(row.attributes);
	 		$("#addMenu").find("input[type=radio][value="+row.isDisplay+"]").attr("checked",'checked');
	 		$("#addMenu").find("#parentName").val(row.parentName);
	 		$("#addMenu").find("#parentId").val(row.parentId);
 			$('#menuTree').tree({    
	 		    url: "parenttree.do",
	 		    lines:true,
	 			onSelect : function(node){
	 				if(row.id == node.id){
	 					$.messager.alert('提示',"无法将自己作为父菜单");
	 					return;
	 				}
	 				$("#addMenu").find("#parentId").val(node.id);
	 				$("#addMenu").find("#parentName").val(node.text);
	 			}
	 		});
	    }		

	 }); 
}

function saveAndEditMenu(){	
	$("#addMenu").submit();	
}
//菜单管理 end

//角色管理 start
function mmRole(){
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '角色管理');
	if(isExists){
		tab.tabs('select', '角色管理'); 
		return;
	}
	$.get("showpage.do?pageId=mmRole", function(data){
		tab.tabs('add',{    
		    title:'角色管理',    
		    content:data,    
		    closable:true,
		    id:'mmRole'
		});	
		var url = "roledata.do";
		loadPage("roleList",url);
	});
}

function showRoleWin(operation){
	var title = "新增角色";
	var row = null;
	var roleId = "";
	if(operation == 'edit'){
		title="编辑角色";
		row = $("#roleList").datagrid("getSelected");
		if(null == row){
			$.messager.alert('提示','请选择一条记录');  
			return;
		}
		roleId = row.id;
	}
	$('#addRoleWin').dialog({    
	    title: title,    
	    width: 400,    
	    height: 550,  
	    modal: true,
	    href:'showpage.do?pageId=addRole',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveAndEditRole();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addRoleWin').window('close'); }}],
	 	onLoad:function(){	 		
	 		if(operation == 'edit'){
	 			
	 			$("#addRole").find("#roleName").val(row.roleName);
	 			$("#addRole").find("#roleId").val(row.id);	 			
	 		}
	 		$('#roleMenuTree').tree({    
	 		    url: "rolemenu.do?roleId="+roleId,
	 		    checkbox:true,
	 		    cascadeCheck:false,
	 		    lines:true
	 		}); 
	    }		

	 }); 
}

function saveAndEditRole(){
	$("#addRole").submit();	
}   

//角色管理 end

//用户管理 start
function mmUser(){
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '用户管理');
	if(isExists){
		tab.tabs('select', '用户管理'); 
		return;
	}
	$.get("showpage.do?pageId=mmUser", function(data){
		tab.tabs('add',{    
		    title:'用户管理',    
		    content:data,    
		    closable:true,
		    id:'mmUser'
		});	
		var url = "userdata.do";
		loadPage("userList",url);
	});
}

function mmUserByName(){
	var userName = $("#searchBoxUserName").val();
	var url = "userdata.do?userName="+encodeURI(userName);
	loadPage("userList",url);
}

function addUserWin(){
	var title = "新增用户";	
	$('#addUserWin').dialog({    
	    title: title,    
	    width: 350,    
	    height: 400,  
	    modal: true,
	    href:'showpage.do?pageId=addUser',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveAndEditUser();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addUserWin').window('close'); }}],
	 	onLoad:function(){	
	 		initForm("adduser.do");
	 		$("#userName").show();
	 		$('#roleSelect').combogrid({    
	 		    panelWidth:200,    
	 		    value:'',    	 		     
	 		    idField:'id',  
	 		    editable:false,
	 		    textField:'roleName',
	 		    editable:false,
	 		    rownumbers:true,//序号
	 		    //pagination : true,
	 		    url:'rolepage.do',    
	 		    columns:[[{field:'id',title:'id',hidden:true,width:60},    
	 		 		        {field:'roleName',title:'角色名称',width:150}]]    
	 		});  	 		
	    }		
	 }); 
}

function editUserWin(){
	var row = $("#userList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	var roleId = row.roleId;
	$('#addUserWin').dialog({    
	    title: "编辑用户",    
	    width: 400,    
	    height: 420,  
	    modal: true,
	    href:'showpage.do?pageId=addUser',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveAndEditUser();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addUserWin').window('close'); }}],
	 	onLoad:function(){	
	 		initForm("edituser.do");
	 		$('#roleSelect').combogrid({    
	 		    panelWidth:200,    
	 		    value:roleId,    	 		     
	 		    idField:'id',  
	 		    textField:'roleName',
	 		    editable:false,
	 		    rownumbers:true,//序号
	 		    //pagination : true,
	 		    url:'rolepage.do',    
	 		    columns:[[{field:'id',title:'id',hidden:true,width:60},    
	 		 		        {field:'roleName',title:'角色名称',width:150}]]    
	 		});  
	 		$("#addUser").find("#userNameDis").show();
	 		$("#addUser").find("#password").remove();
 			$("#addUser").find("#userName").val(row.userName);
 			$("#addUser").find("#userNameDis").val(row.userName);		
 			$("#addUser").find("#mobilePhone").val(row.mobilePhone); 
 			$("#addUser").find("#email").val(row.email);	
 			$("#addUser").find("#id").val(row.id);
 			$("#addUser").find("input[type=radio][value="+row.enabled+"]").attr("checked",'checked');
	    }		
	 }); 
}

function reSetPassword(){
	var row = $("#userList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$.get("resetpassword.do?userId="+row.id, function(data){
		var data = eval('('+data+')');
        if(data.result == true){
        	$.messager.alert('提示',data.message);        	         	
        }
	});
}

function saveAndEditUser(){
	$("#addUser").submit();	
}
function modyPwWin(){
	$('#modyPwWin').dialog({    
	    title: "密码修改",    
	    width: 350,    
	    height: 300,  
	    modal: true,
	    href:'showpage.do?pageId=modyPassword',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){modyPassWord();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#modyPwWin').window('close'); }}],	
	 }); 
}
function modyPassWord(){
	$("#modyPasswd").submit();	
}
//用户管理 end

//软件版本管理 start
function mmSoftwareVersion(){
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '嵌入式软件版本管理');
	if(isExists){
		tab.tabs('select', '嵌入式软件版本管理'); 
		return;
	}
	$.get("showpage.do?pageId=mmVersion", function(data){
		tab.tabs('add',{    
		    title:'嵌入式软件版本管理',    
		    content:data,    
		    closable:true,
		    id:'mmVersion'	    
		    
		});	
		var url = "version/pagedata.do";
		loadPage("versionList",url);
	});
}
function addVersionWin(){
	$('#addVersionWin').dialog({    
	    title: "新增嵌入式软件版本",    
	    width: 350,    
	    height: 320,  
	    modal: true,
	    href:'showpage.do?pageId=addVersion',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addVersion();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addVersionWin').window('close'); }}],
	 	onLoad:function(){	 		
	 		initForm("version/addversion.do");
	 		$("#protocalTr").show();
	 		$('#protocalSelect').combogrid({    
	 		    panelWidth:200,    
	 		    value:'',	 		     
	 		    idField:"id",  
	 		   editable:false,
	 		    textField:'protocalName',
	 		    rownumbers:true,//序号
	 		    //pagination : true,
	 		    url:'protocal/alldata.do',    
	 		    columns:[[{field:'id',title:'id',hidden:true,width:60},    
	 		 		        {field:'protocalName',title:"协议名称",width:150}]]    
	 		});
	 	}		

	 });
}

function editVersionWin(){
	//获取选中列表
	var row = $("#versionList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	
	$('#addVersionWin').dialog({    
	    title: "编辑嵌入式软件版本",    
	    width: 350,    
	    height: 230,  
	    modal: true,
	    href:'showpage.do?pageId=addVersion',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addVersion();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addVersionWin').window('close'); }}],
	 	onLoad:function(){	
	 		initForm("version/editversion.do");
 			$("#addVersion").find("#versionName").val(row.versionName);
 			$("#addVersion").find("#softwareCode").val(row.softwareCode);
 			$("#addVersion").find("#id").val(row.id);
 			$('#protocalSelect').combogrid({    
	 		    panelWidth:200,    
	 		    value:row.id,	 		     
	 		    idField:"id",
	 		   editable:false,
	 		    textField:'protocalName',
	 		    rownumbers:true,//序号
	 		    //pagination : true,
	 		    columns:[[{field:'id',title:'id',width:60},    
	 		 		        {field:'protocalName',title:"协议名称",width:150}]]    
	 		});
	    }		
	 });
}

function addVersion(){
	$('#addVersion').submit();
}

//软件版本管理 end
//通信管理 start
//指令配置start
function instructionConfig() {
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '指令配置');
	if (isExists) {
		tab.tabs('select', '指令配置');
		return;
	}

	$.get("showpage.do?pageId=instructionConfig", function(data) {
		tab.tabs('add', {
			title : '指令配置',
			content : data,
			closable : true,
			id : 'instructionConfig'
		});
		var url = "inStruction/contentPage.do";
		loadPage("instructionConfigList", url);

		$('#searchProtocal').combogrid({
			panelWidth : 200,
			value : '',
			idField : "id",
			editable:false,
			textField : 'protocalName',
			rownumbers : true,
			url : 'protocal/alldata.do',
			columns : [ [ {
				field : 'id',
				title : 'id',
				hidden : true,
				width : 60
			}, {
				field : 'protocalName',
				title : "协议名称",
				width : 150
			} ] ]
		});
	});
}

function addInstructionCofigWin() {
	$('#addInstructionCofigWin').dialog({
		title : "新增指令配置",
		width : 400,
		height : 400,
		modal : true,
		href : 'showpage.do?pageId=addInstructionConfig',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				addInstructionCofig();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addInstructionCofigWin').window('close');
			}
		} ],
		onLoad : function() {
			$('#belongProtocal').combogrid({
				panelWidth : 200,
				value : '',
				idField : "id",
				editable:false,
				textField : 'protocalName',
				rownumbers : true,
				url : 'protocal/alldata.do',
				columns : [ [ {
					field : 'id',
					title : 'id',
					hidden : true,
					width : 60
				}, {
					field : 'protocalName',
					title : "协议名称",
					width : 150
				} ] ],
				onSelect : function(rowIndex, rowData) {
					$.get("protocal/directivelistchecked.do?protocalId="+rowData.id, function(data){
			 			var data = eval('('+data+')');
			 			var ss =document.getElementById("dirSelectValues");
			 			ss.options.length=0;
			 			for(var i =0;i<data.length;i++){
			 				ss.options[i] = new Option(data[i].text,data[i].id);	 		
			 			}
					});
				}
			});
			initForm("inStruction/adddata.do");
		}
	});
}

function editInstructionCofigWin() {
	// 获取选中列表
	var row = $("#instructionConfigList").datagrid("getSelected");
	if (null == row) {
		$.messager.alert('提示', '请选择一条记录');
		return;
	}
	$('#addInstructionCofigWin').dialog({
		title : "修改指令配置",
		width : 400,
		height : 400,
		modal : true,
		href : 'showpage.do?pageId=addInstructionConfig',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				addInstructionCofig();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addInstructionCofigWin').window('close');
			}
		} ],
		onLoad : function() {
			$('#belongProtocal').combogrid({
				panelWidth : 200,
				value : '',
				idField : "id",
				editable:false,
				textField : 'protocalName',
				rownumbers : true,
				url : 'protocal/alldata.do',
				columns : [ [ {
					field : 'id',
					title : 'id',
					hidden : true,
					width : 60
				}, {
					field : 'protocalName',
					title : "协议名称",
					width : 150
				} ] ],
				onSelect : function(rowIndex, rowData) {
					$.get("protocal/directivelistchecked.do?protocalId="+rowData.id, function(data){
			 			var data = eval('('+data+')');
			 			var ss =document.getElementById("dirSelectValues");
			 			ss.options.length=0;
			 			for(var i =0;i<data.length;i++){
			 				ss.options[i] = new Option(data[i].text,data[i].id);	 		
			 			}
					});
				},
				onLoadSuccess:function(){
					$('#belongProtocal').combogrid('grid').datagrid('selectRecord',row.protoCalid);
					$.get("protocal/directivelistchecked.do?protocalId="+row.protoCalid, function(data){
			 			var data = eval('('+data+')');
			 			var ss =document.getElementById("dirSelectValues");
			 			ss.options.length=0;
			 			var k = 0;
			 			for(var i =0;i<data.length;i++){
			 				ss.options[i] = new Option(data[i].text,data[i].id);
			 				if(ss.options[i].value==row.derictid){
			 					k=i;
			 				}
			 			}
			 			ss.options[k].selected = true;
					});
				}
			});
			$("#id").val(row.id);
			$("#attrName").val(row.attrName);
			$("#derection").val(row.derection);
			$("#length").val(row.length);
			$("#startValue").val(row.startValue);
			$("#instrucHigh").val(row.instrucHigh);
			$("#instrucLow").val(row.instrucLow);
			initForm("inStruction/editdata.do");
		}
	});
}

function addInstructionCofig() {
	$("#addInstruction").submit();
}

function derectionformater(value, row, index) {
	if (value == 0) {
		return "上传";
	} else {
		return "下发";
	}
}

function searchProtoCalById() {
	var protoCalid = encodeURI($("#searchProtocal").datetimebox('getValue'));
	var url = "inStruction/contentPage.do?protoCalid=" + protoCalid;
	loadPage("instructionConfigList", url);
}
//指令配置 end

//指令详情配置 start 
function instructionParamsConfig() {
	// 获取选中列表
	var row = $("#instructionConfigList").datagrid("getSelected");
	if (null == row) {
		$.messager.alert('提示', '请选择一条记录');
		return;
	}
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '指令详情配置');
	if (isExists) {
		tab.tabs('close', '指令详情配置');
		var tab = $('#showPanel');
		var isExists = tab.tabs('exists', '指令详情配置');
	}

	$.get("showpage.do?pageId=instructionParamsConfig", function(data) {
		tab.tabs('add', {
			title : '指令详情配置',
			content : data,
			closable : true,
			id : 'instructionParamsConfig'
		});
		var url = "inStruction/paramsPage.do?inStructionId="+row.id;
		loadPage("instructionParamsConfigList", url);
	});
}

function addInstructionParamsCofigWin() {
	var row = $("#instructionConfigList").datagrid("getSelected");
	$('#addInstructionParamsCofigWin').dialog({
		title : "新增指令详情配置",
		width : 400,
		height : 400,
		modal : true,
		href : 'showpage.do?pageId=addInstructionParamsConfig',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				addInstructionParamsCofig();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addInstructionParamsCofigWin').window('close');
			}
		} ],
		onLoad : function() {
			$("#inStructionId").val(row.id);
			initForm("inStruction/adddataParams.do");
		}
	});
}

function editInstructionParamsCofigWin() {
	// 获取选中列表
	var row = $("#instructionParamsConfigList").datagrid("getSelected");
	if (null == row) {
		$.messager.alert('提示', '请选择一条记录');
		return;
	}
	$('#addInstructionParamsCofigWin').dialog({
		title : "修改指令详情配置",
		width : 400,
		height : 400,
		modal : true,
		href : 'showpage.do?pageId=addInstructionParamsConfig',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				addInstructionParamsCofig();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addInstructionParamsCofigWin').window('close');
			}
		} ],
		onLoad : function() {
			$("#id").val(row.id);
			$("#paramName").val(row.paramName);
			$("#paramContent").val(row.paramContent);
			$("#startPosation").val(row.startPosation);
			$("#paramLength").val(row.paramLength);
			$("#orderId").val(row.orderId);
			$("#isOuterset").val(row.isOuterset);
			$("#inStructionName").val(row.inStructionName);
			$("#inStructionId").val(row.inStructionId);
			initForm("inStruction/editdataParams.do");
		}
	});
}

function addInstructionParamsCofig() {
	$("#addInstructionParams").submit();
}

function isOutersetformater(value, row, index) {
	if (value == 1) {
		return "是";
	} else if(value == 0){
		return "否";
	}
}
//指令详情配置 end
//通信管理 end
//通讯协议管理 start
function mmProtocal(){
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '通讯协议管理');
	if(isExists){
		tab.tabs('select', '通讯协议管理'); 
		return;
	}
	$.get("showpage.do?pageId=mmProtocal", function(data){
		tab.tabs('add',{    
		    title:'通讯协议管理',    
		    content:data,    
		    closable:true
		});	
		var url = "protocal/pagedata.do";
		loadPage("protocalList",url);
	});
}
function addProtocalWin(){
	$('#addProtocalWin').dialog({    
	    title: "新增通信协议",    
	    width: 600,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=addProtocal',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addProtocal();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addProtocalWin').window('close'); }}],
	 	onLoad:function(){	 	
	 		initForm("protocal/addprotocal.do");
	 		$('#directiveTree').tree({    
	 		    url: "protocal/directivelist.do",
	 		    checkbox:true,
	 		    cascadeCheck:false,
	 		   onCheck:function(node, checked){
	 			   if(!checked){
	 				  removeParamByDiretive("diretiveListParams",node.id);
	 			   }
	 		   },
	 		    onContextMenu: function(e, node){
	 				e.preventDefault();
	 				$('#directiveTree').tree('select', node.target);
	 				// 显示快捷菜单
	 				$('#directiveTreeAdd').menu('show', {
	 					left: e.pageX,
	 					top: e.pageY
	 				});
	 			}

	 		}); 
	 		$('#diretiveListParams').datagrid({
	 			width:280,
	 			height:290,
	 		    columns:[[    
	 		        {field:'directiveId',title:'指令id',checkbox:true},
	 		        {field:'directive',title:'指令名',width:100},  
	 		        {field:'name',title:'参数名',width:85,editor:{type:'validatebox',options:{required:true}}},    
	 		        {field:'orderId',title:'顺序',width:40,editor:{type:'numberbox',options:{required:true}}}    
	 		    ]],
	 		    onDblClickCell:function(rowIndex, field, value){
		 			  $('#diretiveListParams').datagrid('beginEdit', rowIndex);
		 			 }
	 		    
	 		}); 
	 		}		
	 });
}
function appendParam(){
	var node =$('#directiveTree').tree('getSelected');
	if(!node.checked){
		$.messager.alert('提示', '还未勾选该选项');
		return;
	};
	if(node!= null){
		var $dg = $("#diretiveListParams");
		$dg.datagrid('appendRow', {directiveId:node.id,directive:node.text});
		var rows = $dg.datagrid('getRows');
		$dg.datagrid('beginEdit', rows.length - 1);
	}
}

function removeParamByDiretive(div,diretiveId){
	var $dg = $("#"+div);
	var rows = $dg.datagrid('getRows');
    if (rows) {
    	for(var i = rows.length-1;i>=0;i--){
    		if(diretiveId == rows[i].directiveId){
    			var rowIndex = $dg.datagrid('getRowIndex', rows[i]);
                $dg.datagrid('deleteRow', rowIndex);
    		}
    	}
    }
}

function removeParamFromList(){
	var $dg = $("#diretiveListParams");
	var rows = $dg.datagrid('getSelections');
    if (rows) {
    	for(var i = rows.length-1;i>=0;i--){
    		var rowIndex = $dg.datagrid('getRowIndex', rows[i]);
            $dg.datagrid('deleteRow', rowIndex);
    	}
    }
}

function finishEdit(){
	//获取选中列表
	var $dg = $("#diretiveListParams");
	var rows = $dg.datagrid('getRows');
	var param = "";
	var order = "";
	for ( var i = 0; i < rows.length; i++) {
		 $dg.datagrid('endEdit', i);
	     curParam = "{"+rows[i].directiveId+","+rows[i].name+"}";
	     curOrder = "{"+rows[i].directiveId+","+rows[i].orderId+"}";
	     if(param.indexOf(curParam) !=-1){
	    	 $.messager.alert('提示','同一指令参数名不能重复');
	    	 $dg.datagrid('beginEdit', i);
	    	 return false;
	     }
	     param += curParam;
	     if(order.indexOf(curOrder) !=-1){
	    	 $.messager.alert('提示','同一指令参数顺序不能重复'); 
	    	 $dg.datagrid('beginEdit', i);
	    	 return false;
	     }
	     order += curOrder;
	     
	}
}


function addParam(){
	var $dg = $("#parmsList");
	$dg.datagrid('appendRow', {});
	var rows = $dg.datagrid('getRows');
	$dg.datagrid('beginEdit', rows.length - 1);
}

function removeParam(){
	var $dg = $("#parmsList");
	var row = $dg.datagrid('getSelected');
    if (row) {
        var rowIndex = $dg.datagrid('getRowIndex', row);
        $dg.datagrid('deleteRow', rowIndex);
    }
}
function saveParams(){
	//获取选中列表
	var row = $("#protocalList").datagrid("getSelected");
	var directiveId = $("#dirSelect").val();
	if(row){
		if(null == row){
			$.messager.alert('提示','请选择一条记录');  
			return;
		}
	}
	var $dg = $("#parmsList");
	var rows = $dg.datagrid('getRows');
	var nameList = "";
	var orderIdList = "";
	var str ='[';
	for ( var i = 0; i < rows.length; i++) {	 
	     $dg.datagrid('endEdit', i);
	     if(null == rows[i].name || rows[i].name == ''){
	    	 $.messager.alert('错误',"操作失败，参数名不能为空");
	    	 return;
	     }
	     if(null == rows[i].orderId || rows[i].orderId == ''){
	    	 $.messager.alert('错误',"操作失败，序列不能为空");
	    	 return ;
	     }else if(rows[i].orderId<=0){
	    	 $.messager.alert('错误',"操作失败，序列必须大于0");
	    	 return ;
	     }
	     if(nameList.indexOf("|"+rows[i].name+"|") !=-1|| orderIdList.indexOf("|"+rows[i].orderId+"|") != -1){
	    	 $.messager.alert('错误',"操作失败，参数名或序列有重复");
	    	 return ;
	     }
	     if(i!=0){
	    	 str+=',';
	     }	     	     
	     str+= '{"name":"'+rows[i].name+'","orderId":'+rows[i].orderId+',"directiveId":"'+directiveId+'","protocalId":"'+row.id+'"}';
	     nameList +="|"+rows[i].name+"|";
	     orderIdList +="|"+rows[i].orderId+"|";
	}
	str+=']';
	 $.ajax({  
         type: "POST",  
         url: "protocal/saveparam.do",  
         data: 'data=' + str,  
         dataType: 'json',  
         success: function (data1) {
 	        if(data1.result == true){
 	        	$.messager.alert('消息',"保存成功");
 	        }else{
 	        	$.messager.alert('错误',data.message);
 	        }
         }  
     }); 
}

function editProtocalWin(){
	//获取选中列表
	var row = $("#protocalList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	
	$('#addProtocalWin').dialog({    
	    title: "编辑通信协议",    
	    width: 600,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=addProtocal',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addProtocal();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addProtocalWin').window('close'); }}],
	 	onLoad:function(){		 			
	 		$('#directiveTree').tree({    
	 		    url: "protocal/directivelist.do?protocalId="+row.id,
	 		    checkbox:true,
	 		    cascadeCheck:false,
	 		    lines:true	 ,
	 		    onCheck:function(node, checked){
	 			   if(!checked){
	 				  removeParamByDiretive("diretiveListParams",node.id);
	 			   }
	 		    },
	 		  	onContextMenu: function(e, node){
	 				e.preventDefault();
	 				$('#directiveTree').tree('select', node.target);
	 				// 显示快捷菜单
	 				$('#directiveTreeAdd').menu('show', {
	 					left: e.pageX,
	 					top: e.pageY
	 				});
	 		  	},
	 		    onLoadSuccess:function(node, data){
	 		    	$('#diretiveListParams').datagrid({
	 		 			width:280,
	 		 			height:290,
	 		 		    columns:[[    
	 		 		        {field:'directiveId',title:'指令id',checkbox:true},
	 		 		        {field:'directive',title:'指令名',width:100},  
	 		 		        {field:'name',title:'参数名',width:85,editor:{type:'validatebox',options:{required:true}}},    
	 		 		        {field:'orderId',title:'顺序',width:40,editor:{type:'numberbox',options:{required:true}}}    
	 		 		    ]],
	 		 		    onDblClickCell:function(rowIndex, field, value){
	 			 			  $('#diretiveListParams').datagrid('beginEdit', rowIndex);
	 			 			 }
	 		 		    
	 		 		}); 
	 		    	var nodes = $('#directiveTree').tree('getChecked');
	 		    	//获取选中列表
	 		    	var row = $("#protocalList").datagrid("getSelected");
	 		    	if(null != nodes && null != row){
	 		    		for(var i=0; i < nodes.length;i++){
	 		    			getDirctiveParam(row.id,nodes[i].id);
	 		    		}
	 		    	}
	 		    }
	 	
	 		}); 
	 		initForm("protocal/editprotocal.do");
	 		$("#addProtocal").find("#protocalName").val(row.protocalName);
	 		$("#addProtocal").find("#protocalCode").val(row.protocalCode);
	 		$("#addProtocal").find("#id").val(row.id); 
	 		$("#addProtocal").find("input[type=radio][value="+row.enabled+"]").attr("checked",'checked');
	    }		
	 });
}

function refreshParams(){
	var nodes = $('#directiveTree').tree('getChecked');
 	//获取选中列表
 	var row = $("#protocalList").datagrid("getSelected");
 	if(null != nodes && null != row){
 		for(var i=0; i < nodes.length;i++){
 			getDirctiveParam(row.id,nodes[i].id);
 		}
 	}
}


function getDirctiveParam(protocalId,directiveId){
	$.get("protocal/getparam.do?protocalId="+protocalId+"&directiveId="+directiveId, function(data){
		var data = eval('(' + data + ')');
		for(var i=0;i<data.length;i++){
			$('#diretiveListParams').datagrid('appendRow',data[i]);
		}
	});
}

function linkParemsWin(){
	//获取选中列表
	var row = $("#protocalList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}	
	$('#addProtocalWin').dialog({    
	    title: "编辑参数",    
	    width: 400,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=linkParams',
	    buttons:[{text:'确定',iconCls:'icon-save',handler:function(){$('#addProtocalWin').window('close'); }}],
	 	onLoad:function(){
	 		$.get("protocal/directivelistchecked.do?protocalId="+row.id, function(data){
	 			var data = eval('('+data+')');
	 			var ss =document.getElementById("dirSelect");
	 			for(var i =0;i<data.length;i++){
	 				ss.options[i] = new Option(data[i].text,data[i].id);	 				
	 			}
	 			$('#parmsList').datagrid({ 
		 			singleSelect:true,
		 		    columns:[[    
		 		        {field:'name',title:'参数名',width:100,editor:'text'},    
		 		        {field:'orderId',title:'顺序',width:100,editor:'numberbox'}    
		 		    ]],
		 		    url:"protocal/getparam.do?protocalId="+row.id+"&directiveId="+ss.options[0].value,
		 		    onDblClickCell:function(rowIndex, field, value){
		 			  $('#parmsList').datagrid('beginEdit', rowIndex);
		 			 }
		 		}); 	 			
	 		});
	 			 		
	    }		
	 });
}

function addProtocal(){
	$('#addProtocal').submit();
}
//通讯协议管理 end

//伪指令管理 start
function mmDirective(){
	var tab = $('#showPanel');
	var title = '伪指令管理';
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId=mmDirective", function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		var url = "directive/pagedata.do";
		loadPage("directiveList",url);
	});
}

function addDirectiveWin(){
	$('#addDirectiveWin').dialog({    
	    title: "新增伪指令",    
	    width: 350,    
	    height: 300,  
	    modal: true,
	    href:'showpage.do?pageId=addDirective',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addDirective();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addDirectiveWin').window('close'); }}],
	 	onLoad:function(){	 			 		
	 		initForm("directive/adddirective.do");
	 		}		
	 });
}

function editDirectiveWin(){
	//获取选中列表
	var row = $("#directiveList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$('#addDirectiveWin').dialog({    
	    title: "修改伪指令",    
	    width: 350,    
	    height: 300,  
	    modal: true,
	    href:'showpage.do?pageId=addDirective',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addDirective();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addDirectiveWin').window('close'); }}],
	 	onLoad:function(){	 
	 		initForm("directive/editdirective.do");
	 		$("#addDirective").find("#directiveName").val(row.directiveName);
	 		$("#addDirective").find("#directive").val(row.directive);
	 		$("#addDirective").find("#orderId").val(row.orderId);
	 		$("#addDirective").find("#id").val(row.id);
	 		$("#addDirective").find("input[name=isFunctionRun][value="+row.isfunctionrun+"]").attr("checked",'checked');
	 		}		
	 });
}

function addDirective(){
	$('#addDirective').submit();
}
//伪指令管理 end

//燃气公司管理 start
function mmCompany(){
	var tab = $('#showPanel');
	var title = '燃气公司管理';
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId=mmCompany", function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		var url = "company/pagedata.do?companyName=";
		loadPage("companyList",url);
	});
}

//根据燃气公司名称获取公司明细列表
function searchCompanyList(){
	var name = $("#searchBoxCompanyName").val();
	var url = "company/companylist.do?companyName="+encodeURI(name);	
	loadPage("companyList",url);
}

function editCompanyWin(){
	//获取选中列表
	var row = $("#companyList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$('#addCompanyWin').dialog({    
	    title: "修改燃气公司",    
	    width: 350,    
	    height: 250,  
	    modal: true,
	    href:'showpage.do?pageId=addCompany',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addCompany();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addCompanyWin').window('close'); }}],
	 	onLoad:function(){	 
	 		initForm("company/editcompany.do");
	 		$("#addCompany").find("#companyName").val(row.companyName);
	 		$("#addCompany").find("#note").val(row.note);
	 		$("#addCompany").find("#companyCode").val(row.companyCode);
	 		$("#addCompany").find("input[type=radio][value="+row.enabled+"]").attr("checked",'checked');
	 		}		
	 });
}
function syncCompany(){
	var dlg = $.messager.alert('提示',"数据同步中请稍后......<img src='pm/easyui/themes/default/images/loading.gif'/>");
	$.get("company/sycncompany.do", function(data){
		$(".messager-button").find("a").click();
		var data = eval('('+data+')');
		if(data.result == true){
			 $.messager.alert('提示',"数据同步成功");
			 $('#companyList').datagrid('reload');       	               	
	        }else{
	        	$.messager.alert('错误',data.message);
	     }  
	});
}
function addCompany(){
	$('#addCompany').submit();
}
//燃气公司管理 end
//表具管理 start
function mmMeter(){
	var tab = $('#showPanel');
	var title = '表具管理';
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId=mmMeter", function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		$('#searchBoxMeter').searchbox({ 
			searcher:function(value,name){ 
				if(name==2){
					meterSearch("",value);
				}else{
					meterSearch(value,"");
				}
				
			}, 
			menu:'#mmMeterSearchMenu', 
			prompt:'请输入搜索条件' 
		});
		var url = "meter/pagedata.do";
		loadPage("meterList",url);
	});
}
function meterSearch(meterNum,companName){
	var url = "meter/pagedata.do?companyName="+encodeURI(companName)+"&meterNum="+meterNum;
	loadPage("meterList",url);
}
function editMeterWin(){
	//获取选中列表
	var row = $("#meterList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$('#addMeterWin').dialog({    
	    title: "修改表具",    
	    width: 600,    
	    height: 500,  
	    modal: true,
	    href:'showpage.do?pageId=addMeter',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addMeter();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addMeterWin').window('close'); }}],
	 	onLoad:function(){	 
	 		initForm("meter/editmeter.do");
	 		setMeterEditParams(row);	 		
	 		$('#versionSelectMeter').combogrid({    
			    panelWidth:200,    
			    value:row.softwareversionid,	 		     
			    idField:"id",  
			    editable:false,
			    textField:'versionName',
			    rownumbers:true,//序号
			    onChange:function(){
			    	var gridRow = $('#versionSelectMeter').combogrid('grid').datagrid('getSelected');
			    	selectDirectiveList(gridRow.protocalId,gridRow.id,row.id);
	 		    },
			    url:'version/alldata.do',    
			    columns:[[{field:'protocalId',title:'protocalid',hidden:true,width:60},
			              {field:'id',title:'id',hidden:true,width:60},    
			 		      {field:'versionName',title:"版本名称",width:150}]]
			});	
	 		$('#companySelect').combogrid({    
			    panelWidth:250,    
			    value:row.companyid,	 		     
			    idField:"companyCode",  
			    textField:'companyName',
			    rownumbers:true,//序号
			    mode: 'remote',   
			    url:'meter/companysearth.do', 		   
			    columns:[[{field:'companyCode',title:'companyCode',hidden:true,width:60},    
			 		        {field:'companyName',title:"公司名称",width:150}]]    
			});
	 		$('#companySelect').combogrid('setValues', [row.companyName]);
	 		if(null != row.softwareversionid && row.softwareversionid !=''){
	 			selectDirectiveList(row.protocalid,row.softwareversionid,row.id);
	 		}	 		
	 	}		
	 });
}

function addMeter(){
	$('#addMeter').submit();
}

function selectDirectiveList(protocalId,softwareversionid,id){
	$.get("meter/directives.do?softwareVersionId="+softwareversionid+"&id="+id, function(data){
		if(null != data && data.length > 0){		
			var data = eval('('+data+')');
			$("#directiveChildTreeTd").fadeIn("slow");
			$('#directiveChildTree').tree({
				data:data,
				checkbox:true,
				onCheck:function(node, checked){
		 			   if(checked){
		 				  getDirctiveParamValue(protocalId,node.id,id);
		 			   }else{
		 				  removeParamByDiretive("meterDiretiveListParams",node.id);
		 			   }
		 		},
				onLoadSuccess:function(node, data){
					$('#meterDiretiveListParams').datagrid({
			 			width:280,
			 			height:340,
			 		    columns:[[    
			 		        {field:'directiveId',title:'指令id',checkbox:true},
			 		        {field:'directiveName',title:'指令名',width:100},  
			 		        {field:'name',title:'参数名',width:85},    
			 		        {field:'value',title:'参数值',width:40,editor:{type:'validatebox',options:{required:true}}}    
			 		    ]],
			 		    onDblClickCell:function(rowIndex, field, value){
				 			  $('#meterDiretiveListParams').datagrid('beginEdit', rowIndex);
				 			 }
			 		    
			 		}); 
					clearParams("meterDiretiveListParams");										
				}
			}); 
		}else{
			$("#directiveChildTreeTd").fadeOut("slow");
		}		
	});
}

function clearParams(div){
	var $dg = $("#"+div);
		var rows = $dg.datagrid('getRows');
	    if (rows) {
	    	for(var i = rows.length-1;i>=0;i--){
	    		var rowIndex = $dg.datagrid('getRowIndex', rows[i]);
	            $dg.datagrid('deleteRow', rowIndex);
	    	}
	    }
}

function getDirctiveParamValue(protocalId,directiveId,meterId){
	$.get("meter/getparam.do?protocalId="+protocalId+"&directiveId="+directiveId+"&meterId="+meterId, function(data){
		var data = eval('(' + data + ')');
		for(var i=0;i<data.length;i++){
			var $dg = $("#meterDiretiveListParams");
			$dg.datagrid('appendRow',data[i]);
			var rows =$dg.datagrid('getRows');
			$dg.datagrid('beginEdit', rows.length-1);
		}
	});
}

function setMeterEditParams(row){
		$("#addMeter").find("#id").val(row.id);
		$("#addMeter").find("#meterNum").val(row.meterNum);
		$("#addMeter").find("#basemeternum").val(row.basemeternum);
		$("#addMeter").find("#cumulant").val(row.cumulant);
		$("#addMeter").find("#reservenum").val(row.reservenum);
		$("#addMeter").find("#softwareversionid").val(row.softwareversionid);
		$("#addMeter").find("#companyId").val(row.companyid);
		$("#addMeter").find("input[name='outfactorystate'][value="+row.outfactorystate+"]").attr("checked",'checked');
}


function outFactoryFormater(value,row,index)
{
	if(value==1){
		return "已出厂";
	}else{
		return "未出厂";
	}

}

function needMoveFormater(value,row,index)
{
	if(value==1){
		return "已转移";
	}else{
		return "未转移";
	}

}

function executeMeter(){
	//获取选中列表
	var row = $("#meterList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	
	$.get("meter/executemeter.do?meterId="+row.id, function(data){
		var data = eval('(' + data + ')');
		if (data.result == true) {
			$.messager.alert('提示', '操作成功');
		} else {
			$.messager.alert('错误', data.message);
		}
	});
	
//	$('#addMeterWin').dialog({    
//	    title: "修改表具",    
//	    width: 400,    
//	    height: 530,  
//	    modal: true,
//	    href:'showpage.do?pageId=paramsValue',
//	    buttons:[{text:'返回',iconCls:'icon-save',handler:function(){$('#addMeterWin').window('close');}}],
//	 	onLoad:function(){	 
//	 		$.get("protocal/directivelistchecked.do?id="+row.id+"&protocalId="+row.protocalid, function(data){
//	 			var data = eval('('+data+')');
//	 			var ss =document.getElementById("dirSelectValues");
//	 			for(var i =0;i<data.length;i++){
//	 				ss.options[i] = new Option(data[i].text,data[i].id);	 		
//	 			}
//	 			if(ss.options.length == 0){
//	 				$('#parmsListValue').datagrid({ 
//			 			singleSelect:true,
//			 		    columns:[[    
//			 		        {field:'paramId',title:'paramId',width:100,hidden:true}, 
//			 		        {field:'name',title:'参数名',width:100},    
//			 		        {field:'value',title:'参数值',width:100,editor:'text'}    
//			 		    ]]
//			 		}); 
//	 			}else{
//	 				$('#parmsListValue').datagrid({ 
//			 			singleSelect:true,
//			 		    columns:[[    
//			 		        {field:'paramId',title:'paramId',width:100,hidden:true}, 
//			 		        {field:'name',title:'参数名',width:100},    
//			 		        {field:'value',title:'参数值',width:100,editor:'text'}    
//			 		    ]],
//			 		    url:"meter/getparam.do?protocalId="+row.protocalid+"&directiveId="+ss.options[0].value+"&meterId="+row.id,
//			 		    onDblClickCell:function(rowIndex, field, value){
//			 			  $('#parmsListValue').datagrid('beginEdit', rowIndex);
//			 			 }
//			 		}); 
//	 			}
//	 		});
//	 	}		
//	 });
}

//表具管理 end
//客户库管理 start
function mmIPMapping(){	
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '库名与IP映射管理');
	if(isExists){
		tab.tabs('select', '库名与IP映射管理'); 
		return;
	}
	
	$.get("showpage.do?pageId=mmIPMapping", function(data){
		tab.tabs('add',{    
		    title:'库名与IP映射管理',    
		    content:data,    
		    closable:true,
		    id:'mmIPMapping'
		});	
		var url = "customer/pagedata.do";
		loadPage("ipMappingList",url);
	});
}

function addIPMappingWin(){
	$('#addIPMappingWin').dialog({    
	    title: "新增客户库",    
	    width: 350,    
	    height: 280,  
	    modal: true,
	    href:'showpage.do?pageId=addIPMapping',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addIPMapping();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addIPMappingWin').window('close'); }}],
	 	onLoad:function(){
	 		initForm("customer/adddata.do");	 		
	    }		
	 }); 
}

function editIPMappingWin(){
	//获取选中列表
	var row = $("#ipMappingList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	$('#addIPMappingWin').dialog({    
	    title: "修改客户库",    
	    width: 350,    
	    height: 280,  
	    modal: true,
	    href:'showpage.do?pageId=addIPMapping',
	    buttons:[{text:'保存',iconCls:'icon-save',handler:function(){addIPMapping();}},
	 			{text:'取消',iconCls:'icon-cancel',handler:function(){$('#addIPMappingWin').window('close'); }}],
	 	onLoad:function(){
	 		initForm("customer/editdata.do");
	 		$("#addIPMapping").find("#customerName").val(row.customerName);
	 		$("#addIPMapping").find("#id").val(row.id);
	 		$("#addIPMapping").find("#ip").val(row.ip);
	 		$("#addIPMapping").find("#port").val(row.port);
	    }		
	 }); 
}

function addIPMapping(){
	$("#addIPMapping").submit();
}

//客户库管理 end
//通信记录查询 start
function mmCommunicationRecord(){	
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '通信历史记录查询');
	if(isExists){
		tab.tabs('select', '通信历史记录查询'); 
		return;
	}
	
	$.get("showpage.do?pageId=mmCommunicationRecord", function(data){
		tab.tabs('add',{    
		    title:'通信历史记录查询',    
		    content:data,    
		    closable:true,
		    id:'mmCommunicationRecord'
		});	
		
		var url = "record/pagedata.do";
		loadPage("mmCommunicationRecordList",url);
	});
}

function mmCommunicationRecordByTime(){
	var startTime = encodeURI($("#recordStartTime").datetimebox('getValue'));	
	var endTime = encodeURI($("#recordEndTime").datetimebox('getValue'));
	var state =$('#mmCommunicationRecordSelecte').combobox("getValue");
	if(state == -1){
		state = "";
	}
	var url = "record/pagedata.do?startTime="+startTime+"&endTime="+endTime+"&state="+state;
	loadPage("mmCommunicationRecordList",url);
}
//通信记录查询 end
//统计 start
function mmStatistics(){	
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', '统计');
	if(isExists){
		tab.tabs('select', '统计'); 
		return;
	}
	
	$.get("showpage.do?pageId=mmStatistics", function(data){
		tab.tabs('add',{    
		    title:'统计',    
		    content:data,    
		    closable:true,
		    id:'mmStatistics'
		});
		var url = "statistics/pagedata.do";
		loadPage("mmStatisticsList",url);
	});
}

function mmStatisticsByTime(){	
	var startTime = encodeURI($("#statisticsStartTime").datetimebox('getValue'));	
	var endTime = encodeURI($("#statisticsEndTime").datetimebox('getValue'));
	var url = "statistics/pagedata.do?startTime="+startTime+"&endTime="+endTime;
	loadPage("mmStatisticsList",url);
}
//统计 end
//外部转移 end
function mmOutMove(){
	var tab = $('#showPanel');
	var title = '外部转移';
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId=mmOutMove", function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		$('#searchBoxOutMoving').searchbox({ 
			searcher:function(value,name){ 
				if(name==2){
					$('#outMoveCancel').linkbutton({    
						disabled:true   
					});
					searchOutBatchMove(value);
				}else{
					$('#outMoveCancel').linkbutton({    
						disabled:false   
					});
					searchOutSingleMove(value);
				}
				
			}, 
			menu:'#mmOutMoving', 
			prompt:'请输入燃气公司名称' 
		});
		var url = "meter/metersdata.do?companyName=";
		loadPage("outMoveList",url);
	});
}
function searchOutSingleMove(name){
	$('#outMoveList').datagrid({
		singleSelect:true,
		autoRowHeight:false,
		fitColumns:true,
		pageSize:10,
	    columns:[[    
	        {field:'id',title:'id',checkbox:true,width:100}, 
	        {field:'meterNum',title:'表号',width:115}, 
	        {field:'companyName',title:'燃气公司',width:250},    
	        {field:'outerneedmove',title:'外部需转移',formatter:enabledformater,width:70},  
	        {field:'outermovestate',title:'外部转移状态',formatter:needMoveFormater,width:90}, 
	        {field:'companyid',title:'companyid',hidden:true,width:100} 
	    ]],
	});
	var url = "meter/metersdata.do?companyName="+encodeURI(name);
	loadPage("outMoveList",url);
}

function searchOutBatchMove(name){
	reLoadMoveTable("outMoveList");
	var url = "meter/outerlist.do?companyName="+encodeURI(name);
	loadPage("outMoveList",url);
}

function reLoadMoveTable(id){
	$('#'+id).datagrid({    
	    columns:[[    
	        {field:'companyId',title:'id',checkbox:true,width:100},    
	        {field:'companyName',title:'燃气公司',width:100},    
	        {field:'totalMeterCount',title:'表总数',width:100},  
	        {field:'needMoveMeterCount',title:'需转移表',width:100}, 
	        {field:'movedMeterCount',title:'已转移表',width:100} 
	    ]]    
	});
}

function needOutMoveEdit(opera){
	//获取选中列表
	var row = $("#outMoveList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}	
	if(row.outerneedmove == opera){
		$.messager.alert('提示','已经为该状态，无需再操作');  
		return;
	}
	if(row.outermovestate == 1){
		$.messager.alert('提示','该表已被转移无法变更');  
		return;
	}
	var id = '';
	var company ="";
	if(row.id != null && row.id != ''&&row.id != 'undefined'){
		id = row.id ;
	}else{
		company = "&companyId="+row.companyId;
	}
	var url = "meter/updatemoving.do?id="+id+"&outerNeedMove="+opera+company;
	editMove(url,'outMoveList');
}

function editMove(url,div){
	$.get(url, function(data){
		var data = eval('('+data+')');
        if(data.result == true){
        	$.messager.alert('提示',"操作成功");
        	$('#'+div).datagrid('reload'); 
        }else{
        	$.messager.alert('错误',data.message);
        };
	});
}
//外部转移 end
//内部转移 start
function mmInnerMove(){
	var tab = $('#showPanel');
	var title = '内部转移';
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId=mmInnerMove", function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		$('#searchBoxInnerMoving').searchbox({ 
			searcher:function(value,name){ 
				if(name==2){
					$('#innerMoveCancel').linkbutton({    
						disabled:true   
					});
					searchInnerBatchMove(value);
				}else{
					$('#innerMoveCancel').linkbutton({    
						disabled:false   
					});
					searchInnerSingleMove(value);
				}
				
			}, 
			menu:'#mmInnerMoving', 
			prompt:'请输入燃气公司名称' 
		});
		var url = "meter/metersdata.do?companyName=";
		loadPage("innerMoveList",url);
	});
}
function searchInnerBatchMove(name){
	reLoadMoveTable("innerMoveList");
	var url = "meter/innerlist.do?companyName="+encodeURI(name);	
	loadPage("innerMoveList",url);
}

function searchInnerSingleMove(name){
	$('#innerMoveList').datagrid({
		singleSelect:true,
		autoRowHeight:false,
		fitColumns:true,
		pageSize:10,
	    columns:[[    
	        {field:'id',title:'id',checkbox:true,width:100}, 
	        {field:'meterNum',title:'表号',width:115}, 
	        {field:'companyName',title:'燃气公司',width:250},    
	        {field:'innerneedmove',title:'内部需转移',formatter:enabledformater,width:70},  
	        {field:'innerneedmovetate',title:'内部转移状态',formatter:needMoveFormater,width:90}, 
	        {field:'companyid',title:'companyid',hidden:true,width:100} 
	    ]],
	});
	
	var url = "meter/metersdata.do?companyName="+encodeURI(name);
	loadPage("innerMoveList",url);
}
function needInnerMoveEdit(opera){
	//获取选中列表
	var row = $("#innerMoveList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	if(row.innerneedmove == opera){
		$.messager.alert('提示','已经为该状态，无需再操作');  
		return;
	}
	if(row.innermovestate == 1){
		$.messager.alert('提示','该表已被转移无法变更');  
		return;
	}
	var id = '';
	var company ="";
	if(row.id != null && row.id != ''&&row.id != 'undefined'){
		id = row.id ;
	}else{
		company = "&companyId="+row.companyId;
	}
	var url = "meter/updatemoving.do?id="+id+"&innerNeedMove="+opera+company;
	editMove(url,'innerMoveList');
}
//内部转移 end
//common

function enabledformater(value,row,index)
{
	if(value==1){
		return "是";
	}else{
		return "否";
	}

}
//时间格式转换
function timeFormater(value)
{
	var newTime = new Date();
	if(value != null){
		newTime = new Date(value);
	}
	var year = newTime.getFullYear();
	var month = addTag0(newTime.getMonth() + 1);  //月 
    var day = addTag0(newTime.getDate());         //日  
    var hh = addTag0(newTime.getHours());       //时  
    var mm = addTag0(newTime.getMinutes());    //分  
    var ss = addTag0(newTime.getSeconds());   //分 
	return year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss;	 	
}

//时间大小比较
function compTime(beginTime,endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
        return false;
    } else{
    	return true;
    }
}

function  addTag0(values){
	if(values < 10){
		return values = "0"+values;
	}
	return values;
}

function showPage(title,attributes){	
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', title);
	if(isExists){
		tab.tabs('select', title); 
		return;
	}
	$.get("showpage.do?pageId="+attributes, function(data){
		tab.tabs('add',{    
		    title:title,    
		    content:data,    
		    closable:true,
		});	
		var url = attributes+"data.do";
		loadPage(attributes+"List",url);
	});
}

function loadPage(divId,url){
	// 改变选项并刷新页面右栏的信息 
	$("#dom_var_pagination_"+divId).pagination('select', 1);  
	$('#'+divId).datagrid({
		pageNumber:1,
		height: 635,
		url:url,   
		loadFilter :function(data){
			 if(data.data == null || data.data == ""){
				 data.data = "";
			 }
			 data_ = {
					total: data.total,
					rows: data.data
			 };
			 $("#dom_var_pagination_"+divId).pagination({ 
			        pageList:[5,10,15,20],//每页显示几条记录
			        beforePageText: '第',//页数文本框前显示的汉字
			        afterPageText: '页    共 {pages} 页',
			        displayMsg: '当前显示 {from} - {to} 条记录    共 {total} 条记录',
		            total : data_.total  ,
		            onBeforeRefresh:function(){ 
		                $(this).pagination('loading');//正在加载数据中...
		                $(this).pagination('loaded'); //数据加载完毕
		            },
		            onSelectPage: function(pageNumber, pageSize){
				    	var start = (pageNumber-1)*pageSize+1;	
				    	var end = start+pageSize; 
				        $('#'+divId).datagrid('load',{  
				        	start: start,  
				        	end: end 
				        });
				    }
		            
		       });
			 
			 return data_;
		}
	});
}