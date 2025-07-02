package com.umgc.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.application.userAccess.UserAccess;
import com.umgc.application.userAccess.UserAccessRepository;
import com.umgc.application.userAccess.UserAccessService;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing

@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
public class UserAccessServiceTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;

	@Autowired
	UserAccessRepository userAccessRepository;
	
	@Autowired
	private UserAccessService userAccessService;
	
	// static, all tests share this mysql container
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");
	
	Long userId1 = null;
	
	
	Long terminalId1 = null;

	@BeforeEach
	void testSetUp() {

		BASEURI = "http://localhost:" + port;

		userAccessRepository.deleteAll();
		
		Date date = new Date();
		
		userId1 = Long.valueOf(1);
//		Long userId2 = Long.valueOf(2);
//		Long userId3 = Long.valueOf(3);
//		Long userId4 = Long.valueOf(4);
		
		terminalId1 = Long.valueOf(1);
//		Long terminalId2 = Long.valueOf(2);
//		Long terminalId3 = Long.valueOf(3);
//		Long terminalId4 = Long.valueOf(4);

		
	}
	
	@Test
	public void testUserAccessSaveAndFindById() {
		
		UserAccess  ua1 = new UserAccess( userId1, terminalId1 );
		
		userAccessService.save(ua1);


//
//	        // find user access record by id
	        Optional<UserAccess> result = userAccessService.findById(ua1.getId());
	        assertTrue(result.isPresent());

	//        UserAccess uaFromGet = result.get();

//	        assertEquals("entryA", uaFromGet.getTe);
	    }

}