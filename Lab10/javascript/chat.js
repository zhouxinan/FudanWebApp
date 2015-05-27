/**
 * 
 */


//定义获取消息的函数
function getmsg(){
}

//处理返回的json数据
function appendmsg(){
}


//定义发送消息函数
function sendmsg(){

}
//自动获取消息，为getmsg函数设置一个定时器。推荐使用js的setInterval函数






/*Do not modify following parts*/

//调用addMsg函数来显示信息
function addMsg(pic,name,date,detail){
	var content=document.createElement('div');
	content.className="msg";
	content.innerHTML='<img src="'+pic+'" title="'+name+'" alt="'+name+'" class="pic"><span class="brf">'+name+':&nbsp; '+date+'</span><span class="detail">'+detail+'</span>';
	var msgbox=document.getElementById("msgbox");
	msgbox.insertBefore(content,msgbox.lastChild);
	//自动滚动到最下
	msgbox.scrollTop=msgbox.scrollHeight;
}

//按Ctrl+Enter键发送
window.onkeyup=function(e){
	var ev=e||window.event;
	if (ev.ctrlKey && ev.keyCode==13){
		sendmsg();
	}
}

