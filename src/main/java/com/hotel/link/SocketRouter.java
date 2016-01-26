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
		String type=null;
		
		JSONObject msg=null;
		
		IoSession rcuSession=null;
		Session appSession=null;
		
		if(jo.containsKey("type")){
			type=jo.getString("type");
			type=type!=null?type.toLowerCase():null;
		}
		
		if(jo.containsKey("src")){
			src=jo.getString("src");
			src=src!=null?src.toLowerCase():null;
		}
		
		if(jo.containsKey("dst")){
			dst=jo.getString("dst");
			dst=dst!=null?dst.toLowerCase():null;
		}
		
		if(jo.containsKey("sid")){
			sid=jo.getString("sid");
			rcuSession=rcuSessions.get(sid);
		}
		
		if(jo.containsKey("uid")){
			uid=jo.getString("uid");
			appSession=appSessions.get(uid);
		}
		
		try{

			if("htpk".equals(type) && "rcu".equals(src) && rcuSession!=null){
				/**
				 * rcu发出的心跳包
				 */
				msg=new JSONObject();
				msg.accumulate("src", "svr");
				msg.accumulate("dst", "rcu");
				msg.accumulate("type", "htpk");
				msg.accumulate("sid", sid);
				String result="#@" + msg.toString() +"@#";
				rcuSession.write(result);
			}else if("idset".equals(type) && "app".equals(src) && rcuSession!=null){
				/**
				 * app发出的上线通知
				 */
				String result="#@" + jo.toString() +"@#";
				rcuSession.write(result); //转发到rcu

			}else if ("ctst".equals(type) && "svr".equals(dst) && rcuSession !=null ){
				/**
				 * 控制命令 
				 * 发送都rcu端
				 */
				String result=jo.toString() +"@#";
				rcuSession.write(result);
				
			}else if("ctrc".equals(type) && "app".equals(dst) && appSession !=null){
				/**
				 * RCU响应控制命令的回复信息
				 */
				String result= jo.toString() ;
				appSession.getBasicRemote().sendText(result);
			}else if("sats".equals(type)  && "app".equals(dst) && appSession !=null){
				//
			}else if("idclr".equals(type)){
				/**
				 * 用户已经退房
				 */
			}else{
				logger.error(jo.toString());
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
