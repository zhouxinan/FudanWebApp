window.onload = function() {
	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 + "px";

	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75) + "px";
	ds.scrollTop = ds.scrollHeight;

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900) / 2 + "px";

	init();
};

window.onresize = function() {
	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75) + "px";

	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 + "px";

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900) / 2 + "px";
};

window.onunload = function() {
	if (ws != null) {
		ws.close();
	}
}

var ws;
var username;
function init() {
	username = window.sessionStorage.getItem("username");

	// websocket 初始化
	if (window.WebSocket) {
		// 建立websock连接
		ws = new WebSocket(encodeURI('ws://localhost:8080/Chat/websocket/'
				+ encodeURIComponent(username)));

		ws.onopen = function() {
			console.log('socket start!!');
		}

		ws.onclose = function() {
			console.log('socket.end');
		}

		ws.onerror = function() {
			console.log('error!!');
		}

		ws.onmessage = function(event) {
			var message = JSON.parse(event.data);
			var type = message.type;
			var itemHtml;
			if (type == 'news') {
				itemHtml = produceNewsHtml(message.content);
			} else if (type == 'message') {
				itemHtml = produceMessageHtml(message.username,
						message.content, message.time);
			}
			var display = document.getElementById("display");
			display.innerHTML = display.innerHTML + itemHtml;
			display.scrollTop = display.scrollHeight;
		}
	}

	// 设置文件上传的按钮
	$('#photo').on('click', function() {
		$('#file').trigger('click');
	});
	
	setFileTypeValidator();
}

function setFileTypeValidator() {
	$("#file").on('change', function() {
		var file = document.getElementById("file").files[0];
		if (file.type.match('image.*')) {
			alert("You have successfully selected an image file!");
		} else {
			alert("You can only choose an image file! Please try again.");
			resetFileInput();
		}
	});
}

// 发送文本
function sendMessage(content) {
	var time = new Date().toLocaleString();
	var message = {
		'type' : 'message',
		'content' : content,
		'time' : time
	};
	ws.send(JSON.stringify(message));
	var file = document.getElementById("file").files[0];
	if (file != undefined) {
		var fileUploadMessage = {
			type : 'file',
			fileName : file.name
		};
		ws.send(JSON.stringify(fileUploadMessage));
		var reader = new FileReader();
		var rawData = new ArrayBuffer();
		reader.onload = function(e) {
			rawData = e.target.result;
			ws.send(rawData);
			ws.send("end");
		}
		reader.readAsArrayBuffer(file);
	} else {
		ws.send("end");
	}
	resetFileInput();
}

// 接受服务器文本并展示
function produceMessageHtml(username, content, date) {
	var ret = "<div class='noteItem'> ";
	ret += "<img class='user-photo' src='img/user.jpg'/> ";
	ret += "<div class='blog-content'> <span class='blog-name'>" + username
			+ "</span> : ";
	ret += "<span class='text-content' >" + content + "</span>";
	ret += "<div class='info'><span class='time'>" + date + "</span>";
	ret += "<span class='agent'>来自网页</span>";
	ret += "<span class='operation'>转发</span></div>";
	ret += "</div>";
	ret += "<div class='clear' ></div></div>";
	return ret;
}

// 接受用户上线或者下线信息并展示
function produceNewsHtml(content) {
	var ret = "<div class='newsItem'> ";
	ret += "<div class='centerNews'> " + content + "</div></div>";
	return ret;
}

document.getElementById("submit").onclick = function() {
	var promote = document.getElementById("promote").innerHTML;
	if (Number(promote) < 0) {
		return;
	}
	var text = document.getElementById("text");
	var content = text.value;
	text.value = "";
	content = content.replace(/\(F_(\d+).gif\)/g,
			'<img src="./img/faces/F_$1.gif">'); // 正则表达式替换
	document.getElementById("promote").innerHTML = 140;
	if (content.length > 0) {
		sendMessage(content);
	}
};

function resetFileInput() {
	document.getElementById("reset").innerHTML = '<input type="file" class="hiden" id="file">';
	setFileTypeValidator();
}

/** ------------- 以下全部为lab6所实现，大家无需关心--------------------------------- */
document.getElementById("text").oninput = function() {
	var text = document.getElementById("text");
	var remain = 140 - text.value.length;
	document.getElementById("promote").innerHTML = remain;
}

document.getElementById("face").onclick = function() {
	var faceHtml = "<table>";
	for (var i = 0; i < 4; i++) {
		faceHtml += "<tr>";
		for (var j = 0; j < 10; j++) {
			var id = i * 10 + j + 1;
			if (id == 40) {
				faceHtml += "<td>&nbsp;</td>"
				continue;
			}
			faceHtml += "<td id='F_" + id + ".gif' background='img/faces/F_"
					+ id + ".gif' onclick='chooseFace(this.id)'>&nbsp;</td>";
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

function chooseFace(id) {
	faceDiv.style.display = "none";
	var text = document.getElementById("text");
	text.value = text.value + "(" + id + ")";
	var remain = 140 - text.value.length;
	document.getElementById("promote").innerHTML = remain;
}

function deleteMethod(id) {
	document.getElementById("messageID").value = id;
	document.getElementById("deleteMessage").submit();
}
