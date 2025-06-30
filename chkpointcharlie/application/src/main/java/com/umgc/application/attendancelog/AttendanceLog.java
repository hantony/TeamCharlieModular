package com.umgc.application.attendancelog;

import jakarta.persistence.Entity;

// curl http://localhost:9090/user/add -d name=AAA -d role=Role01  -d cardId=Card01 -d status=Status01

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AttendanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long terminalId;
    
    private Long entryTime;
    private String entryType;
	public AttendanceLog(Long userId, Long terminalId, Long entryTime, String entryType) {
		super();
		this.userId = userId;
		this.terminalId = terminalId;
		this.entryTime = entryTime;
		this.entryType = entryType;
	}
	public AttendanceLog() {
		super();
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
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public Long getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Long entryTime) {
		this.entryTime = entryTime;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	@Override
	public String toString() {
		return "AttendanceLog [id=" + id + ", userId=" + userId + ", terminalId=" + terminalId + ", entryTime="
				+ entryTime + ", entryType=" + entryType + "]";
	}
   
    
	

}

