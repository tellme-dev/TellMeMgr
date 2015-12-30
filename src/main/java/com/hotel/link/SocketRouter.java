package com.hotel.link;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketRouter {
	/**
	 * rcu客户端socket端连接的会话缓存
	 */
	private static Map<String,IoSession> rcuIoSessions=new ConcurrentHashMap<String,IoSession>();
	
	/**
	 * web 控制台 websocket 连接会话缓存
	 */
	private static Map<String,Session> consoleSessions=new ConcurrentHashMap<String,Session>();
	/**
	 * 客户端的websocket连接会话缓存
	 */
	private static Map<String,Session> appSessions =new ConcurrentHashMap<String,Session>(); 
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static  void rcuConnection(String message,IoSession ioSession){
		
		String sid=null;
		
		try{
			String txt=message.toString().trim();
			txt=txt.replaceAll("#@", "");
			
			JSONObject jo =JSONObject.fromObject(txt);

			if(jo.containsKey("sid") && jo.containsKey("type") ){
				sid=jo.getString("sid");
				rcuIoSessions.put(sid, ioSession);
			}else{
				throw new Exception("接收的RCU Messge 格式不争取，没有SID Key!");
			}
			
		}catch(Exception ex){
			LoggerFactory.getLogger(SocketRouter.class).error(ex.getMessage());
		}
	}
	
	
	public static void consoleConnection(String consoleKey,Session session){
		consoleSessions.put(consoleKey, session);
	}
	
	public static Map<String,IoSession> getRcuSessions() {
		return rcuIoSessions;
	}

	public static Map<String,Session> getConsoleSessions() {
		return consoleSessions;
	}
	
	public static Map<String,Session> getAppSessions() {
		return appSessions;
	}
	
	/**
	 * 执行接受到的命令
	 * 包括 rcu,app, console 发送的，
	 * @param jo
	 */
	public static void execute(JSONObject jo){
		
	}
	
//	public static void client2Rcu(JSONObject jo) throws Exception{
//		if(jo.containsKey("sid") && jo.containsKey("type") ){
//			RcuSession rcuSession= rcuIoSessions.get(jo.getString("sid"));
//			if(rcuSession !=null){
//				rcuSession.sendMessage(jo);
//				notify2Console(jo.toString());
//			}
//		}else{
//			throw new Exception("接收的RCU Messge 格式不争取，没有SID Key!");
//		}
//	}
	
	/**
	 * 发送到web 客户端
	 * @param msg
	 * @throws IOException
	 */
	private static void  notify2Console(String msg) throws IOException{
		
		for(Entry<String,Session> entry:consoleSessions.entrySet()){
			Session  session=entry.getValue();
			session.getBasicRemote().sendText(msg);
		}
	}
}
