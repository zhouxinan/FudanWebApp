<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.*" %>
    <%
   		User user = (User)request.getSession().getAttribute("user");
   		if (user == null) {
   			response.sendRedirect("login.jsp");
   			return;
   		}
   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>识乎 - 设置</title>
</head>
<body>
	<jsp:include page="navigator.jsp"/>
	
</body>
</html>