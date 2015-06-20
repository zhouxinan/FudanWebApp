$(".clickbox").click(function() {
	window.location = $(this).find("a").attr("href");
	return false;
});

$("#searchButton").click(function() {
	var keyword = $("#searchInput").val();
	if (keyword == "") {
		$("#searchInput").attr("placeholder", "请输入关键词");
		$("#searchInput").focus();
		return;
	}
	window.location = "search.jsp?keyword=" + encodeURIComponent(keyword);
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