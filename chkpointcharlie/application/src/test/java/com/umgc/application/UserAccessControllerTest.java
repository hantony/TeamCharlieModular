package com.umgc.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.umgc.application.user.User;
import com.umgc.application.userAccess.UserAccess;
import com.umgc.application.userAccess.UserAccessRepository;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing

@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
public class UserAccessControllerTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;

	@Autowired
	UserAccessRepository userAccessRepository;

	// static, all tests share this mysql container
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");

	@BeforeEach
	void testSetUp() {

		BASEURI = "http://localhost:" + port;

		userAccessRepository.deleteAll();

		UserAccess userAccessA = new UserAccess(1L, 1L);
		UserAccess userAccessB = new UserAccess(2L, 1L);
		UserAccess userAccessC = new UserAccess(1L, 2L);
		UserAccess userAccessD = new UserAccess(2L, 2L);
		

		userAccessRepository.saveAll(List.of(userAccessA, userAccessB, userAccessC, userAccessD));
	}

	@Test
	void testFindAll() {

		// find all User Access and return List<UserAccess>
		ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {
		};
		ResponseEntity<List<User>> response = restTemplate.exchange(BASEURI + "/UserAccess", HttpMethod.GET, null, typeRef);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4, response.getBody().size());

	}

	@Test
	public void testCreate() {

		// Create a new UserAccess 

		UserAccess userAccessE = new UserAccess(3L, 1L);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<UserAccess> request = new HttpEntity<>(userAccessE, headers);

		// test POST save
		ResponseEntity<UserAccess> responseEntity = restTemplate.postForEntity(BASEURI + "/UserAccess", request, UserAccess.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		// find User Access
		List<UserAccess> list = userAccessRepository.findByUserId(1L);

		// Test User EEE details
		UserAccess myUserAccess = list.get(0);
		assertEquals(1L, myUserAccess.getUserId());
		
	}
	
//	@Test
//	public void testDeleteById() {
//
//		// Create a new User Access 
//
//		UserAccess userAccess = new UserAccess (4L, 1L);
//		
//		userAccessRepository.save(userAccess);
//
//		// find UserAccess 
//		Long userAccessId = userAccess.getId();
//		Optional<UserAccess> result = userAccessRepository.findById(userAccessId);
//        assertTrue(!result.isEmpty());
//		
//        // Delete User EEE
//		userAccessRepository.deleteById(userAccessId);
//		
//        result = userAccessRepository.findById(userAccessId);
//        assertTrue(result.isEmpty());
//	}
	
}