function login() {
	if (!validateEmail(loginEmail.value)) {
		return;
	}
	if (validatePassword(loginPassword.value)) {
		errorMessage.textContent = "";
		loginForm.submit();
	}
}

function register() {
	if (!validateUsername(regUsername.value)) {
		return;
	}
	if (!validateEmail(regEmail.value)) {
		return;
	}
	if (validatePassword(regPassword.value)) {
		errorMessage.textContent = "";
		regForm.submit();
	}
}

function validateUsername(username) {
	var usernameLength = username.length;
	if (usernameLength == 0) {
		errorMessage.textContent = "请输入用户名！";
		return false;
	}
	if (usernameLength < 2 | usernameLength > 16) {
		errorMessage.textContent = "用户名长度必须是2～16个字符！";
		return false;
	}
	return true;
}

function validateEmail(emailAddress) {
	if (emailAddress.length == 0) {
		errorMessage.textContent = "请输入邮箱！";
		return false;
	}
	var filter = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/; //The regex is from http://www.w3.org/TR/html5/forms.html#valid-e-mail-address
	if (filter.test(emailAddress)) {
		return true;
	} else {
		errorMessage.textContent = "邮箱格式错误！";
		return false;
	}
}

function validatePassword(password) {
	var passwordLength = password.length;
	if (passwordLength == 0) {
		errorMessage.textContent = "请输入密码！";
		return false;
	}
	if (passwordLength < 6 | passwordLength > 16) {
		errorMessage.textContent = "密码长度必须是6～16个字符！";
		return false;
	}
	var allNumbersFilter = /^\d+$/;
	if (allNumbersFilter.test(password)) {
		errorMessage.textContent = "密码不允许是纯数字！";
		return false;
	}
	var numberAndLetterFilter = /^[A-Za-z0-9]+$/;
	if (numberAndLetterFilter.test(password)) {
		return true;
	} else {
		errorMessage.textContent = "密码只能包含数字和字母！";
		return false;
	}
}

function changeForm() {
	if (loginDiv.style.display == "block") {
		loginDiv.style.display = "none";
		regDiv.style.display = "block";
		changeFormButton.textContent = "登陆";
		submitButton.textContent = "注册";
		submitButton.onclick = register;
	} else {
		loginDiv.style.display = "block";
		regDiv.style.display = "none";
		changeFormButton.textContent = "注册";
		submitButton.textContent = "登陆";
		submitButton.onclick = login;
	}
}