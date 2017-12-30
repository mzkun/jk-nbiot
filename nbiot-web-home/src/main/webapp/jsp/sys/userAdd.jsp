<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>

<div >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>用户名<label class="required">*</label>：</td>
                    <td><input name="userNo" type="text"
                    class="easyui-validatebox" data-options="required:true,validType:['format','length[0,25]']" value=""></td>
                </tr>
                <tr>
                	<td>姓名：</td>
                    <td><input name="userName" type="text"
                    class="easyui-validatebox" data-options="required:false,validType:['length[0,25]']" value=""></td>
                </tr>
	            <tr>
	               <td>描述：</td>
	               <td colspan="3"><textarea name="remark" style="resize: none;" rows="3" cols="35" class="easyui-validatebox" data-options="validType:['length[0,50]']"></textarea></td>
	            </tr> 
              
               
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {    
    format: {    
        validator: function(value){
            return !value.match(/[^\w\.\/]/ig);    
        },    
        message: '用户名只能为字母和数字'   
    }   
}); 
</script>