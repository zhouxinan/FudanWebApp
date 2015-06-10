var sessionStorage = window.sessionStorage;

window.onload = function(){
	clearStorage();
};

document.getElementById("loginForm").onsubmit= function(){
	sessionStorage.setItem("username", document.getElementById("name").value);
}

function clearStorage(){
	sessionStorage.clear();
}