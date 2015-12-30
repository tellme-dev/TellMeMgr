package com.hotel.link;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value="/appWs/{appKey}",configurator = SpringConfigurator.class)
public class AppWsHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@OnOpen
	public void onOpen(@PathParam(value = "appKey") String appKey,	
			Session session
			) {
		try {
			if(appKey !=null){
				SocketRouter.appConnection(appKey, session);
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
	public String onMessage(@PathParam(value = "appKey") String appKey,
			String msg, 
			Session session) {
		
		try{
			JSONObject jo =JSONObject.fromObject(msg.trim());
			SocketRouter.appConnection(appKey, session);
			SocketRouter.execute(jo);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		
		return null;
	}

	/**
	 * 连接被关闭
	 * @param session
	 */
	@OnClose
	public void onClose(@PathParam(value = "appKey") String appKey,
			Session session) {
		if(appKey !=null){
			SocketRouter.getConsoleSessions().remove(appKey);
		}
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
