<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect("login.jsp");
		return;
	}
%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/profile.css" />
<link type="text/css" rel="stylesheet" href="css/messages.css" />
<script src="lib/jquery-2.1.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>识乎 - 私信</title>
</head>
<body>
	<jsp:include page="navigator.jsp" />
	<div id="content">
		<div id="contentWrapper">
			<div id="tabBar" class="columnDiv">
				<div class="tab active" id="messagesUnread">未读</div>
				<div class="tab" id="messagesRead">已读</div>
				<div class="tab" id="messagesSent">已发</div>
				<div class="tab" id="newMessage">新私信</div>
			</div>
			<div id="messagesDiv" class="columnDiv">
				<div>
					收信人：
					<input type="text" id="receiverUsername" class="standardInput" placeholder="想发给谁呢？">
				</div>
				<textarea id="messageContent" placeholder="我来告诉你一点人生的经验……"></textarea>
				<div id="sendMessageErrorMessageDiv"></div>
				<button id="sendMessageButton" class="standardButton">发送</button>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/messages.js"></script>
</body>
</html>