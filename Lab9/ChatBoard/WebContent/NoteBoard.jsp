<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
   <%@page import="dao.Dao" %>
   <%@page import="bean.*" %>
   <%@page import="java.util.List" %>
   
   <%
   		User user = (User)request.getSession().getAttribute("user");
   		if(user == null){
   			response.sendRedirect("Login.jsp");
   			return;
   		}
   		List<Message> messageList = Dao.getInstance().getAllMessage(user.getUserID());
   %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html"; charset="UTF-8"/>
	<title>神马留言板</title>
	<link rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/lab6.js" defer="defer" charset="utf-8"></script>
</head>
<body>
		<div id="note">
			<h1>神马留言板，留的是神马~~</h1>
			<hr/>
			<div id="display">
				<%
					for(Message m: messageList){%>
						<div class='noteItem'> 
							<img class='user-photo' src='img/user.jpg'/> 
							 <div class='blog-content'> <span class='blog-name'><%=user.getUsername() %></span> : 
								<span class='text-content' ><%=m.getMessage() %></span>
								<div class='info'><span class='time'><%=m.getDate().toLocaleString() %></span>
									<span class='agent'>来自网页</span>
									<span class='operation' onclick=deleteMethod(<%=m.getMessageID() %>)>删除</span>
								</div>
							</div>
							<div class='clear' ></div>
						</div>
				<% 	}
				%>
			</div>
		</div>

		<div id="write">
			<div id="noteDiv">
				<span>有神马要说的</span>
				<p >还可以输入<b id="promote">140</b>字</p>
			</div>
			<textarea id="text"></textarea>
			<div id="tool">
				<ul>
					<li><a id="face" href="#"><div class="image"></div><div class="name">表情</div></a></li>
					<li><a id="photo" href="#"><div class="image"></div><div class="name">图片</div></a></li>
					<li><a id="video" href="#"><div class="image"></div><div class="name">视频</div></a></li>
				</ul>
				<button id="submit">提交</button>
			</div>
			<div id="faceDiv">
			</div>
		</div>	
		
		<div class="hiden">
			<form action="addMessage" method="post" id="addMessage">
				<input type="text" name="message" id="message"/>
			</form>
		</div>
		
		<div class="hiden">
			<form action="deleteMessage" method="post" id="deleteMessage">
				<input type="text" name="messageID" id="messageID"/>
			</form>
		</div>
	
</body>
</html>