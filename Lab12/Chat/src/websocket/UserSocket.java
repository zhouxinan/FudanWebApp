package websocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

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
	private Message currentMessage;
	private String fileName = null;
	private FileOutputStream fos = null;
	final String filePath = this.getClass().getClassLoader().getResource("")
			.getPath()
			+ "../../img/download/";

	@OnOpen
	public void open(Session session, @PathParam(value = "user") String user) {
		try {
			username = java.net.URLDecoder.decode(user, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SocketSessionPool pool = SocketSessionPool.getPoolInstance();
		JSONObject result = new JSONObject();
		result.append("username", username);
		result.append("type", "news");
		result.append("content", "欢迎" + username + "加入！！");
		pool.broadcast(result.toString());
		pool.addSession(session, username);

	}

	@OnMessage
	public void message(String message) throws IOException {
		System.out.println("Message: " + message);
		if (!message.startsWith("{")) {
			if (message.startsWith("end")) {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
				SocketSessionPool userPool = SocketSessionPool
						.getPoolInstance();
				userPool.broadcast(tranportToPublish(currentMessage));
				currentMessage = null;
				fileName = null;
			}
			return;
		}
		JSONObject messageObj = new JSONObject(message);
		String type = messageObj.getString("type");
		if (type.equals("message")) {
			currentMessage = transportMessageIn(message.toString());
		} else if (type.equals("file")) {
			prepareFileIO(messageObj);
		} else {
			System.out.println("other: " + type);
		}
	}

	@OnMessage
	public void processUpload(ByteBuffer msg, boolean last, Session session)
			throws IOException {
		while (msg.hasRemaining()) {
			fos.write(msg.get());
		}
	}

	public Message transportMessageIn(String message) {
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

	private String tranportToPublish(Message message) {
		JSONObject JSONMessage = new JSONObject();
		JSONMessage.append("username", message.getFrom());
		JSONMessage.append("type", "message");
		if (fileName != null) {
			message.setContent(message.getContent()
					+ "<br><img src=\"img/download/" + fileName + "\"/>");
		}
		JSONMessage.append("content", message.getContent());
		JSONMessage.append("time", message.getTime());
		return JSONMessage.toString();
	}

	private void prepareFileIO(JSONObject messageObj) throws IOException {
		fileName = messageObj.getString("fileName");
		File file = new File(filePath + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		fos = new FileOutputStream(file);
	}

	@OnClose
	public void close() {
		SocketSessionPool userPool = SocketSessionPool.getPoolInstance();
		userPool.removeSession(username);
		JSONObject result = new JSONObject();
		result.append("type", "news");
		result.append("content", "欢迎" + username + "离开！！");
		userPool.broadcast(result.toString());
	}
}
