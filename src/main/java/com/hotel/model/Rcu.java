package com.hotel.model;

public class Rcu {
    private Integer id;
    
    private String name;

    private Integer roomId;

    private String serialId;
    
    private Integer cfgId;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCfgId() {
		return cfgId;
	}

	public void setCfgId(Integer cfgId) {
		this.cfgId = cfgId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }
}
