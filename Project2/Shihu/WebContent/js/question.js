$(function setTabAction() {
	var $replyCount = $(".replyCount");
	var $replyListDiv = $(".replyListDiv");
	$replyCount.click(function() {
		for (var i = 0; i < $replyCount.length; i++) {
			if (this == $replyCount[i]) {
				if ($replyListDiv.eq(i).attr("class") == "replyListDiv") {
					$replyListDiv.eq(i).attr("class", "replyListDiv active");
				} else {
					$replyListDiv.eq(i).attr("class", "replyListDiv");
				}
			}
		}
	});
});

function getReply(answerID) {
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'getReply',
			answerID : answerID
		},
		dataType : "json",
		success : function(data) {
			appendreply(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function appendReply(data) {
	if (data != null) {
		for (i = 0; i < data.length; i++) {
			addReply(data[i].userID, data[i].avatarPath, data[i].username,
					data[i].content, data[i].replyTime);
		}
	}
}

function addReply(userID, avatarPath, username, content, replyTime) {
	var newDiv = document.createElement('div');
	newDiv.setAttribute('class', 'columnDiv');
	newDiv.innerHTML = '<div class="userInfoDiv"><a href="profile.jsp?id='
			+ userID + '"><img class="userAvatar" src="img/avatar/'
			+ avatarPath + '" /><span class="userName">' + username
			+ '</span></a><span class="userSignature">' + motto
			+ '</span></div><div class="answer"><p>' + content
			+ '</p></div><div class="answerMetadata"><span>' + answerTime
			+ '</span> <span class="replyCount noSelect">评论(' + replyCount
			+ ')</span></div><div class="replyListDiv">';
	$("#leftColumn").append(newDiv);
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
	var newDiv = document.createElement('div');
	newDiv.setAttribute('class', 'columnDiv');
	newDiv.innerHTML = '<div class="userInfoDiv"><a href="profile.jsp?id='
			+ userID + '"><img class="userAvatar" src="img/avatar/'
			+ avatarPath + '" /><span class="userName">' + username
			+ '</span></a><span class="userSignature">' + motto
			+ '</span></div><div class="answer"><p>' + content
			+ '</p></div><div class="answerMetadata"><span>' + answerTime
			+ '</span> <span class="replyCount noSelect">评论(' + replyCount
			+ ')</span></div><div class="replyListDiv">';
	$("#getMoreAnswersDiv").before(newDiv);
}

$("#getMoreAnswersButton").bind('click', function() {
	getAnswer(startingIndex, 5);
});

startingIndex = 0;

$("#getMoreAnswersButton").click();
