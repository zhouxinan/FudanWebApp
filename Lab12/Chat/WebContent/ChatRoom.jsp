<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="bean.User"    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html"; charset="UTF-8"/>
		<title>神马留言板</title>
		<link rel="stylesheet" href="css/style.css">
		<script type="text/javascript" src="js/jquery-2.1.3.min.js" defer="defer"></script>
		<script type="text/javascript" src="js/lab6.js" defer="defer" charset="utf-8"></script>
	</head>

	<body>
		<div id="note">
			<h1>神马留言板，留的是神马~~</h1>
			<hr/>
			<div id="display"></div>
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
		
		<form id="reset"><input type="file" class="hiden" id="file"></form>
		<textarea class="hiden" id="username" name=<%=((User)(request.getSession().getAttribute("user"))).getUsername() %>></textarea>
	</body>
</html>