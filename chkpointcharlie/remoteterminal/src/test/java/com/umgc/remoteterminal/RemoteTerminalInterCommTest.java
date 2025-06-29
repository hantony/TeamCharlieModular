package com.umgc.remoteterminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

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

import com.umgc.application.attendancelog.AttendanceLog;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing

@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
public class RemoteTerminalInterCommTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;
	private String MAIN_APP_BASEURI;

	@Autowired
	RemoteTerminalRepository remoteTerminalRepository;

	// static, all tests share this mysql container
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");

	@BeforeEach
	void testSetUp() {

		BASEURI = "http://localhost:" + port;
		MAIN_APP_BASEURI = "http://localhost:" + 8080;

		remoteTerminalRepository.deleteAll();
		
		Date date = new Date();
		
		RemoteTerminal terminalA = new RemoteTerminal("locA", 3L, date.getTime() );
		RemoteTerminal terminalB = new RemoteTerminal("locB", 3L, date.getTime() );
		RemoteTerminal terminalC = new RemoteTerminal("locC", 4L, date.getTime() );
		RemoteTerminal terminalD = new RemoteTerminal("locD", 4L, date.getTime() );
		
		remoteTerminalRepository.saveAll(List.of(terminalA, terminalB, terminalC, terminalD));
	}
	
	// Tests creating of an Attendance Log entry.   The Main Application needs to be running for this test to succeed
	@Test
	public void testCreateAttendanceLogEntry() {

		// Create a new Attendance Log Entry
		Date date = new Date();
		Long userId5 = Long.valueOf(5);
		Long terminalId5 = Long.valueOf(5);
		
		AttendanceLog newLog = new AttendanceLog(userId5, terminalId5, date.getTime(), "entryType5", "location5");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<AttendanceLog> request = new HttpEntity<>(newLog, headers);

		// test POST save
		ResponseEntity<AttendanceLog> responseEntity = restTemplate.postForEntity(MAIN_APP_BASEURI + "/Log", request, AttendanceLog.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		long userId = responseEntity.getBody().getUserId();
		assertEquals( userId5, userId);

	}


	
}