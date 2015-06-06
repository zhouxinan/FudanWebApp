<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   		String a = request.getParameter("id"); // test parameter
   %>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="css/base.css" />
	<link type="text/css" rel="stylesheet" href="css/layout.css" />
	<link type="text/css" rel="stylesheet" href="css/question.css" />
	<script src="lib/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/question.js"></script>
	<meta charset="utf-8" />
	<!-- For iPhone to display normally -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>识乎 - 提问</title>
</head>
<body>
	<jsp:include page="navigator.jsp"/>
	<div id="content">
		<div id="contentWrapper">
			<div id="leftColumn">
				<div class="columnDiv">
					<div id="questionTitle">美国的华莱士到底有多高？</div>
					<div id="questionMetadata">
						<span class="userName"><a href="#">张宝华</a></span><span>提问于</span><span>2015年1月1日</span>
					</div>
				</div>
				<div class="columnDiv">
					<div class="userInfoDiv">
						<a href="#">
							<img class="userAvatar" src="img/avatar/user_9.jpg" />
							<span class="userName">江选研讨会</span>
						</a>
						<span class="userSignature">一起学习三个代表重要思想</span>
					</div>
					<div class="answer">
						<p>考完试了，先来扯谈一篇轻松愉快简单粗暴的。跟那篇「到底哪个西方国家没去过」一样，本文继续来讨论下那段讲话中的另一句金句「美国的那个华莱士比你们不知道高到哪里去了」。</p>
						<p>华莱士到底有多高呢，我们试图以长者的身高来推出他的身高来。先来看一张长者和华莱士的合影。</p>
						<p>这张是我在“CBS60分钟”里的截图</p>
						<p><img src="img/upload/640.jpeg" /></p>
						<p>由于是采访前的对话，双方着装很正式，所以不存在一方人字拖一方皮鞋造成的身高误差。从图里看出华莱士明显比长者高出半个头，但从腰带看长者的腿却比华莱士的长不少，可见按这腿的比例来算长者应该是十头身的魔鬼身材。</p>
						<p>首先来确定下长者身高，在身高问题领域，领导人的大多是秘密，而由于职业原因运动员的身高数据是相对最透明的，于是我们来看下长者和姚明的合影，图中还有老布什。而且三人合影推身高必将比两人推更准确。</p>
						<p><img src="img/upload/640-2.jpeg" /></p>
						<p>稍有常识的人都知道姚明的身高是226CM，现在来确定老布什，根据姚明跟同样是体育明星的刘翔合影看出老布什与刘翔同样顶到姚明的肩膀，所以老布什也约莫是188CM。</p>
						<p><img src="img/upload/640-3.jpeg" /></p>
						<p>再看那张三人合影，长者头顶与老布什鼻头相平，而鼻头到头顶一般13-14CM，所以长者在76岁时的身高是174CM左右，所以华莱士的身高比长者高半个头为185CM左右。</p>
						<p>于是第一个问题华莱士的身高问题解决了，现在来继续研究下长者说的「比你们高到哪里去了」中「哪里去了」究竟是多大的一个度量衡。</p>
						<p>我们先找到长者「你们」中的这位女记者，她叫张洺华，14年前是香港无线的记者，现在微博认证为「香港传媒人，寰亚传媒集团高级副总裁」，她这十四年来的心路历程以后单独开篇详述。确定她的身高也简单，其相册里有一张她与张家辉的合影（中间）。</p>
						<p><img src="img/upload/640-4.jpeg" /></p>
						<p>从微博内容及图片里来看她是在跟张家辉一起参加阿迪的活动，一身运动装扮也避免了高跟鞋等装扮带来的计算误差。张家辉在百度百科里的身高是172，按照标准中国男人的身高虚报法，其真实身高应该是170CM，女记者的头顶约莫位于张的眼部，眼部到头顶距离一般10CM+，这样女记者的身高应该就是160CM上下。</p>
						<p>所以答案很快就出来了，「美国的华莱士有多高？」「185CM」、「比你们高到哪里去了？」「25CM」</p>
					</div>
					<div class="answerMetadata">
						<span>2015-01-01 12:12</span>
						<span class="replyCount noSelect">评论 (2)</span>
					</div>
					<div class="replyListDiv">
						<div class="replyDiv">
							<div><a href="#"><img class="userAvatar" src="img/avatar/user_2.jpg" /></a></div>
							<div class="replyData">
								<div class="userName"><a href="#">张宝华</a></div>
								<div class="replyContent">哇，原来真的很高哦！</div>
								<div class="replyTime">2015-01-01 13:01</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="replyDiv">
							<div><a href="#"><img class="userAvatar" src="img/avatar/myAvatar.jpg" /></a></div>
							<div class="replyData">
								<div class="userName"><a href="#">江泽民</a></div>
								<div class="replyContent">你毕竟还是too young！</div>
								<div class="replyTime">2015-01-01 13:06</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="replyDiv new">
							<form method="post" action="">
								<input type="text" name="newReply" placeholder="说你什么好呢……"/>
								<button class="submitButton">发表评论</button>
							</form>
						</div>
					</div>
				</div>
				<div class="columnDiv">
					<div class="userInfoDiv">
						<a href="#">
							<img class="userAvatar" src="img/avatar/user_1.jpg" />
							<span class="userName">华莱士</span>
						</a>
						<span class="userSignature">比你们不知道高到哪里去</span>
					</div>
					<div class="answer">
						<p>185cm，我就明确告诉你。</p>
					</div>
					<div class="answerMetadata">
						<span>2015-01-01 12:12</span>
						<span class="replyCount noSelect">评论 (1)</span>
					</div>
					<div class="replyListDiv">
						<div class="replyDiv">
							<div><a href="#"><img class="userAvatar" src="img/avatar/user_2.jpg" /></a></div>
							<div class="replyData">
								<div class="userName"><a href="#">张宝华</a></div>
								<div class="replyContent">豪迈！</div>
								<div class="replyTime">2015-01-01 13:02</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="replyDiv new">
							<form method="post" action="">
								<input type="text" name="newReply" placeholder="说你什么好呢……"/>
								<button class="submitButton">发表评论</button>
							</form>
						</div>
					</div>
				</div>
				<div class="columnDiv">
					<form method="post" action="">
						<textarea placeholder="我来告诉你们人生的经验……"></textarea>
						<button id="uploadImgButton"><img src="img/icon/attachment.png" class="icon" /></button>
						<button class="submitButton">发布回答</button>
					</form>
					<div class="clear"></div>
				</div>
			</div>
			<div id="rightColumn">
				<div class="columnDiv">
					<div class="rightColumnTitle">热门问题</div>
					<div id="popularQuestionList">
						<div><a href="#">美国的华莱士到底有多高？</a></div>
						<div><a href="#">香港记者到底跑得有多快？</a></div>
						<div><a href="#">江主席究竟掌握了几门语言？</a></div>
						<div><a href="#">李光耀眼里的长者是什么样的？</a></div>
						<div><a href="#">为什么有那么多人膜蛤？</a></div>
					</div>
				</div>
				<div class="columnDiv">
					<div class="rightColumnTitle">热门用户</div>
					<div id="popularUserList">
						<div class="popularUser">
							<a href="#">
								<img class="userAvatar" src="img/avatar/user_1.jpg" />
								<div class="userName">华莱士</div>
							</a>
							<div class="userSignature">比你们不知道高到哪里去</div>
						</div>
						<div class="popularUser">
							<a href="#">
								<img class="userAvatar" src="img/avatar/user_2.jpg" />
								<div class="userName">张宝华</div>
							</a>
							<div class="userSignature">著名香港女记者</div>
						</div>
						<div class="popularUser">
							<a href="#">
								<img class="userAvatar" src="img/avatar/user_3.jpg" />
								<div class="userName">董建华</div>
							</a>
							<div class="userSignature">香港第一任特首</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="columnDiv">
					<div id="author">© 2015 Zhou Xinan</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>