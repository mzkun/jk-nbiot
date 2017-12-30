<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- [jQuery] -->
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/jquery.min.js" charset="utf-8"></script>
<!-- [EasyUI]  -->
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<link id="pms-theme" rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.4.3/themes/ui-cupertino/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" />

<!-- [扩展JS] --> 
<script type="text/javascript" src="${path}/js/jquery-easyui-1.4.3/easyui-common.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/js/jquery-easyui-1.4.3/windowControl.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/js/common.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/js/ajaxfileupload.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/js/jquery.mask.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/js/echarts.common.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/css/system.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/common.css">
<!-- 小图标 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ico.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" />

<script type="text/javascript">
var path = "${path}";

$(function(){
	$.ajaxSetup({
		type : "POST",
		error : function(jqXHR, textStatus, errorThrown) {
		}
	});
});

/*// 模糊查询燃气公司(类百度搜索框)
function btsloader(param,success,error){
	  var q = param.q || "";
	 
	  if(q.length <= 0) return false;
	  
	  $.ajax({
		 type:'post',
		 url:'getTopCustomCompany.do',
		 data:{searchText:q},
		 dataType:'json',
		 success:function(data){
		 	success(data);
		 },
		 error:function(xml, text, msg){
			 error.apply(this, arguments);
		 }
	  });
}
// 模糊查询燃气公司(类百度搜索框)带状态控制
function btsloaderForState(param,success,error){
	  var q = param.q || "";
	 
	  if(q.length <= 0) return false;
	  
	  $.ajax({
		 type:'post',
		 url:'getTopCustomCompanyForState.do',
		 data:{searchText:q},
		 dataType:'json',
		 success:function(data){
		 	success(data);
		 },
		 error:function(xml, text, msg){
			 error.apply(this, arguments);
		 }
	  });
}

//模糊查询批次信息(类百度搜索框)
function getTopBatch(param,success,error){
	var q = param.q || "";
	 
  	if(q.length <= 0) return false;
    var companyCode = $('#companyCode').val();

  	$.ajax({
	 	type:'post',
	 	url:'getTopBatch.do',
 		data:{searchText:q,companyCode:companyCode},
	 	dataType:'json',
	 	success:function(data){
	 		success(data);
	 	},
	 	error:function(xml, text, msg){
		 	error.apply(this, arguments);
	 	}
  	});
}

//模糊查询SIM卡入库批次信息(类百度搜索框)
function getSmTopBatch(param,success,error){
	var q = param.q || "";
	 
  	if(q.length <= 0) return false;
	  
  	$.ajax({
	 	type:'post',
	 	url:'getTopSmEnterBatch.do',
 		data:{searchText:q},
	 	dataType:'json',
	 	success:function(data){
			console.info(data)
	 		success(data);
	 	},
	 	error:function(xml, text, msg){
		 	error.apply(this, arguments);
	 	}
  	});
}
//模糊查询保管人
function getSmUserName(param,success,error){
	var q = param.q || "";

	if(q.length <= 0) return false;

	$.ajax({
		type:'post',
		url:'getTopUserName.do',
		data:{searchText:q},
		dataType:'json',
		success:function(data){
			success(data);
		},
		error:function(xml, text, msg){
			error.apply(this, arguments);
		}
	});
}
//模糊查询模组批次信息(类百度搜索框)
function getTopModBatch(param,success,error){
    var q = param.q || "";

    if(q.length <= 0) return false;

    $.ajax({
        type:'post',
        url:'getTopModBatch.do',
        data:{searchText:q},
        dataType:'json',
        success:function(data){
            success(data);
        },
        error:function(xml, text, msg){
            error.apply(this, arguments);
        }
    });
}

//模糊查询公司信息(类百度搜索框)
function getTopCompany(param,success,error){
    var q = param.q || "";

    if(q.length <= 0) return false;

    $.ajax({
        type:'post',
        url: path + '/user/getTopCompany.do',
        data:{searchText:q},
        dataType:'json',
        success:function(data){
            success(data);
        },
        error:function(xml, text, msg){
            error.apply(this, arguments);
        }
    });
}*/
</script>

