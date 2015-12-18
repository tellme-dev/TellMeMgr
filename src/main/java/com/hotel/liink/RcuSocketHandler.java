package com.hotel.liink;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class RcuSocketHandler extends IoHandlerAdapter{
	
	/**
	 * 唯一标识符号，来至于RCU硬件编码
	 */
	private String sid;
	
	private IoSession ioSession;
	
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

		this.ioSession=session;
		
		try{
			if(message != null){
				String txt=(String)message;
				SocketRouter.rcuConnection(txt, session);
				
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		

    }
	
	public void sendMessage(String msg){
		if(this.ioSession != null){
			this.ioSession.write(msg);
		}
	}
}
