<%@ page language="java"   pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css">
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="js/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<script type="text/javascript">
(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

</script>
<%
	if (session.getAttribute("userInfo") == null) {
		response.sendRedirect("login.jsp");

		return;
	}
%>