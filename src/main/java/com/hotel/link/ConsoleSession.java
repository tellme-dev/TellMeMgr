package com.hotel.link;

import java.io.IOException;

import javax.websocket.Session;

import ch.qos.logback.classic.Logger;

/**
 * websocket 连接会话
 * @author charo
 *
 */
public class ConsoleSession {
	private String consoleId;
	private Session session;
	
	public String getConsoleId() {
		return consoleId;
	}

	public void setConsoleId(String consoleId) {
		this.consoleId = consoleId;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void sendMessage(String msg) throws IOException{
		session.getBasicRemote().sendText(msg);
	}
}
