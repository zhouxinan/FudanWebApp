function login() {
	if (!validateEmail(document.getElementById("loginEmail").value)) {
		return;
	}
	if (validatePassword(document.getElementById("loginPassword").value)) {
		showErrorMessage("");
		document.getElementById("loginForm").submit();
		window.location.href = "question.html";
	}
}

function register() {
	if (!validateUsername(document.getElementById("regUsername").value)) {
		return;
	}
	if (!validateEmail(document.getElementById("regEmail").value)) {
		return;
	}
	if (validatePassword(document.getElementById("regPassword").value)) {
		showErrorMessage("");
		document.getElementById("regForm").submit();
		window.location.href = "question.html";
	}
}

function validateUsername(username) {
	var usernameLength = username.length;
	if (usernameLength == 0) {
		showErrorMessage("请输入用户名！");
		return false;
	}
	if (usernameLength < 2 || usernameLength > 16) {
		showErrorMessage("用户名长度必须是2～16个字符！");
		return false;
	}
	return true;
}

function validateEmail(emailAddress) {
	if (emailAddress.length == 0) {
		showErrorMessage("请输入邮箱！");
		return false;
	}
	var filter = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+([a-zA-Z0-9]+)$/;
	if (filter.test(emailAddress)) {
		return true;
	} else {
		showErrorMessage("邮箱格式错误！");
		return false;
	}
}

function validatePassword(password) {
	var passwordLength = password.length;
	if (passwordLength == 0) {
		showErrorMessage("请输入密码！");
		return false;
	}
	if (passwordLength < 6 || passwordLength > 16) {
		showErrorMessage("密码长度必须是6～16个字符！");
		return false;
	}
	var allNumbersFilter = /^\d+$/;
	if (allNumbersFilter.test(password)) {
		showErrorMessage("密码不允许是纯数字！");
		return false;
	}
	var numberAndLetterFilter = /^[A-Za-z0-9]+$/;
	if (numberAndLetterFilter.test(password)) {
		return true;
	} else {
		showErrorMessage("密码只能包含数字和字母！");
		return false;
	}
}

function switchForm() {
	if (document.getElementById("loginDiv").style.display == "block") {
		document.getElementById("loginDiv").style.display = "none";
		document.getElementById("regDiv").style.display = "block";
		document.getElementById("switchFormControllerText").textContent = "登陆";
		document.getElementById("submitButton").textContent = "注册";
		document.getElementById("submitButton").onclick = register;
	} else {
		document.getElementById("loginDiv").style.display = "block";
		document.getElementById("regDiv").style.display = "none";
		document.getElementById("switchFormControllerText").textContent = "注册";
		document.getElementById("submitButton").textContent = "登陆";
		document.getElementById("submitButton").onclick = login;
	}
}

function showErrorMessage(errorMessage) {
	document.getElementById("errorMessage").textContent = errorMessage;
}