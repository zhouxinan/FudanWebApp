<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="bean.*" %>
<%@ page import="dao.*" %>
    <%
   		User user = (User)request.getSession().getAttribute("user");
   		if (user == null) {
   			response.sendRedirect("login.jsp");
   			return;
   		}
   		String id = request.getParameter("id");
   		User currentUser;
   		if (id != null && !id.equals("" + user.getUserID())) {
   			currentUser = Dao.getInstance().getUserByID(id);
   		} else {
   			currentUser = user;
   		}
   		if (currentUser == null) { // It will only happen if id is invalid.
   			response.sendRedirect("404.jsp");
   			return;
   		}
   %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/base.css" />
	<link type="text/css" rel="stylesheet" href="css/layout.css" />
	<link type="text/css" rel="stylesheet" href="css/profile.css" />
	<script src="lib/jquery-2.1.3.min.js"></script>
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
					<img id="myBigAvatar" src="img/avatar/<%=currentUser.getAvatarPath() %>" />
				</div>
				<% if (id != null && !id.equals("" + user.getUserID())) {
				%>
				<div class="columnDiv">
					<button id="followButton">关注</button>
				</div>
				<%} %>
				<div class="columnDiv" id="myInfoDiv">
					<div id="userIDDiv"><%=(id!=null)?id:user.getUserID() %></div>
					<div class="myInfoRowDiv">
						<img src="img/icon/user_green.png" class="icon" />
						<div><%=currentUser.getUsername() %></div>
					</div>
					<div class="myInfoRowDiv">
						<img src="img/icon/signature.png" class="icon" />
						<div><%=currentUser.getMotto() %></div>
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
					<div class="avatarList" id="followingListDiv">
					</div>
					<div class="clear"></div>
				</div>
				<div class="columnDiv">
					<div class="rightColumnTitle">
						最近被他们关注
					</div>
					<div class="avatarList" id="followerListDiv">
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
	<script type="text/javascript" src="js/profile.js"></script>
</body>
</html>