function setReplyCountDivAction() {
	var $replyCountDiv = $(".replyCountDiv");
	var $replyListDiv = $(".replyListDiv");
	$replyCountDiv.click(function() {
		var answerID = $(this).next().html();
		getReply(answerID, $(this).parent().next());
		for (var i = 0; i < $replyCountDiv.length; i++) {
			if (this == $replyCountDiv[i]) {
				if ($replyListDiv.eq(i).attr("class") == "replyListDiv") {
					$replyListDiv.eq(i).attr("class", "replyListDiv active");
				} else {
					$replyListDiv.eq(i).attr("class", "replyListDiv");
				}
			}
		}
	});
};

function sendReply(answerID, content) {
	if (content == "") {
		$("#sendAnswerResponseMessageDiv").html("请输入回答！");
		return;
	}
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'addReply',
			answerID : $("#questionID").html(),
			content : content
		},
		dataType : "json",
		success : function(data) {
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getReply(answerID, replyListDiv) {
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'getReply',
			answerID : answerID
		},
		dataType : "json",
		success : function(data) {
			processReplyData(data, replyListDiv);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function processReplyData(data, replyListDiv) {
	replyListDiv
			.html('<div class="replyDiv new"><input type="text" id="newReplyContent" placeholder="说你什么好呢……" /><button class="submitButton" id="sendReplyButton">发表评论</button></div>');
	if (data != null) {
		for (i = 0; i < data.length; i++) {
			addReplyToPage(data[i].userID, data[i].avatarPath,
					data[i].username, data[i].content, data[i].replyTime,
					replyListDiv);
		}
	}
}

function addReplyToPage(userID, avatarPath, username, content, replyTime,
		replyListDiv) {
	var replyDiv = document.createElement('div');
	replyDiv.setAttribute('class', 'replyDiv');
	replyDiv.innerHTML = '<div><a href="profile.jsp?id='
			+ userID
			+ '"><img class="userAvatar" src="img/avatar/'
			+ avatarPath
			+ '" /></a></div><div class="replyData"><div class="userName"><a href="profile.jsp?id='
			+ userID + '">' + username + '</a></div><div class="replyContent">'
			+ content + '</div><div class="replyTime">' + replyTime
			+ '</div></div><div class="clear"></div>';
	replyListDiv.prepend(replyDiv);
}

function sendAnswer() {
	var content = $("#newAnswerContent").val();
	if (content == "") {
		$("#sendAnswerResponseMessageDiv").html("请输入回答！");
		return;
	}
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'addAnswer',
			questionID : $("#questionID").html(),
			content : content
		},
		dataType : "json",
		success : function(data) {
			addAnswerToPage(data.userID, data.avatarPath, data.username,
					data.motto, data.content, data.answerTime, data.answerID,
					data.replyCount);
			$("#newAnswerContent").val("");
			$("#sendAnswerResponseMessageDiv").html("回答成功！");
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getAnswer(startingIndex, numberOfAnswers) {
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'getAnswer',
			questionID : $("#questionID").html(),
			startingIndex : startingIndex,
			numberOfAnswers : numberOfAnswers
		},
		dataType : "json",
		success : function(data) {
			processAnswerData(data);
			setReplyCountDivAction();
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function processAnswerData(data) {
	for (i = 0; i < data.length; i++) {
		addAnswerToPage(data[i].userID, data[i].avatarPath, data[i].username,
				data[i].motto, data[i].content, data[i].answerTime,
				data[i].answerID, data[i].replyCount);
	}
	startingIndex += data.length;
	if (data.length < 5) {
		$("#getMoreAnswersButton").html("没有更多答案了");
		$("#getMoreAnswersButton").unbind("click");
		$("#getMoreAnswersButton").css({
			"color" : "#bdc3c7",
			"border" : "1px solid #bdc3c7",
			"cursor" : "default"
		});
	}
}

function addAnswerToPage(userID, avatarPath, username, motto, content,
		answerTime, answerID, replyCount) {
	var columnDiv = document.createElement('div');
	columnDiv.setAttribute('class', 'columnDiv');
	columnDiv.innerHTML = '<div class="userInfoDiv"><a href="profile.jsp?id='
			+ userID
			+ '"><img class="userAvatar" src="img/avatar/'
			+ avatarPath
			+ '" /><span class="userName">'
			+ username
			+ ' </span></a><span class="userSignature">'
			+ motto
			+ '</span></div><div class="answer"><p>'
			+ content
			+ '</p></div><div class="answerMetadata"><div>'
			+ answerTime
			+ '</div> <div class="replyCountDiv noSelect">评论 ('
			+ replyCount
			+ ')</div><div class="answerID">'
			+ answerID
			+ '</div></div><div class="replyListDiv"><div class="replyDiv new"><input type="text" id="newReplyContent" placeholder="说你什么好呢……" /><button class="submitButton" id="sendReplyButton">发表评论</button></div></div>';
	$("#getMoreAnswersDiv").before(columnDiv);
}

$(".sendReplyButton").click(function() {
	alert($(this).prev().val());
});

$("#getMoreAnswersButton").bind('click', function() {
	getAnswer(startingIndex, 5);
});

$("#sendAnswerButton").bind('click', function() {
	sendAnswer();
});

startingIndex = 0;

$("#getMoreAnswersButton").click();
