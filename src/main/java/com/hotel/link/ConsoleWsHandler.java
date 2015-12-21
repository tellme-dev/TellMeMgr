package com.hotel.link;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

/**
 * 控制台 websocket 连接
 * @author charo
 *
 */
@ServerEndpoint(value="/console/{consoleId}",configurator = SpringConfigurator.class)
public class ConsoleWsHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@OnOpen
	public void onOpen(@PathParam(value = "consoleId") String consoleId,
			Session session
			) {
		try {
			if(consoleId !=null){
				ConsoleSession consoleSession=new ConsoleSession();
				consoleSession.setConsoleId(consoleId);
				SocketRouter.getConsoleSessions().put(consoleId, consoleSession);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

	}

	/**
	 * 接受客户端数据
	 * @param msg
	 * @param session
	 * @return
	 */
	@OnMessage
	public String onMessage(@PathParam(value = "consoleId") String consoleId,
			String msg, 
			Session session) {
		return null;
	}

	/**
	 * 连接被关闭
	 * @param session
	 */
	@OnClose
	public void onClose(@PathParam(value = "consoleId") String consoleId,
			Session session) {
		if(consoleId !=null){
			SocketRouter.getConsoleSessions().remove(consoleId);
		}
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

}
