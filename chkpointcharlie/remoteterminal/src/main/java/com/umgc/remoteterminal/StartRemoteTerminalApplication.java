
package com.umgc.remoteterminal;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.application.user.User;

@SpringBootApplication(scanBasePackages = "com.umgc.remoteterminal")

public class StartRemoteTerminalApplication {

	public final RestTemplate restTemplate = new RestTemplate();

	private static final Logger log = LoggerFactory.getLogger(StartRemoteTerminalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartRemoteTerminalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeTerminals(RemoteTerminalRepository remoteTerminalRepository) {
		return (args) -> {
			
			Date date = new Date();
			
			RemoteTerminal rt1 = new RemoteTerminal(1L, date.getTime());
			RemoteTerminal rt2 = new RemoteTerminal(2L, date.getTime());
			RemoteTerminal rt3 = new RemoteTerminal(3L, date.getTime());
			
			// save a few terminals, ID auto increase, expect 1, 2, 3
			remoteTerminalRepository.saveAll(List.of(rt1, rt2, rt3));

			// find all terminals
			log.info("-------------------------------");
			log.info("findAll(), expect 3 remote terminals");
			log.info("-------------------------------");
			for (RemoteTerminal rt : remoteTerminalRepository.findAll()) {
				log.info(rt.toString());
			}
			
			ResponseEntity<User[]> getUsersResponse = getAllUsers();
			User[] users = getUsersResponse.getBody();
			
			for (User user : users ) {
				log.info("   User : " + user);
			}
		};
	}
	
	ResponseEntity<User[]> getAllUsers() {
		String MAIN_APP_BASEURI = "http://localhost:" + 8080;

		ResponseEntity<User[]> response =
				  restTemplate.getForEntity(
						  MAIN_APP_BASEURI + "/User",
				  User[].class);
		return response;
	}

}