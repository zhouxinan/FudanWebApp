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
		$(this).html('取消关注');
	} else {
		sendfollow('defollow');
		$(this).html('关注');
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
		dataType : "json"
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

checkfollow();