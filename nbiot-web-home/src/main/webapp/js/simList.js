$(function(){
	/*$.ajax({ 
	     type: "post",
	     url: "simList.do", 
	     //data: dataParam,
	     dataType: "json",  
	     success: function (data) {
	        alert(data);
	        
	     	
	   	},
	   	error: function (err,err1,err2) {     
	   		alertMsg.error("调用方法发生异常:"+JSON.stringify(err)+"err1"+ JSON.stringify(err1)+"err2:"+JSON.stringify(err2));
	   	}  
	 }); */
	
	initTable();
});
function initTable(){
	$('#companyList').datagrid({
		pageNumber:1,
		height: 635,
		url:'listuser.do',
		loadFilter :function(data){
			if(data.data == null || data.data == ""){
				data.data = "";
			}
			data_ = {
					total: data.total,
					rows: data.data
			};
			$("#dom_var_pagination_companyList").pagination({
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
					var start = (pageNumber-1)*pageSize;	
					var end = 10;//start+pageSize;
				$('#companyList').datagrid('load',{
					start: start,
					end: end
				});
				}
		
		});
			
			return data_;
		}
	});
}
function searchStudentList(){
	var companyName = $("#searchBoxCompanyName").val();
	$('#companyList').datagrid({
		pageNumber:1,
		height: 635,
		url:'listuser.do?params='+companyName,
		loadFilter :function(data){
			if(data.data == null || data.data == ""){
				data.data = "";
			}
			data_ = {
					total: data.total,
					rows: data.data
			};
			$("#dom_var_pagination_companyList").pagination({
			pageNumber:1,
			pageList:[5,10,15,20],//每页显示几条记录
			beforePageText: '第',//页数文本框前显示的汉字
			afterPageText: '页    共 {pages} 页',
			displayMsg: '当前显示 {from} - {to} 条记录    共 {total} 条记录',
		total : data_.total  ,
		onBeforeRefresh:function(){
		$(this).pagination('loading');//正在加载数据中...
		$(this).pagination('loaded'); //数据加载完毕
		},
		onSelectPage: function(){
					var start = (pageNumber-1)*pageSize;	
					var end = start+pageSize;
				$('#companyList').datagrid('load',{
					start: start,
					end: end
				});
				}
		
		});
			
			return data_;
		}
	});
	
}




function addStudent(){
	
	$('#tan').dialog({
	    title: '新增用户',
	    width: 300,
	    height: 300,
	    closed: false,
	    cache: false,
	    href: 'tan.do',
	    modal: true,
	    onLoad:function(){	
 			$("#name").val("");
 			$("#age").val("");
 			$('#sex').bind('change',tt);
 			
	    }
	});
}

function tt(){
	
	alert("哈哈哈哈哈哈");
}
function editStudent(){
	var row = $("#companyList").datagrid("getSelected");
	if(null == row){
		$.messager.alert('提示','请选择一条记录');  
		return;
	}
	var userId = row.id;
	$('#tan').dialog({
	    title: '修改用户',
	    width: 300,
	    height: 300,
	    closed: false,
	    cache: false,
	    href: 'tan.do?userId='+userId,
	    modal: true,
		onLoad:function(){	
 			$("#name").val(row.name);
 			$("#age").val(row.age);		
 			
	    }
	});
}


function submitForm(){
	$("#ff").form('validate');
    $('#ff').form('submit', {
        success: function(data){
        	data=JSON.parse(data);
            if (data.result==true){
            	$.messager.confirm('提示', data.message, function(r){
            		 $('#tan').dialog({
         			    closed: true
         			});
                     initTable();
            		
            	});
               
            }else{
            	
            	$.messager.alert('错错错',data.message); 
           		 $('#tan').dialog({
        			    closed: true
        			});
                    initTable();
           		
            }
        }
    });
}

function guanbi(){
	$('#tan').dialog({
	    closed: true
	});
}
function addOperator(){
	$('#tan').dialog({
	    title: '新增运营商',
	    width: 300,
	    height: 300,
	    closed: false,
	    cache: false,
	    href: 'tanO',
	    modal: true,
	    onLoad:function(){	
	    	$("#typeCode").change(function(){
	    		$.ajax({ 
	    		     type: "post",
	    		     url: "operter.do", 
	    		     //data: dataParam,
	    		     dataType: "json",  
	    		     success: function (data) {
	    		        alert(data.message);
	    		        $("#dd").val(data.message);
	    		   	},
	    		   	error: function (err,err1,err2) {
	    		   		alert("出错");
	    		   		//alertMsg.error("调用方法发生异常:"+JSON.stringify(err)+"err1"+ JSON.stringify(err1)+"err2:"+JSON.stringify(err2));
	    		   	}  
	    		 });
	    	});	
 			
	    }
	});
}

