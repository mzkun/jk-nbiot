// easyui datagrid 默认设置
$.fn.datagrid.defaults.pagination = true;//是否分页
$.fn.datagrid.defaults.border = true;//是否有边框
$.fn.datagrid.defaults.striped = true;//是否显示条纹行
$.fn.datagrid.defaults.fit = true;//表格是否填充
$.fn.datagrid.defaults.fitColumns = true;//列是否自动调整大小
$.fn.datagrid.defaults.rownumbers = true;//是否显示行号
$.fn.datagrid.defaults.pageNumber = 0;
$.fn.datagrid.defaults.pageSize = 0;
$.fn.datagrid.defaults.pageList = [ 10, 20, 30, 40, 50 ];//默认显示条数
$.fn.datagrid.defaults.singleSelect = true;//是否单选行

// easyui treegrid 默认设置
$.fn.treegrid.defaults.pagination = true;
$.fn.treegrid.defaults.border = true;
$.fn.treegrid.defaults.striped = true;
$.fn.treegrid.defaults.fit = true;
$.fn.treegrid.defaults.fitColumns = true;
$.fn.treegrid.defaults.rownumbers = true;
$.fn.datagrid.defaults.pageSize = 10;
$.fn.treegrid.defaults.pageList = [ 10, 20, 30, 40, 50 ];
$.fn.treegrid.defaults.singleSelect = true;



// treegrid数据支持平滑数据格式
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
	var options = $(this).data().treegrid.options;
	if (!options.parentField) {
		return data;
	}
	
	return treeDataFilter(data,options.idField,options.treeField,options.parentField);
}

// tree数据支持平滑数据格式
$.fn.tree.defaults.loadFilter = function(data, parentId) {
	var options = $(this).data().tree.options;
	if (!options.parentField) {
		return data;
	}
	
	return treeDataFilter(data,options.idField,options.treeField,options.parentField);
}

//jQuery 将form表单元素的值序列化成对象
$.extend($.fn.form.methods, {
	serialize : function(jq) {
		var arrayValue = $(jq[0]).serializeArray();
		var json = {};
		$.each(arrayValue, function() {
			var item = this;
			var value = "";

			if (item["value"] != null) {
				value = item["value"].replace(/(^\s*)|(\s*$)/g, "");
			}

			if (value == '---请选择---') {
				value = '';
			}

			if (json[item["name"]]) {
				json[item["name"]] = json[item["name"]] + "," + value;
			} else {
				json[item["name"]] = value;
			}
		});
		return json;
	},
	getValue : function(jq, name) {
		var jsonValue = $(jq[0]).form("serialize");
		return jsonValue[name];
	},
	setValue : function(jq, data) {
		return jq.each(function() {
			$(this).form("load", data);
		});
	}
});


