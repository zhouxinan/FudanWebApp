// 网页开启时获取最新一条信息
function getLastMessage() {
	$.ajax({
		type : 'POST',
		url : "MessageServlet",
		data : {
			action : 'getLastMessage',
		},
		dataType : "json",
		success : function(data) {
			if (data == null) {
				lastReceivedMessageID = 0;
			} else {
				lastReceivedMessageID = data.messageID;
				addMsg(data.avatar, data.username, data.time, data.messageText);
			}
		},
		error : function() {
			handleConnectionError();
		}
	});
}

// 定义获取消息的函数
function getmsg() {
	$.ajax({
		type : 'POST',
		url : "MessageServlet",
		data : {
			action : 'get',
			lastReceivedMessageID : lastReceivedMessageID
		},
		dataType : "json",
		success : function(data) {
			appendmsg(data);
		},
		error : function() {
			handleConnectionError();
		}
	});
}

// 处理返回的json数据
function appendmsg(data) {
	if (data != null) {
		for (i = 0; i < data.length; i++) {
			addMsg(data[i].avatar, data[i].username, data[i].time, data[i].messageText);
		}
		if (data.length > 0) {
			lastReceivedMessageID = data[data.length - 1].messageID;
		}
	}
}

// 定义发送消息函数
function sendmsg() {
	var messageText = $('.enterbox > textarea').val();
	$('.enterbox > textarea').val("");
	$.ajax({
		type : 'POST',
		url : "MessageServlet",
		data : {
			action : 'send',
			username : username,
			messageText : messageText,
			lastReceivedMessageID : lastReceivedMessageID
		},
		dataType : "json",
		success : function(data) {
			appendmsg(data);
		},
		error : function() {
			handleConnectionError();
		}
	});
}
// 自动获取消息，为getmsg函数设置一个定时器。推荐使用js的setInterval函数

function handleConnectionError() {
	alert("Connection error!");
	clearInterval(timer);
}

/* Do not modify following parts */

// 调用addMsg函数来显示信息
function addMsg(pic, name, date, detail) {
	var content = document.createElement('div');
	content.className = "msg";
	content.innerHTML = '<img src="' + pic + '" title="' + name + '" alt="'
			+ name + '" class="pic"><span class="brf">' + name + ':&nbsp; '
			+ date + '</span><span class="detail">' + detail + '</span>';
	var msgbox = document.getElementById("msgbox");
	msgbox.insertBefore(content, msgbox.lastChild);
	// 自动滚动到最下
	msgbox.scrollTop = msgbox.scrollHeight;
}

// 按Ctrl+Enter键发送
window.onkeyup = function(e) {
	var ev = e || window.event;
	if (ev.ctrlKey && ev.keyCode == 13) {
		sendmsg();
	}
}

do {
	username = prompt("请输入你的用户名");
} while (username == '' | username == null);

getLastMessage();

timer = window.setInterval(getmsg, 1000);