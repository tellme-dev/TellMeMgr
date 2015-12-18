package com.hotel.link;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

/**
 * 控制台 websocket 连接
 * @author charo
 *
 */
@ServerEndpoint(value="/console/{consoleId}",configurator = SpringConfigurator.class)
public class ConsoleWsHandler {
	private Session session; //websocket 的session
	private String consoleId; //customerId 作为缓存key。
	/**
	 * 客户端请求一个webSocket连接
	 * @param session
	 * @param consoleId
	 */
	@OnOpen
	public void onOpen(Session session,
			@PathParam(value = "consoleId") String consoleId) {
		try {
			if (consoleId == null) {
				session.close();
			} else {
				this.session = session;
				this.consoleId=consoleId;
				
				//MessageRouter.getConsoleHandlers().put(consoleId, this);
			}
		} catch (IOException e) {

		}

	}

	/**
	 * 接受客户端数据
	 * @param msg
	 * @param session
	 * @return
	 */
	@OnMessage
	public String onMessage(String msg, Session session) {
		this.session = session;
		return null;
	}

	/**
	 * 连接被关闭
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		//MessageRouter.getConsoleHandlers().remove(consoleId);
	}

	/**
	 * 发生错误
	 * @param session
	 * @param t
	 */
	@OnError
	public void onError(Session session, Throwable t) {

	}

	/**
	 * 发送数据到客户端
	 * @param msg
	 */
	public void sendMessage(String msg){
		try {
			session.getBasicRemote().sendText(msg);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
