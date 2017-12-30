//特殊字符校验
function regSpecialCharacter(param){
	var keyword = new RegExp(
	"[\\ ,\\，,\\。,\\.,\\`,\\~,\\!,\\！,\\@,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\.,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
	if(keyword.test(param)){
		return true;
	}else{
		return false;
	}
}
//中文字符校验
function regChinese(param){
	var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
	if(reg.test(param)){
		return true;
	}else{
		return false;
	}
}
function regNumber(param){
	//var reg = "^\\d+$";
	var reg = /^[1-9]+?[0-9]*$/;
	if(reg.test(param)){
		return true;
	}else{
		return false;
	}
}
function regNumberAndEng(param){
	var reg = /^[a-zA-Z0-9]+$/;
	if(reg.test(param)){
		return true;
	}else{
		return false;
	}
}
//时间对比
function duibi(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes <= lktimes) {
        return false;
    }
    else
        return true;

}

