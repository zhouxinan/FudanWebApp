<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link type="text/css" rel="stylesheet" href="css/navigator.css" />
<div id="navigator">
	<div id="navigatorWrapper">
		<div id="logo">识乎</div>
		<div id="searchDiv">
			<form method="post" action="">
				<input type="text" id="searchInput" name="searchInput" placeholder="搜索问题或用户" />
				<button type="submit" id="searchButton">搜索</button>
			</form>
		</div>
		<div id="newQuestionButton"><button>提问题</button></div>
		<div id="menuDiv">
			<div class="changeHoverBackground">首页</div>
			<div class="changeHoverBackground">话题</div>
			<div class="changeHoverBackground">发现</div>
		</div>
		<div id="userMenuDiv" class="changeHoverBackground">
			<div id="myAvatarAndNameDiv">
				<img id="myAvatar" src="img/avatar/myAvatar.jpg" />
				<div id="myName">江泽民</div>
			</div>
			<div id="userMenu">
				<a href="profile.html"><div><span><img src="img/icon/user.png" class="icon" />我的主页</span></div></a>
				<a href="#"><div><span><img src="img/icon/envelop.png" class="icon" />私信</span></div></a>
				<a href="#"><div><span><img src="img/icon/cog.png" class="icon" />设置</span></div></a>
				<a href="#"><div><span><img src="img/icon/exit.png" class="icon" />退出</span></div></a>
			</div>
		</div>
	</div>
</div>