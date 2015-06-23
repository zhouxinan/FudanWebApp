<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%
	String keyword = request.getParameter("keyword");
	Dao dao = Dao.getInstance();
	List<Question> questionList = dao.searchQuestionByKeyword(keyword);
	List<User> userList = dao.searchUsernameByKeyword(keyword);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/tabBar.css" />
<link type="text/css" rel="stylesheet" href="css/search.css" />
<title>识乎 - 搜索</title>
</head>
<body>
	<jsp:include page="navigator.jsp" />
	<div id="content">
		<div id="contentWrapper">
			<div id="leftColumn">
				<div id="tabBar" class="columnDiv">
					<div class="tab active">问题</div>
					<div class="tab">用户</div>
				</div>
				<div>
					<div class="tabPane active">
						<div class="columnDiv">
							<%
								for (Question question : questionList) {
							%>
							<div class="questionTitleDiv">
								<a href="question.jsp?id=<%=question.getQuestionID()%>"><%=question.getTitle()%></a>
							</div>
							<%
								}
							%>
						</div>
					</div>
					<div class="tabPane">
						<%
							for (User user : userList) {
						%>
						<div class="columnDiv">
							<div class="bigUserAvatarDiv">
								<a href="profile.jsp?id=<%=user.getUserID()%>"> <img
									class="bigUserAvatar"
									src="img/avatar/<%=user.getAvatarPath()%>" />
								</a>
							</div>
							<div class="userName">
								<a href="profile.jsp?id=<%=user.getUserID()%>"><%=user.getUsername()%></a>
							</div>
							<div class="userSignature"><%=user.getMotto()%></div>
						</div>
						<%
							}
						%>
					</div>
				</div>
			</div>
			<jsp:include page="popularQuestionAndUser.jsp" />
		</div>
	</div>
	<script type="text/javascript" src="js/search.js"></script>
</body>
</html>