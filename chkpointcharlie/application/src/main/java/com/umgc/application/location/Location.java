package com.umgc.application.location;

import jakarta.persistence.Entity;

// curl http://localhost:9090/user/add -d name=AAA -d role=Role01  -d cardId=Card01 -d status=Status01

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    String location;
    
	public Location() {
		super();
	}

	public Location(String location) {
		super();
		this.location = location;
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

	@Override
	public String toString() {
		return "Location [id=" + id + ", location=" + location + "]";
	}

	
 
}