//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉字
    CHS: {
        validator: function (value) {
        	var reg = /^[\u0391-\uFFE5]+$/;
           // return /^[\u0391-\uFFE5]+$/.test(value);
        	return !reg.test(value);
        },
        message: 'The input Chinese characters only.'
    },
    //移动手机号码验证
    Mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: 'Please enter your mobile phone number correct.'
    },
    //国内邮编验证
    ZipCode: {
        validator: function (value) {
            var reg = /^[0-9]\d{5}$/;
            return reg.test(value);
        },
        message: 'The zip code must be 6 digits and 0 began.'
    },
  //数字
    Number: {
        validator: function (value) {
            //var reg =/^[0-9]*$/;
            var reg = /^[1-9]+?[0-9]*$/;
            return reg.test(value);
        },
        message: '请输入正整数'
    },
    //英文、数字、下划线、—
    Batch: {
        validator: function (value) {
            var reg = /^[-_a-zA-Z0-9]+$/;
            return reg.test(value);
        },
        message: '请输入英文、数字、横线、下划线'
    },
    Integer : {
		validator : function(value) {
			return (/^(0|[1-9][0-9]*)$/.test(value));
		},
		message : '请输入整数!'
	},
	Default : {
		validator : function(value) {
			if (value == '---请选择---') {
				return false
			}
			return true;
		},
		message : '请选择!'
	},
	NumLetter:{
		validator:function(value){
			return /^[a-zA-Z0-9]+$/.test(value);
		},
		message:'请输入数字和字母'
	},
	SpecChar:{
		validator:function(value){
			var keyword = new RegExp("[\\ ,\\，,\\。,\\.,\\`,\\~,\\!,\\！,\\@,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\.,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
			return !keyword.test(value);
		},
		message:'不能含有特殊字符'
	},
  //数字
    number: {
        validator: function (value) {
            //var reg =/^[0-9]*$/;
            var reg = /^[0-9]+?[0-9]*$/;
            return reg.test(value);
        },
        message: '请输入数字'
    },
  //验证7位数字
    numberSeven: {
        validator: function (value) {
            //var reg =/^[0-9]*$/;
            var reg = /^[0-9]{7}$/;
            return reg.test(value);
        },
        message: '请输入7位数数字'
    },
	//字母数字
	EngNum: {
		validator: function (value) {
			var reg = /^[A-Za-z0-9]+$/;
			return reg.test(value);
		},
		message: '请输入英文、数字'
	}

})


/**
 * easyui 弹窗
 * @param options
 * 		title:窗口标题,
 * 		url:页面url,
 * 		contents:内容页面 url content 二选1
 * 		isFrame:是否以iframe打开url页面,默认false
 * 		width:窗口宽度,
 *  	height:窗口高度,
 *  	modal:模式窗体，默认为true,
 *  	buttons:按钮(取消按钮已经默认添加),事件名：btnClick(dialog,target)
 *  	onComplete:加载完成后事件(dialog, jq).
 *  	data:
 */
function alertWindow(options) {
	top.jQuery.createWin({
		title : options.title,
		url : options.url,
		data:options.data,
		contents:options.contents,
		width : options.width,
		height : options.height,
		modal : options.modal == null ? true : options.modal,
		buttons : options.buttons,
		onComplete:options.onComplete
	})
}

/**
 * easyui 消息提示框
 * @param msg 提示消息
 * @param icon 消息级别 
 * 		info(提示),
 * 		warning(警告), 
 * 		error(错误)
 * @param fn 确认按钮事件
 */
function alertMessage(msg, icon, fn) {
	top.jQuery.messager.alert('提示', msg, icon, function() {
		if (fn) {
			fn();
		}
	})
}

/***
 * easyui 消息确认框
 * @param msg 提示消息
 * @param okFn 确认按钮事件
 * @param cancleFn 取消按钮事件
 */
function alertConfirm(msg, okFn, cancleFn) {
	top.jQuery.messager.confirm('询问', msg, function(isOk) {
		if (isOk && okFn) {
			okFn();
		} else {
			if (cancleFn)
				cancleFn();
		}
	})
}




function treeDataFilter(data,idFiled,textFiled,parentField){
	idFiled = idFiled || 'id';
	textFiled = textFiled || 'text';
	parentField = parentField || 'parentId';

	var i, l, treeData = [], tmpMap = [];
	for (i = 0, l = data.length; i < l; i++) {
		tmpMap[data[i][idFiled]] = data[i];
	}
	for (i = 0, l = data.length; i < l; i++) {
		if (tmpMap[data[i][parentField]]
				&& data[i][idFiled] != data[i][parentField]) {
			if (!tmpMap[data[i][parentField]]['children'])
				tmpMap[data[i][parentField]]['children'] = [];
			data[i]['text'] = data[i][textFiled];
			tmpMap[data[i][parentField]]['children'].push(data[i]);
		} else {
			data[i]['text'] = data[i][textFiled];
			treeData.push(data[i]);
		}
	}
	return treeData;
}