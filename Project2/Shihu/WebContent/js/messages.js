$(function setTabAction() {
	var $tab = $(".tab");
	$tab.click(function() {
		for (var i = 0; i < $tab.length; i++) {
			if (this == $tab[i]) {
				$tab.attr("class", "tab");
				$(this).attr("class", "tab active");
			}
		}
	});
});

function sendRequest(actionName) {
	$.ajax({
		type : 'POST',
		url : 'MessageServlet',
		data : {
			action : actionName,
		},
		dataType : "json",
		success : function(data) {
			appendMessage(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

$("#messagesSent").click(function() {
	$("#viewMessageDiv").removeClass("hidden");
	sendRequest('getAllSentMessages');
	$("#newMessageDiv").addClass("hidden");
});

$("#messagesRead").click(function() {
	$("#viewMessageDiv").removeClass("hidden");
	sendRequest('getAllReadMessages');
	$("#newMessageDiv").addClass("hidden");
});

$("#messagesUnread").click(function() {
	$("#viewMessageDiv").removeClass("hidden");
	sendRequest('getAllUnreadMessages');
	$("#newMessageDiv").addClass("hidden");
});

$("#newMessage").click(function() {
	$("#viewMessageDiv").addClass("hidden");
	$("#newMessageDiv").removeClass("hidden");
});

$("#sendMessageButton").click(function() {
	sendMessage();
});

function appendMessage(data) {
	$("#viewMessageDiv").html("");
	if (data != null) {
		for (i = 0; i < data.length; i++) {
			addMessage(data[i].userID, data[i].avatarPath, data[i].messageID,
					data[i].content, data[i].username);
		}
	}
}

function addMessage(userID, avatarPath, messageID, content, username) {
	var newDiv = document.createElement('div');
	newDiv.setAttribute('class', 'columnDiv');
	newDiv.innerHTML = '<div><a href="profile.jsp?id=' + userID
			+ '"><img class="userAvatar" src="img/avatar/' + avatarPath
			+ '" /><span class="userName">' + username
			+ '</span></a><a href="readMessage.jsp?id=' + messageID + '">'
			+ content + '</a></div>';
	$("#viewMessageDiv").append(newDiv);
}

function sendMessage() {
	var receiverUsername = $("#receiverUsername").val();
	if (receiverUsername == "") {
		$("#receiverUsername").attr("placeholder", "请输入收信人的用户名！");
		$("#receiverUsername").focus();
		return;
	}
	var content = $("#messageContent").val();
	if (content == "") {
		$("#messageContent").attr("placeholder", "请输入私信内容！");
		$("#messageContent").focus();
		return;
	}
	$.ajax({
		type : 'POST',
		url : 'MessageServlet',
		data : {
			action : 'addMessage',
			receiverUsername : receiverUsername,
			content : content
		},
		success : function(data) {
			if (data == "true") {
				$("#messagesSent").click();
			} else {
				$("#sendMessageErrorMessageDiv").html("你输入的用户名不存在！");
			}
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

// $("#messagesUnread").trigger("click");
