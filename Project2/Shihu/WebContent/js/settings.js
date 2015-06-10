$(function setTabAction() {
	var $tab = $(".tab");
	var $tabPane = $(".tabPane");
	$tab.click(function() {
		for (var i = 0; i < $tab.length; i++) {
			if (this == $tab[i]) {
				$tab.attr("class", "tab");
				$(this).attr("class", "tab active");
				$tabPane.attr("class", "tabPane");
				$tabPane.eq(i).attr("class", "tabPane active");
			}
		}
	});
});

$("#savePasswordButton").click(function() {
	$.ajax({
		type : 'POST',
		url : "SettingsServlet",
		data : {
			action : 'modifyPassword',
			oldPassword : $("#oldPassword").val(),
			newPassword : $("#newPassword").val(),
			newPasswordRepeat : $("#newPasswordRepeat").val()
		},
		success : function(data) {
			$("#savePasswordResponseMessageDiv").html(data);
			if (data == '修改密码成功！') {
				$("#oldPassword").val("");
				$("#newPassword").val("");
				$("#newPasswordRepeat").val("");
			}
		},
		error : function() {
			alert("Connection error!");
		}
	});
});

$("#saveMottoButton").click(function() {
	var motto = $("#motto").val();
	if (motto == "") {
		$("#saveMottoResponseMessageDiv").html("请输入个人简介！");
		return;
	}
	$.ajax({
		type : 'POST',
		url : "SettingsServlet",
		data : {
			action : 'modifyMotto',
			motto : motto
		},
		success : function(data) {
			if (data == "true") {
				$("#saveMottoResponseMessageDiv").html("修改成功！");
				$("#motto").attr('placeholder', motto);
				$("#motto").val("");
			} else {
				$("#saveMottoResponseMessageDiv").html("服务器错误");
			}
		},
		error : function() {
			alert("Connection error!");
		}
	});
});