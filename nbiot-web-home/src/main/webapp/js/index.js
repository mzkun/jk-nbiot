$(function() { 
	//绑定右键菜单事件
	tabCloseEven();
	//获取到期表具数量
    getExpireCountData()
}); 

/*为选项卡绑定右键*/
function tabBind() {
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
	
		var subtitle =$(this).children(".tabs-closable").text();
		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}

//绑定右键菜单事件
function showIframe(id,nodeName,url) { 
	var tab = $('#showPanel');
	var isExists = tab.tabs('exists', nodeName);
	if (isExists) {
		tab.tabs('select', nodeName);
		return;
	}
	tab.tabs('add',{    
	    title:nodeName,    
	    closable:true,
	    content:"<iframe src='"+url+"' id='tab_iframe_'"+id+" frameborder='0' style='border:0;width:100%;height:99.5%'></iframe>",
	    id:'tab_conten_'+id
	});
	tabBind();
}

//绑定右键菜单事件
function tabCloseEven(){
	var tab = $('#showPanel');
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = tab.tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url!=null){
			var id = $(currTab.panel('options').content).attr('id');
			tab.tabs('update',{    
				tab:currTab,
				options:{
					content:"<iframe src='"+url+"' id='"+id+"' width='99.5%' height='99%'></iframe>" 
				} 
			}); 
		} 
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		tab.tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!='欢迎使用'){
				tab.tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t!='欢迎使用'){
				tab.tabs('close',t);
			} 
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t!='欢迎使用'){
				tab.tabs('close',t);
			}  
		});
		return false;
	});
 
}

function editUserPwd() {
	var buttons = [ {
		iconCls : 'icon-ok',
		text : '确定',
		btnClick : function(dialog, target) {
			if (!target("[id=editUserPwdForm]").form('validate')) {
				return;
			}  
			$.ajax({
				type : 'post',
				url : path + '/user/editPwd.do',
				data : target("[id=editUserPwdForm]").form('serialize'),
				dataType : 'json',
				success : function(resp) {
					if (!resp.result) {
						alertMessage(resp.message, 'error');
						return;
					} 
					alertMessage(resp.message, 'info');
					dialog.dialog('destroy'); 
				}
			});
		}
	} ];

	alertWindow({
		title : '修改密码',
		width : 300,
		height : 250,
		url : path+'/user/editPwdPage.do',
		buttons : buttons
	});
}

function getExpireCountData() {
    $.ajax({
        type : 'get',
        url : path + '/getExpireCount.do',
        dataType : 'json',
        success : function(data) {
			console.log(data.monthCount)
			setData(data.monthCount,data.allCount)
            // 使用刚指定的配置项和数据显示图表。
            expireCount.setOption(option);
        }
    });
}


// 基于准备好的dom，初始化echarts实例
var expireCount = echarts.init(document.getElementById('expireCount'));


function setData(monthCount,allCount) {
    // 指定图表的配置项和数据
     option = {
         title : {
             text: '表具到期情况统计',
             subtext:  '三个月即将到期的表具：' + monthCount + '\n'+'所有已到期表具：'+ allCount,
			 x:'center'
         },
         tooltip : {
             trigger: 'item',
             formatter: "{a} <br/>{b} : {c} ({d}%)"
         },
        series : [
            {
                name: '表具到期统计',
                type: 'pie',
                radius: '55%',
                data:[
                    {value:monthCount, name:'三个月即将到期的表具'},
                    {value:allCount, name:'所有已到期表具'},
                ],
            //    roseType: 'angle',
                itemStyle: {
                    normal: {
                        //shadowBlur: 200,
                        // shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
}