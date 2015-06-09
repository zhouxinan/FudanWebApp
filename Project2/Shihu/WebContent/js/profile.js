$(function setTabAction() {
	var $tab = $(".tab");
	var $tabPane = $(".tabPane");
	$tab.click(function() {
		for (var i = 0; i < $tab.length; i++) {
			if (this == $tab[i]) {
				$tab.attr("class", "tab");
				$(this).attr("class", "tab active");
				$tabPane.attr("class", "tabPane");
				$tabPane.eq(i).attr("class", "tabPane active");
			}
		}
	});
});

$("#followButton").click(function() {
	if ($(this).html() == '关注') {
		sendfollow('follow');
	} else {
		sendfollow('defollow');
	}
});

function sendfollow(action) {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : action,
			toUserID : $("#userIDDiv").html()
		},
		dataType : "json",
		success : function(data) {
			processData(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function checkfollow() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'checkFollow',
			toUserID : $("#userIDDiv").html()
		},
		success : function(data) {
			if (data == 'true') {
				$("#followButton").html('取消关注');
			} else {
				$("#followButton").html('关注');
			}
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getFollowers() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'getFollowers',
			toUserID : $("#userIDDiv").html()
		},
		dataType : "json",
		success : function(data) {
			$("#followerListDiv").html("");
			appendAvatar(data, 'followerListDiv');
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getFollowing() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'getFollowing',
			fromUserID : $("#userIDDiv").html()
		},
		dataType : "json",
		success : function(data) {
			appendAvatar(data, 'followingListDiv');
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function appendAvatar(data, avatarListDivID) {
	if (data != null) {
		for (i = 0; i < data.length; i++) {
			addAvatar(data[i].userID, data[i].avatarPath, avatarListDivID);
		}
	}
}

function addAvatar(fromUserID, avatarPath, avatarListDivID) {
	var content = document.createElement('a');
	content.href = 'profile.jsp?id=' + fromUserID;
	content.innerHTML = '<img src="img/avatar/' + avatarPath
			+ '" class="userAvatar">';
	$("#" + avatarListDivID).append(content);
}

function getFollowingCount() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'getFollowingCount',
			fromUserID : $("#userIDDiv").html()
		},
		success : function(data) {
			$("#followingCount").html(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getFollowerCount() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'getFollowerCount',
			toUserID : $("#userIDDiv").html()
		},
		success : function(data) {
			$("#followerCount").html(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}

function getFollowInfo() {
	$.ajax({
		type : 'POST',
		url : "FollowServlet",
		data : {
			action : 'getFollowInfo',
			userID : $("#userIDDiv").html()
		},
		dataType : "json",
		success : function(data) {
			processData(data);
		},
		error : function() {
			alert("Connection error!");
		}
	});
}


function processData(data) {
	if (data.isFollowed == true) {
		$("#followButton").html('取消关注');
	} else {
		$("#followButton").html('关注');
	}
	$("#followerCount").html(data.followerCount);
	$("#followingCount").html(data.followingCount);
	$("#followerListDiv").html("");
	$("#followingListDiv").html("");
	appendAvatar(data.followingList, 'followingListDiv');
	appendAvatar(data.followerList, 'followerListDiv');
}

getFollowInfo();