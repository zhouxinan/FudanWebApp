
window.onload = function(){
	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75)+"px";
	ds.scrollTop = ds.scrollHeight;

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
}

window.onresize = function(){
	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75)+"px";

	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
}

document.getElementById("submit").onclick = function(){
	var promote = document.getElementById("promote").innerHTML;
	if(Number(promote) < 0){
		return ;
	}
	var text = document.getElementById("text");
	var content = text.value;
	text.value = "";
//	content = transport(content);
	content = content.replace(/\(F_(\d+).gif\)/g, '<img src="./img/faces/F_$1.gif">');  //正则表达式替换
	var display = document.getElementById("display");
//	display.innerHTML = display.innerHTML + produceHtml(content);
	display.scrollTop = display.scrollHeight;
	document.getElementById("promote").innerHTML = 140;
	
	var form = document.getElementById("addMessage");
	document.getElementById("message").value=content;
	form.submit();
};

function produceHtml(text){
	var ret = "<div class='noteItem'> ";
	ret += "<img class='user-photo' src='img/user.jpg'/> ";
	ret += "<div class='blog-content'> <span class='blog-name'>囧囧先生</span> : ";
	ret += "<span class='text-content' >" + text +"</span>";
	ret += "<div class='info'><span class='time'>"+new Date().toLocaleString()+"</span>";
	ret += "<span class='agent'>来自网页</span>";
	ret += "<span class='operation'>转发</span></div>";
	ret += "</div>";
	ret += "<div class='clear' ></div></div>";
	return ret;
}

function transport(text){
	text = String(text);
	while(true){
		var index1 = text.indexOf("(");
		var index2 = text.indexOf(")");
		if(index1 == -1 || index2 == -1){
			return text;
		}
		var faceFile = text.slice(index1+1, index2);
		var replace = "<img src='img/faces/"+faceFile+"'></img>";
		text = text.replace("("+faceFile+")", replace);
	}
}

document.getElementById("text").oninput=function(){
	var text = document.getElementById("text");
	var remain = 140-text.value.length;
	document.getElementById("promote").innerHTML = remain;
}

document.getElementById("face").onclick = function(){
	var faceHtml = "<table>";
	for(var  i=0; i<4; i++){
		faceHtml += "<tr>";
		for(var j=0; j<10; j++){
			var id = i*10+j+1;
			if(id == 40){
				faceHtml += "<td>&nbsp;</td>"
				continue;
			}
			faceHtml += "<td id='F_"+id+".gif' background='img/faces/F_"+id+".gif' onclick='chooseFace(this.id)'>&nbsp;</td>";
		}
		faceHtml += "</tr>";
	}
	faceHtml += "</table>";
	var faceDiv = document.getElementById("faceDiv");
	faceDiv.innerHTML = faceHtml;
	faceDiv.style.display = "inherit";
	faceDiv.style.position = "absolute";
	faceDiv.style.top = "45px";
	faceDiv.style.left = "0px";
	faceDiv.style.background = "#eee";
}

function chooseFace(id){
	faceDiv.style.display = "none";
	var text = document.getElementById("text");
	text.value = text.value + "("+id+")";
	var remain = 140 - text.value.length;
	document.getElementById("promote").innerHTML = remain;
}

