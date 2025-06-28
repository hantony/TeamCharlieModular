package com.umgc;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.umgc.attendancelog.AttendanceLog;
import com.umgc.attendancelog.AttendanceLogRepository;
import com.umgc.attendancelog.AttendanceLogService;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing

@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
public class AttendanceLogServiceTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;

	@Autowired
	AttendanceLogRepository attendanceLogRepository;
	
	@Autowired
	private AttendanceLogService attendanceLogService;

	
	// static, all tests share this mysql container
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");

	@BeforeEach
	void testSetUp() {

		BASEURI = "http://localhost:" + port;

		attendanceLogRepository.deleteAll();
		
		Date date = new Date();
		
		Long userId1 = Long.valueOf(1);
		Long userId2 = Long.valueOf(2);
		Long userId3 = Long.valueOf(3);
		Long userId4 = Long.valueOf(4);
		
		Long terminalId1 = Long.valueOf(1);
		Long terminalId2 = Long.valueOf(2);
		Long terminalId3 = Long.valueOf(3);
		Long terminalId4 = Long.valueOf(4);
		
		AttendanceLog newLog1 = new AttendanceLog(userId1, terminalId1, date.getTime(), "entryTypeA", "locationA");
		AttendanceLog newLog2 = new AttendanceLog(userId2, terminalId2, date.getTime()+1, "entryTypeB", "locationB");
		AttendanceLog newLog3 = new AttendanceLog(userId3, terminalId3, date.getTime()+2, "entryTypeC", "locationC");
		AttendanceLog newLog4 = new AttendanceLog(userId4, terminalId4, date.getTime()+3, "entryTypeD", "locationD");
		
		attendanceLogService.save(newLog1);
		attendanceLogService.save(newLog2);
		attendanceLogService.save(newLog3);
		attendanceLogService.save(newLog4);
	}
	

	 @Test
	    public void testAttendanceLogSaveAndFindById() {

	        // Create a new attendance log entry
	        AttendanceLog alog = new AttendanceLog();
	        alog.setLocation("LocA");
	        

	        // save attendance log entry
	        attendanceLogService.save(alog);

	        // find attendance log entry by id
	        Optional<AttendanceLog> result = attendanceLogService.findById(alog.getId());
	        assertTrue(result.isPresent());

	        AttendanceLog alogFromGet = result.get();

	        assertEquals("LocA", alogFromGet.getLocation());
	        

	    }

}