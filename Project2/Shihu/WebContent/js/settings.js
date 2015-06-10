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
			$("#serverResponseMessageDiv").html(data);
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