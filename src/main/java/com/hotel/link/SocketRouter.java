package com.hotel.link;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketRouter {
	/**
	 * rcu客户端端连接的集合。
	 */
	private static Map<String,RcuSession> rcuIoSessions=new ConcurrentHashMap<String,RcuSession>();
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static  RcuSession rcuConnection(String message,IoSession ioSession){
		
		String sid=null;
		RcuSession rcuIoSession=null;
		
		try{
			String txt=message.toString().trim();
			txt=txt.replaceAll("#@", "");
			
			JSONObject jo =JSONObject.fromObject(txt);

			if(jo.containsKey("sid") && jo.containsKey("type") ){
				sid=jo.getString("sid");
				rcuIoSession=new RcuSession();
				rcuIoSession.setSid(sid);
				rcuIoSession.setMessageJson(jo);
				rcuIoSessions.put(sid, rcuIoSession);
			}else{
				throw new Exception("接收的RCU Messge 格式不争取，没有SID Key!");
			}
			
		}catch(Exception ex){
			LoggerFactory.getLogger(SocketRouter.class).error(ex.getMessage());
			return null;
		}
		return rcuIoSession;
	}
	
	public static Map<String,RcuSession> getRcuIoSessions() {
		return rcuIoSessions;
	}

	
}