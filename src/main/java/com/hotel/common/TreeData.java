package com.hotel.common;

import java.util.List;
import java.util.Map;

public class TreeData extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2422212713448674965L;
	
	public static final String STATE_CLOSED = "closed";
	
	public static final String STATE_OPEN = "open";
	
	private Object id;
	
	private String text;

	private String state = STATE_OPEN;
	
	private String iconCls;
	
	private boolean checked = false;
	
	private Map<String, Object> attributes;
	
	private List<? extends TreeData> children;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if(!STATE_CLOSED.equals(state)){
			this.state = STATE_OPEN;
		}else{
			this.state = state;
		}
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<? extends TreeData> getChildren() {
		return children;
	}

	public void setChildren(List<? extends TreeData> children) {
		this.children = children;
	}

	public boolean hasChildren(){
		return children == null?false:!children.isEmpty();
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
