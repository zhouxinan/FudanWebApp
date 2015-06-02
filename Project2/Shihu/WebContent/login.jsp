<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/base.css" />
	<link type="text/css" rel="stylesheet" href="css/login.css" />
	<script type="text/javascript" src="js/login.js"></script>
	<meta charset="utf-8" />
	<!-- For iPhone to display normally -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>识乎 - 提高自己的知识水平</title>
</head>
<body>
	<div id="top">
		<div id="topWrapper">
			<div id="logoDiv">
				<div id="title">识乎</div>
				<div>提高自己的知识水平</div>
			</div>
			<div id="loginAndRegDiv" class="noSelect">
				<div id="switchFormController" onclick="switchForm()">
					<div><img src="img/icon/arrow-right.png" /></div>
					<div id="switchFormControllerText">登陆</div>
				</div>
				<div id="regDiv" class="formDiv">
					<form name="regForm" id="regForm" method="post" action="LoginServlet">
						<input type="hidden" name="action" value="register" />
						<input type="text" id="regUsername" name="username" placeholder="用户名" />
						<input type="text" id="regEmail" name="email" placeholder="邮箱" />
						<input type="password" id="regPassword" name="password" placeholder="密码" />
					</form>
				</div>
				<div id="loginDiv" class="formDiv">
					<form name="loginForm" id="loginForm" method="post" action="LoginServlet">
						<input type="hidden" name="action" value="login" />
						<input type="text" id="loginEmail" name="email" placeholder="邮箱" />
						<input type="password" id="loginPassword" name="password" placeholder="密码" />
					</form>
				</div>
				<div id="errorMessage">
				<% 
					if(request.getSession()!=null && (String)request.getSession().getAttribute("error") != null){
						out.print(request.getSession().getAttribute("error"));
						request.getSession().removeAttribute("error");
					}
				%>
				</div>
				<button id="submitButton" onclick="register()">注册</button>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div id="content">
		<div id="contentWrapper">
			<div id="popularUserDiv">
				<div class="contentTitle">热门用户</div>
				<div id="popularUserList">
						<div><a href="#"><img src="img/avatar/user_1.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_2.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_3.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_4.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_5.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_6.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_7.jpg" /></a></div>
						<div><a href="#"><img src="img/avatar/user_8.jpg" /></a></div>
				</div>
				<div class="clear"></div>
			</div>
			<div id="popularQuestionDiv">
				<div class="contentTitle">热门问题</div>
				<div id="popularQuestionList">
					<div><a href="#">美国的华莱士到底有多高？</a></div>
					<div><a href="#">香港记者到底跑得有多快？</a></div>
					<div><a href="#">江主席究竟掌握了几门语言？</a></div>
					<div><a href="#">李光耀眼里的长者是什么样的？</a></div>
					<div><a href="#">为什么有那么多人膜蛤？</a></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>