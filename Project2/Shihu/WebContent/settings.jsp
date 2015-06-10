<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect("login.jsp");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/profile.css" />
<link type="text/css" rel="stylesheet" href="css/settings.css" />
<script src="lib/jquery-2.1.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>识乎 - 设置</title>
</head>
<body>
	<jsp:include page="navigator.jsp" />
	<div id="content">
		<div id="contentWrapper">
			<div id="tabBar" class="columnDiv">
				<div class="tab active">个人简介</div>
				<div class="tab">密码</div>
				<div class="tab">头像</div>
			</div>
			<div id="settingsDiv">
				<div id="setMottoDiv" class="tabPane active">
					<div class="columnDiv">
						<div>
							<label for="motto">个人简介</label>
							<input type="text" id="motto" />
						</div>
						<div>
							<button class="saveButton">保存</button>
						</div>
					</div>
				</div>
				<div id="setPasswordDiv" class="tabPane">
					<div class="columnDiv">
						<div>
							<input type="password" id="oldPassword" />
						</div>
						<div>
							<input type="password" id="newPassword" />
						</div>
						<div>
							<input type="password" id="newPasswordRepeat" />
						</div>
						<div id="serverResponseMessageDiv"></div>
						<div>
							<button class="saveButton" id="savePasswordButton">保存</button>
						</div>
					</div>
				</div>
				<div id="setAvatarDiv" class="tabPane">
					<div class="columnDiv">
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/settings.js"></script>
</body>
</html>