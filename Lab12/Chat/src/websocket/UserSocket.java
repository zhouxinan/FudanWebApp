package websocket;

import java.io.UnsupportedEncodingException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import bean.Message;

@ServerEndpoint(value = "/websocket/{user}")
public class UserSocket {
	private String username;
	
	@OnOpen
	public void open(Session session,  @PathParam(value = "user")String user){
		try {
			username =  java.net.URLDecoder.decode(user , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		SocketSessionPool pool = SocketSessionPool.getPoolInstance();
		JSONObject result = new JSONObject();
		result.append("username", username);
		result.append("type", "news");
		result.append("content", "欢迎"+username+"加入！！");
		pool.broadcast(result.toString());
		pool.addSession(session, username);

	}
	
	@OnMessage
	public void message(String message){
		System.out.println("Message: "+message);
		if(!message.startsWith("{")){
			return;
		}
		Message receivedMessage = this.transportMessageIn(message.toString());
		String type = receivedMessage.getType();
		SocketSessionPool userPool = SocketSessionPool.getPoolInstance();
		if(type.equals("message")){
			userPool.broadcast(this.tranportToPublish(receivedMessage));
		}else{
			System.out.println("other: "+type);
		}
	}
	
	public Message transportMessageIn(String message){
		JSONObject JSONMessage = new JSONObject(message.toString());
		String type = JSONMessage.getString("type");
		String content = JSONMessage.getString("content");
		String time = JSONMessage.getString("time");
		
		Message transportMessage = new Message();
		transportMessage.setFrom(username);
		transportMessage.setContent(content);
		transportMessage.setTime(time);
		transportMessage.setType(type);
		return transportMessage;
	}
	
	private String tranportToPublish(Message message){
		JSONObject JSONMessage = new JSONObject();
		JSONMessage.append("username", message.getFrom());
		JSONMessage.append("type", "message");
		JSONMessage.append("content", message.getContent());
		JSONMessage.append("time", message.getTime());
		return JSONMessage.toString();
	}

	
	@OnClose
	public void close(){
		SocketSessionPool userPool = SocketSessionPool.getPoolInstance();
		userPool.removeSession(username);
		JSONObject result = new JSONObject();
		result.append("type", "news");
		result.append("content", "欢迎"+username+"离开！！");	
		userPool.broadcast(result.toString());
	}
}
