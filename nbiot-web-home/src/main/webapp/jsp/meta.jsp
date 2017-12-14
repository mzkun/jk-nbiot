<%@ page language="java"   pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- [jQuery] -->
<script type="text/javascript" src="${path }/js/jquery-easyui-1.4.3/jquery.min.js"></script>
<!-- [EasyUI] -->
<script type="text/javascript" src="${path }/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path }/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/js/jquery-easyui-1.4.3/themes/ui-cupertino/easyui.css" />
<link rel="stylesheet" type="text/css" href="${path }/js/jquery-easyui-1.4.3/themes/icon.css" />
<!-- [EasyUI 扩展] -->
<script type="text/javascript" src="${path }/js/common/jeasyui-common.js"></script>
<script type="text/javascript" src="${path }/js/common/jeasyui-form-extend.js"></script>
<script type="text/javascript" src="${path }/js/common/jeasyui-validate-extend.js"></script>
<script type="text/javascript" src="${path }/js/common/jeasyui-window.js"></script>
<script type="text/javascript" src="${path }/js/common/kz.js"></script>
<!-- jQuery 遮罩层 -->
<script type="text/javascript" src="${path }/js/common/jquery.mask.js"></script>

<link rel="stylesheet" type="text/css" href="${path }/css/common.css" />
<script type="text/javascript">
$(function(){
	// ajax全局提交方式设置
	/* $.ajaxSetup({
		type : "POST",
		error : function(jqXHR, textStatus, errorThrown) {
		}
	}); */
});

function pathUrl(url){
	return '${path}' + url;
}
</script>