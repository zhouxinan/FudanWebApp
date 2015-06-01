$(function setTabAction() {
	var $replyCount = $(".replyCount");
	var $replyListDiv = $(".replyListDiv");
	$replyCount.click(function(){
		for (var i = 0; i < $replyCount.length; i++) {
			if (this == $replyCount[i]) {
				if ($replyListDiv.eq(i).attr("class") == "replyListDiv") {
					$replyListDiv.eq(i).attr("class", "replyListDiv active");
				} else {
					$replyListDiv.eq(i).attr("class", "replyListDiv");
				}
			}
		}
	});
});