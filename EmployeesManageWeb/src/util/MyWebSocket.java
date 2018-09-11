package util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/pageview")
public class MyWebSocket {

	private static Set<MyWebSocket> set = new HashSet<>();
	private static int num=0;
	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		set.add(this); // 加入set中
		num++;
		String str = String.valueOf(num);
		for (MyWebSocket item : set) {
			try {
				item.session.getBasicRemote().sendText(str);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@OnClose
	public void onClose() {
		set.remove(this); // 从set中删除
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		for (MyWebSocket item : set) {
			try {
				item.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
}
