document.getElementById("regExpButton").onclick = regExp;
function regExp(){
	/* Demo code */
	var content = document.getElementById("content").value;
	var div = document.getElementById("showHtmlDiv");

	var reg1 = /^[0-9]+$/;

	//Finish the following regular expression(reg2,reg3,reg4).

	var reg2 = /^[a-zA-Z]\w*$/;
	var reg3 = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+((cn)|(com))$/;
	var reg4 = /^[\u4e00-\u9fa5]+$/;

	if (reg1.test(content)){
		div.innerHTML = "<font color='green'>全为数字</font>";
	} else if (reg2.test(content)){
		div.innerHTML = "<font color='green'>以字母开头、可带数字、\"_\"的字串。</font>";
	} else if (reg3.test(content)){
		div.innerHTML = "<font color='green'>邮箱地址</font>";
	} else if (reg4.test(content)){
		div.innerHTML = "<font color='green'>全为中文</font>";
    } else {
		div.innerHTML = "<font color='green'>其他格式</font>";
	}
}
