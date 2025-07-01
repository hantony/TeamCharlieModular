package com.umgc.application.userAccess;

import jakarta.persistence.Entity;

// curl http://localhost:9090/user/add -d name=AAA -d role=Role01  -d cardId=Card01 -d status=Status01

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAccess {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long locationId;
    
    
	public UserAccess() {
		super();
	}


	public UserAccess(Long userId, Long locationId) {
		super();
		this.userId = userId;
		this.locationId = locationId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getLocationId() {
		return locationId;
	}


	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}


	@Override
	public String toString() {
		return "UserAccess [id=" + id + ", userId=" + userId + ", locationId=" + locationId + "]";
	}
    
 
}

