
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
	document.getElementById("promote").textContent = remain;
	return remain;
}

function tweet() {
	if (countWords() >= 0) {
		var newTweet = document.createElement("div");
		newTweet.className = "tweet";
		var quote = document.createElement("div");
		quote.className = "quote";
		var username = document.createElement("span");
		username.className = "username";
		username.appendChild(document.createTextNode("江泽民"));
		var userImage = document.createElement("img");
		userImage.className = "userImage";
		userImage.src = "img/user.jpg";
		var tweetContent = document.createElement("span");
		tweetContent.className = "tweetContent";
		tweetContent.appendChild(document.createTextNode(text.value));
		var tweetInfo = document.createElement("div");
		tweetInfo.className = "tweetInfo";
		var tweetTime = document.createElement("span");
		tweetTime.className = "tweetTime";
		tweetTime.appendChild(document.createTextNode(getCurrentTime()));
		var from = document.createElement("span");
		from.className = "from";
		from.appendChild(document.createTextNode("来自网页"));
		var forward = document.createElement("span");
		forward.className = "forward";
		forward.appendChild(document.createTextNode("转发"));
		var clear = document.createElement("div");
		clear.className = "clear";
		quote.appendChild(username);
		quote.appendChild(tweetContent);
		tweetInfo.appendChild(tweetTime);
		tweetInfo.appendChild(from);
		tweetInfo.appendChild(forward);
		newTweet.appendChild(userImage);
		newTweet.appendChild(quote);
		newTweet.appendChild(tweetInfo);
		newTweet.appendChild(clear);
		document.getElementById("display").appendChild(newTweet);
		text.value = "";
		countWords();
	}
}

function getCurrentTime() {
	var currentTime = new Date();
	var day = currentTime.getDate();
	var month = currentTime.getMonth() + 1;
	var year = currentTime.getFullYear();
	var hours = currentTime.getHours();
	var minutes = currentTime.getMinutes();
	var currentTimeString = year + "/" + month + "/" + day + " " + hours + ":";
	if (minutes < 10){
		minutes = "0" + minutes;
	}
	currentTimeString += minutes + " ";
	if (hours > 11){
		currentTimeString += "PM";
	} else {
		currentTimeString += "AM";
	}
	return currentTimeString;
}

text = document.getElementById("text");
text.onkeydown = countWords;
submit = document.getElementById("submit");
submit.onclick = tweet;