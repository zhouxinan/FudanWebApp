<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%@ page import="org.json.*"%>
<%
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	Dao dao = Dao.getInstance();
	List<JSONObject> trendEntryList = dao
			.getTrendEntryListForUser(user);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<title>识乎 - 主页</title>
</head>
<body>
	<jsp:include page="navigator.jsp" />
	<div id="content">
		<div id="contentWrapper">
			<div id="leftColumn">
				<%
					for (JSONObject obj : trendEntryList) {
				%>
				<div class="columnDiv">
					<div>
						<a href="question.jsp?id=<%=obj.get("questionID")%>"><%=obj.get("questionTitle")%></a>
					</div>
					<%
						if (obj.get("isQuestion").equals("0")) {
					%>
					<div class="replyContent"><%=obj.get("content")%></div>
					<%
						} else {
					%>
					<%
						}
					%>
					<div class="replyTime"><%=obj.get("time")%></div>
				</div>
				<%
					}
				%>
			</div>
			<jsp:include page="popularQuestionAndUser.jsp" />
		</div>
	</div>
</body>
</html>