$(".clickbox").click(function() {
	window.location = $(this).find("a").attr("href");
	return false;
});

$.ajax({
	type : 'POST',
	url : "MessageServlet",
	data : {
		action : 'getUnreadMessageCount',
	},
	success : function(data) {
		if (data != "0") {
			$(".notificationBubble").html(data);
			$(".notificationBubble").css('display', 'block');
		}
	},
	error : function() {
		alert("Connection error!");
	}
});