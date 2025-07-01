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
    
    private Long locationId;
    private Long entryTime;
    
	public RemoteTerminal() {
		super();
	}

	public RemoteTerminal(Long locationId, Long entryTime) {
		super();
		this.locationId = locationId;
		this.entryTime = entryTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Long entryTime) {
		this.entryTime = entryTime;
	}

	@Override
	public String toString() {
		return "RemoteTerminal [id=" + id + ", locationId=" + locationId + ", entryTime=" + entryTime + "]";
	}
	



}

