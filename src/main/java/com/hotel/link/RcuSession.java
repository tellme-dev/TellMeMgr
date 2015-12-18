package com.hotel.link;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

/**
 * RCU 的 socket 的连接保持会话
 * @author charo
 *
 */
public class RcuSession {
	/**
	 * 唯一标识符号
	 */
	private String sid;
	/**
	 * 类型
	 */

	/**
	 * 命令的数据
	 */
	private JSONObject messageJson;
	
	private IoSession ioSession;
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public IoSession getIoSession() {
		return ioSession;
	}

	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	public JSONObject getMessageJson() {
		return messageJson;
	}

	public void setMessageJson(JSONObject messageJson) {
		this.messageJson = messageJson;
	}
	/**
	 * 发送消息到RCU
	 * 
	 * @param message
	 */
	public void sendMessage(JSONObject message){
		String  txt=message.toString();
		if(this.ioSession!=null){
			this.ioSession.write(txt);
		}
	}
	
	/**
	 * 执行MessageJson的命令
	 */
	public void execute(){
		String type=this.messageJson.getString("type");
		
		switch(type){
		case "csts":
			
		case "htpk":
			
		}
	}

}
