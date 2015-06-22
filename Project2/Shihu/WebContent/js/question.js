function setReplyCountDivAction() {
	var $replyCountDiv = $(".replyCountDiv");
	$replyCountDiv.click(function() {
		var currentReplyListDiv = $(this).parent().next();
		if (currentReplyListDiv.attr("class") == "replyListDiv") {
			var answerID = $(this).next().html();
			getReply(answerID, currentReplyListDiv);
			currentReplyListDiv.attr("class", "replyListDiv active");
		} else {
			currentReplyListDiv.attr("class", "replyListDiv");
		}
	});
};

function sendReply(answerID, content, sendReplyButton) {
	if (content == "") {
		sendReplyButton.prev().attr("placeholder", "请输入回复！");
		sendReplyButton.prev().focus();
		return;
	}
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'addReply',
			answerID : answerID,
			content : content
		},
		dataType : "json",
		success : function(data) {
			processReplyData(data, sendReplyButton.parent().parent());
			setSendReplyButtonAction();
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
			setSendReplyButtonAction();
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function processReplyData(data, replyListDiv) {
	if (data == "-1") {
		replyListDiv
				.html('<div class="replyDiv new"><div class="errorMessageDiv">登录后才可以查看和发表评论</div></div>');
		return;
	}
	replyListDiv
			.html('<div class="replyDiv new"><input type="text" placeholder="说你什么好呢……" /><button class="submitButton sendReplyButton">发表评论</button></div>');
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
		$("#newAnswerContent").attr("placeholder", "请输入回答！");
		$("#newAnswerContent").focus();
		return;
	}
	var file = document.getElementById("file").files[0];
	if (file != undefined) {
		$("#questionID").val($("#questionIDDiv").html());
		$("#newAnswerForm").submit();
	} else {
		$.ajax({
			type : 'POST',
			url : 'QuestionServlet',
			data : {
				action : 'addAnswer',
				questionID : $("#questionIDDiv").html(),
				content : content
			},
			dataType : "json",
			success : function(data) {
				addAnswerToPage(data.userID, data.avatarPath, data.username,
						data.motto, data.content, data.answerTime,
						data.answerID, data.replyCount);
				$("#newAnswerContent").val("");
			},
			error : function() {
				alert("Connection error!");
			}
		});
	}
}

function getAnswer(startingIndex, numberOfAnswers) {
	$.ajax({
		type : 'POST',
		url : 'QuestionServlet',
		data : {
			action : 'getAnswer',
			questionID : $("#questionIDDiv").html(),
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
			+ userID + '"><img class="userAvatar" src="img/avatar/'
			+ avatarPath + '" /><span class="userName">' + username
			+ ' </span></a><span class="userSignature">' + motto
			+ '</span></div><div class="answer"><p>' + content
			+ '</p></div><div class="answerMetadata"><div>' + answerTime
			+ '</div> <div class="replyCountDiv noSelect">评论 (' + replyCount
			+ ')</div><div class="answerID hidden">' + answerID
			+ '</div></div><div class="replyListDiv"></div>';
	$("#getMoreAnswersDiv").before(columnDiv);
}

function setSendReplyButtonAction() {
	var $sendReplyButton = $(".sendReplyButton");
	var $answerID = $(".answerID");
	$sendReplyButton
			.click(function() {
				for (var i = 0; i < $sendReplyButton.length; i++) {
					if (this == $sendReplyButton[i]) {
						sendReply($answerID.eq(i).html(), $(this).prev().val(),
								$(this));
					}
				}
			});
}

function setFileTypeValidator() {
	$("#file").on('change', function() {
		var file = document.getElementById("file").files[0];
		if (!file.type.match('image.*')) {
			alert("你只能选择图片文件，请重新选择！");
			resetFileInput();
		}
	});
}

function resetFileInput() {
	document.getElementById("newAnswerForm").innerHTML = '<textarea id="newAnswerContent" name="newAnswerContent" placeholder="我来告诉你们人生的经验……"></textarea><input type="file" id="file" class="hidden" name="file" /> <input type="text" id="questionID" class="hidden" name="questionID" />';
	setFileTypeValidator();
}

$("#getMoreAnswersButton").bind('click', function() {
	getAnswer(startingIndex, 5);
});

$("#sendAnswerButton").bind('click', function() {
	sendAnswer();
});

startingIndex = 0;

$("#getMoreAnswersButton").click();

$("#uploadImgButton").bind('click', function() {
	$("#file").trigger('click');
});

setFileTypeValidator();