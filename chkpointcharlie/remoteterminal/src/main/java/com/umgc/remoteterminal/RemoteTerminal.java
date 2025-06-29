package com.umgc.remoteterminal;

import jakarta.persistence.Entity;

// curl http://localhost:9090/user/add -d name=AAA -d role=Role01  -d cardId=Card01 -d status=Status01

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RemoteTerminal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String location;
    private Long userId;
    private Long entryTime;
    
    
    
	public RemoteTerminal() {
		super();
	}

	public RemoteTerminal(String location, Long userId, Long entryTime) {
		super();
		this.location = location;
		this.userId = userId;
		this.entryTime = entryTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Long entryTime) {
		this.entryTime = entryTime;
	}

	@Override
	public String toString() {
		return "RemoteTerminal [id=" + id + ", location=" + location + ", userId=" + userId + ", entryTime=" + entryTime
				+ "]";
	}



}

