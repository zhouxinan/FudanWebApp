<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="bean.*" %>
<%
		String username = "登陆";
		String avatarPath = "default.jpg";
   		User user = (User)request.getSession().getAttribute("user");
   		if (user != null) {
   			username = user.getUsername();
   			avatarPath = user.getAvatarPath();
   		}
   %>
<link type="text/css" rel="stylesheet" href="css/navigator.css" />
<link type="text/css" rel="stylesheet" href="css/base.css" />
<div id="navigator">
	<div id="navigatorWrapper">
		<div id="logo"><a href="index.jsp">识乎</a></div>
		<div id="searchDiv">
			<form method="post" action="">
				<input type="text" id="searchInput" name="searchInput" placeholder="搜索问题或用户" />
				<button type="submit" id="searchButton">搜索</button>
			</form>
		</div>
		<div id="newQuestionButton"><button>提问题</button></div>
		<div id="menuDiv">
			<div class="changeHoverBackground"><a href="index.jsp">首页</a></div>
			<div class="changeHoverBackground">话题</div>
			<div class="changeHoverBackground"><a href="discovery.jsp">发现</a></div>
		</div>
		<div id="userMenuDiv" class="changeHoverBackground">
			<div id="myAvatarAndNameDiv">
				<img id="myAvatar" src="img/avatar/<%=avatarPath %>" />
				<div id="myName"><a href="profile.jsp"><%=username %></a></div>
			</div>
			<% if (user != null) { %>
			<div id="userMenu">
				<div class="clickbox"><img src="img/icon/user.png" class="icon" /><a href="profile.jsp">我的主页</a></div>
				<div class="clickbox"><img src="img/icon/envelop.png" class="icon" /><a href="messages.jsp">私信</a></div>
				<div class="clickbox"><img src="img/icon/cog.png" class="icon" /><a href="settings.jsp">设置</a></div>
				<div class="clickbox"><img src="img/icon/exit.png" class="icon" /><a href="LoginServlet?action=logout">退出</a></div>
			</div>
			<% }%>
		</div>
	</div>
</div>
<script src="lib/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="js/navigator.js"></script>