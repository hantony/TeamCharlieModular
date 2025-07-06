package com.umgc.application.attendancelog;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class AttendanceLogService {

	public AttendanceLogService() {
		super();
		this.serviceProperties = new ServiceProperties();	
	}

	private final ServiceProperties serviceProperties;
	
	public AttendanceLogService(ServiceProperties serviceProperties) {
	    this.serviceProperties = serviceProperties;
	}
	
    @Autowired
    private AttendanceLogRepository attendanceLogRepository;
    
    public List<AttendanceLog> findAll() {
    	List<AttendanceLog> logs = attendanceLogRepository.findAll();
        return logs;
    }

    public Optional<AttendanceLog> findById(Long id) {
        return attendanceLogRepository.findById(id);
    }

    public AttendanceLog save(AttendanceLog user) {
        return attendanceLogRepository.save(user);
    }

    public void deleteById(Long id) {
    	attendanceLogRepository.deleteById(id);
    }
    
    public String message() {
		return this.serviceProperties.getMessage();
	}
}

   
