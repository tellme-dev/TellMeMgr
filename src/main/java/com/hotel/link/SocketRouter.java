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
	private static Map<String,IoSession> rcuSessions=new ConcurrentHashMap<String,IoSession>();
	
	/**
	 * web 控制台 websocket 连接会话缓存
	 */
	private static Map<String,Session> consoleSessions=new ConcurrentHashMap<String,Session>();
	/**
	 * 客户端的websocket连接会话缓存
	 */
	private static Map<String,Session> appSessions =new ConcurrentHashMap<String,Session>(); 
	
	private final static Logger logger = LoggerFactory.getLogger(SocketRouter.class);
	
	/**
	 *接收 RCU端发送的数据，注意，RCU的数据格式是 用#@ 打头， @#结尾
	 * @param message
	 * @param ioSession
	 */
	public static  void rcuConnection(String rcuKey,IoSession ioSession){
		rcuSessions.put(rcuKey, ioSession);
	}
	
	/**
	 * 控制台连接
	 * @param consoleKey
	 * @param session
	 */
	public static void consoleConnection(String consoleKey,Session session){
		consoleSessions.put(consoleKey, session);
	}
	/**
	 * App端连接
	 * @param appKey
	 * @param session
	 */
	public static void appConnection(String appKey,Session session){
		appSessions.put(appKey, session);
	}
	
	public static Map<String,IoSession> getRcuSessions() {
		return rcuSessions;
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
		String src=null;
		String dst=null;
		String sid=null;
		String uid=null;
		
		IoSession rcuSession=null;
		Session appSession=null;
		
		if(jo.containsKey("src")){
			src=jo.getString("src");
		}
		
		if(jo.containsKey("dst")){
			dst=jo.getString("dst");
		}
		
		if(jo.containsKey("sid")){
			sid=jo.getString("sid");
		}
		
		if(jo.containsKey("uid")){
			uid=jo.getString("uid");
		}
		
		try{
			if(dst =="rcu" && sid !=null  && (rcuSession=rcuSessions.get(sid)) !=null){
				String msg="#@"+jo.toString() + "@#";
				rcuSession.write(msg);
			}else if (dst =="app" && uid !=null &&  (appSession=appSessions.get(uid)) !=null){
				String msg=jo.toString();
				appSession.getBasicRemote().sendText(msg);
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}
	
	
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
