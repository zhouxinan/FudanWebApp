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
				<a href="profile.jsp"><div><span><img src="img/icon/user.png" class="icon" />我的主页</span></div></a>
				<a href="messages.jsp"><div><span><img src="img/icon/envelop.png" class="icon" />私信</span></div></a>
				<a href="settings.jsp"><div><span><img src="img/icon/cog.png" class="icon" />设置</span></div></a>
				<a href="LoginServlet?action=logout"><div><span><img src="img/icon/exit.png" class="icon" />退出</span></div></a>
			</div>
			<% }%>
		</div>
	</div>
</div>