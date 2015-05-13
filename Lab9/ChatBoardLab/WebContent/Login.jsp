<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login</title>
	<link rel="stylesheet" type="text/css" href="css/Login.css">
	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
</head>

<body>
	<div id="container">
		<div id="back">
		</div>
		<div id="content">
			<form method="post" action="login">
				<input type="text" name="username"  class="text_input"  placeholder="username" id="name"/>
				<br />
				<input type="password" name="password"  placeholder="password" class="text_input"/>
				<% 
					if(request.getSession()!=null && (String)request.getSession().getAttribute("error") != null){
						request.getSession().removeAttribute("error");
				%>
					<br /><span id="error">用户名，密码错误</span>
				<% }%>
				<input type="submit"  value="登陆" id="login" />
			</form>
		</div>
	</div>
</body>
</html>