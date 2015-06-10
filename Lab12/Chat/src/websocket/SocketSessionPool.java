package websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

public class SocketSessionPool {
	private static SocketSessionPool pool;
	
	private Map<String, Session> sessionMap = new HashMap<String, Session>();
	
	private SocketSessionPool(){}
	
	public static SocketSessionPool getPoolInstance(){
		if(pool == null){
			pool = new SocketSessionPool();
		}
		return pool;
	}
	
	public void addSession(Session session, String username){
		if(sessionMap.get(username) != null){
			return;
		}
		sessionMap.put(username, session);
	}
	
	public void removeSession(String username){
		sessionMap.remove(username);
	}
	
	public void sendToOneUser(String message, String username){
		Session userSession = sessionMap.get(username);
		if(userSession != null){
			try {
				userSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(String message){
		try {
			for (Entry<String, Session> one : sessionMap.entrySet()) {
				one.getValue().getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
