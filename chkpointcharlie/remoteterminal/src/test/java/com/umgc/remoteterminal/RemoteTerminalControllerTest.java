package com.umgc.remoteterminal;

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

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing

@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
public class RemoteTerminalControllerTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;

	@Autowired
	RemoteTerminalRepository remoteTerminalRepository;

	// static, all tests share this mysql container
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0-debian");

	@BeforeEach
	void testSetUp() {

		BASEURI = "http://localhost:" + port;

		remoteTerminalRepository.deleteAll();
		
		Date date = new Date();
		
		RemoteTerminal terminalA = new RemoteTerminal(3L, date.getTime() );
		RemoteTerminal terminalB = new RemoteTerminal(3L, date.getTime() );
		RemoteTerminal terminalC = new RemoteTerminal(4L, date.getTime() );
		RemoteTerminal terminalD = new RemoteTerminal(4L, date.getTime() );
		
		remoteTerminalRepository.saveAll(List.of(terminalA, terminalB, terminalC, terminalD));
	}

	@Test
	void testFindAll() {

		// find all Terminal Entries and return List<User>
		ParameterizedTypeReference<List<RemoteTerminal>> typeRef = new ParameterizedTypeReference<>() {
		};
		ResponseEntity<List<RemoteTerminal>> response = restTemplate.exchange(BASEURI + "/RemoteTerminal", HttpMethod.GET, null, typeRef);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4, response.getBody().size());

	}

	@Test
	public void testCreate() {

		// Create a new Remote Terminal Entry 
		
		Date date = new Date();

		RemoteTerminal terminal = new RemoteTerminal(3L, date.getTime() );
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<RemoteTerminal> request = new HttpEntity<>(terminal, headers);

		// test POST save
		ResponseEntity<RemoteTerminal> responseEntity = restTemplate.postForEntity(BASEURI + "/RemoteTerminal", request, RemoteTerminal.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		// find Remote Terminal Entry 
		List<RemoteTerminal> list = remoteTerminalRepository.findByLocationId(3L);

		// Test Terminal details
		RemoteTerminal getTerminal = list.get(0);
		assertEquals(3L, getTerminal.getLocationId());
	}
	
	@Test
	public void testDeleteById() {

		// Create a new Remote Terminal 
		
		Date date = new Date();
		RemoteTerminal newTerminal = new RemoteTerminal(3L, date.getTime() );
		
		remoteTerminalRepository.save(newTerminal);

		// find Remote Terminal 
		Long newTerminalId = newTerminal.getId();
		Optional<RemoteTerminal> result = remoteTerminalRepository.findById(newTerminalId);
        assertTrue(!result.isEmpty());
		
        // Delete Remote Terminal 
        remoteTerminalRepository.deleteById(newTerminal.getId());
		
        result = remoteTerminalRepository.findById(newTerminalId);
        assertTrue(result.isEmpty());
	}
	
}