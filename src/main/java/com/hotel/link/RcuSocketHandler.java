package com.hotel.link;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RcuSocketHandler extends IoHandlerAdapter{
	
	private final  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override 
    public void sessionCreated(IoSession session) throws Exception {
		//System.out.println("created");
    }
	
	@Override
    public void sessionOpened(IoSession session) throws Exception {
		//System.out.println("opened");
    }
	
	@Override
    public void sessionClosed(IoSession session) throws Exception {
		// 
    }
	
	@Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("sessionIdle");
    }
	
	/**
	 * 获取RCU端发送的数据。
	 */
	@Override
    public void messageReceived(IoSession session, Object message) throws Exception {
		
		try{
			
			String txt=message.toString().trim();
			String sid=null;
			
			JSONObject jo =JSONObject.fromObject(txt);
	
			if(jo.containsKey("sid") && jo.containsKey("type") ){
				sid=jo.getString("sid");
				SocketRouter.rcuConnection(sid, session);
				SocketRouter.execute(jo);
			}else{
				throw new Exception("接收的RCU Messge 格式不争取，没有SID Key!");
			}

		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
    }
	
}
