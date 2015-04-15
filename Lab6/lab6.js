
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
	var len = text.value.length;
	var promote = document.getElementById("promote");
	promote.textContent = 140 - len;
}

text = document.getElementById("text");
text.onkeyup = countWords;