<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%
	User user = (User) request.getSession().getAttribute("user");
	if (user == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	String saveAvatarResponseMessage = (String)request.getSession().getAttribute("saveAvatarResponseMessage");
	request.getSession().removeAttribute("saveAvatarResponseMessage");
%>
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
				<div class="tab active">头像</div>
				<div class="tab">个人简介</div>
				<div class="tab">密码</div>
			</div>
			<div id="settingsDiv">
				<div id="setAvatarDiv" class="tabPane active">
					<div class="columnDiv">
						<img src="img/avatar/<%=user.getAvatarPath() %>" />
						<form id="uploadAvatarForm" method="POST" action="FileUploadServlet?action=uploadUserAvatar" enctype="multipart/form-data">
							<input type="file" id="file" name="file">
						</form>
						<div id="saveAvatarResponseMessageDiv"><%= (saveAvatarResponseMessage != null)? saveAvatarResponseMessage : "" %></div>
						<button class="saveButton" id="saveAvatarButton">保存</button>
					</div>
				</div>
				<div id="setMottoDiv" class="tabPane">
					<div class="columnDiv">
						<div>
							<label for="motto">个人简介</label>
							<input type="text" id="motto" placeholder="<%=user.getMotto() %>"/>
						</div>
						<div id="saveMottoResponseMessageDiv"></div>
						<div>
							<button class="saveButton" id="saveMottoButton">保存</button>
						</div>
					</div>
				</div>
				<div id="setPasswordDiv" class="tabPane">
					<div class="columnDiv">
						<div>
							<label for="oldPassword">当前密码</label>
							<input type="password" id="oldPassword" />
						</div>
						<div>
							<label for="newPassword">新的密码</label>
							<input type="password" id="newPassword" />
						</div>
						<div>
							<label for="newPasswordRepeat">确认密码</label>
							<input type="password" id="newPasswordRepeat" />
						</div>
						<div id="savePasswordResponseMessageDiv"></div>
						<div>
							<button class="saveButton" id="savePasswordButton">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/settings.js"></script>
</body>
</html>