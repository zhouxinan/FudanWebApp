<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="bean.*" %>
    <%
   		User user = (User)request.getSession().getAttribute("user");
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
	<script src="lib/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/profile.js"></script>
	<meta charset="utf-8" />
	<!-- For iPhone to display normally -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>识乎 - 个人资料</title>
</head>
<body>
	<jsp:include page="navigator.jsp"/>
	<div id="content">
		<div id="contentWrapper">
			<div id="leftColumn">
				<div id="tabBar" class="columnDiv">
					<div class="tab active">提问</div>
					<div class="tab">回答</div>
				</div>
				<div id="myQuestionAndAnswerDiv">
					<div id="myQuestionDiv" class="tabPane active">
						<div class="columnDiv">
							<div><a href="#">声乐有几种唱法？</a></div>
							<div><a href="#">三个代表重要思想的内容是什么？</a></div>
							<div><a href="#">为什么有那么多人膜蛤？</a></div>
							<div><a href="#">我和华莱士到底谁更高？</a></div>
							<div><a href="#">为什么现在的新闻工作者动不动就喜欢搞个大新闻？</a></div>
						</div>
					</div>
					<div id="myAnswerDiv" class="tabPane">
						<div class="columnDiv">
							<div><a href="#">你读过最美的一句诗是什么</a></div>
							<div class="replyContent">苟利国家生死以，岂因祸福避趋之。</div>
							<div class="replyTime">2015.1.1 12:00</div>
						</div>
						<div class="columnDiv">
							<div><a href="#">你去过哪些西方国家？</a></div>
							<div class="replyContent">哪个西方国家我没去过？</div>
							<div class="replyTime">2015.1.1 12:00</div>
						</div>
						<div class="columnDiv">
							<div><a href="#">保持长寿的秘诀是什么？</a></div>
							<div class="replyContent">天天有人续。</div>
							<div class="replyTime">2015.1.1 12:00</div>
						</div>
					</div>
				</div>
			</div>
			<div id="rightColumn">
				<div class="columnDiv" id="myBigAvatarDiv">
					<img id="myBigAvatar" src="img/avatar/<%=user.getAvatarPath() %>" />
				</div>
				<div class="columnDiv" id="myInfoDiv">
					<div class="myInfoRowDiv">
						<img src="img/icon/user_green.png" class="icon" />
						<div><%=user.getUsername() %></div>
					</div>
					<div class="myInfoRowDiv">
						<img src="img/icon/signature.png" class="icon" />
						<div><%=user.getMotto() %></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="columnDiv" id="followerDiv">
					<div>
						<div class="bigNumber"><span>4</span></div>
						<div class="followerDivText"><span>Follower</span></div>
					</div>
					<div>
						<div class="bigNumber"><span>4</span></div>
						<div class="followerDivText"><span>Followed</span></div>
					</div>
				</div>
				<div class="columnDiv">
					<div class="rightColumnTitle">
						最近关注了
					</div>
					<div class="avatarList">
						<a href="#"><img src="img/avatar/user_1.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_2.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_3.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_4.jpg" class="userAvatar" /></a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="columnDiv">
					<div class="rightColumnTitle">
						最近被他们关注
					</div>
					<div class="avatarList">
						<a href="#"><img src="img/avatar/user_1.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_2.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_3.jpg" class="userAvatar" /></a>
						<a href="#"><img src="img/avatar/user_4.jpg" class="userAvatar" /></a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="columnDiv">
					<div id="author">© 2015 Zhou Xinan</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>