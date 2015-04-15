
window.onload = function(){
	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
}

window.onresize = function(){
	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
}

function countWords() {
	var remain = 140 - text.value.length;
	var promote = document.getElementById("promote");
	promote.textContent = remain;
	return remain;
}

function tweet() {
	if (countWords() >= 0) {
		alert("tweet!");
		text.value = "";
		countWords();
	} else alert("naive!");
}

function getCurrentTime() {
	var currentTime = new Date();
	var day = currentTime.getDate();
	var month = currentTime.getMonth() + 1;
	var year = currentTime.getFullYear();
	var hours = currentTime.getHours();
	var minutes = currentTime.getMinutes();
	var currentTimeString = year + "/" + month + "/" + day + " ";
	if (minutes < 10){
		minutes = "0" + minutes;
	}
	currentTimeString += hours + ":" + minutes + " ";
	if (hours > 11){
		currentTimeString += "PM";
	} else {
		currentTimeString += "AM";
	}
	return currentTimeString;
}

text = document.getElementById("text");
text.onkeyup = countWords;
submit = document.getElementById("submit");
submit.onclick = tweet;