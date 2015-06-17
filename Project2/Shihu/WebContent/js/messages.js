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
	sendRequest('getAllSentMessages');
});
$("#messagesRead").click(function() {
	sendRequest('getAllReadMessages');
});
$("#messagesUnread").click(function() {
	sendRequest('getAllUnreadMessages');
});

function appendMessage(data) {
	$("#messagesDiv").html("");
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
	$("#messagesDiv").append(newDiv);
}

//$("#messagesUnread").trigger("click");
