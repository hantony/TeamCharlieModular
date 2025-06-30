package com.umgc.remoteterminal;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootApplication(scanBasePackages = "com.umgc.remoteterminal")

public class StartRemoteTerminalApplication {

	String LECTURE_HALL_A = "Lecture Hall A";
	String STUDENT_LOUNGE = "Student Lounge";
	String FACULTY_LOUNGE = "Faculty Lounge";

	public final RestTemplate restTemplate = new RestTemplate();

	private static final Logger log = LoggerFactory.getLogger(StartRemoteTerminalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartRemoteTerminalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeTerminals(RemoteTerminalRepository remoteTerminalRepository) {
		return (args) -> {

			Date date = new Date();

			RemoteTerminal rt1 = new RemoteTerminal("Lecture Hall A");
			RemoteTerminal rt2 = new RemoteTerminal("Student Lounge");
			RemoteTerminal rt3 = new RemoteTerminal("Faculty Lounge");

			// save a few users, ID auto increase, expect 1, 2, 3, 4
			remoteTerminalRepository.saveAll(List.of(rt1, rt2, rt3));

			// find all terminals
			log.info("-------------------------------");
			log.info("findAll(), expect 3 remote terminals");
			log.info("-------------------------------");
			for (RemoteTerminal rt : remoteTerminalRepository.findAll()) {
				log.info(rt.toString());
			}

			AttendanceLog alog = null;

			for (int i = 0; i < 10; ++i) {
				alog = generateRandomLogEvent();
				ResponseEntity<AttendanceLog> result = saveAttendanceLogEntry(alog);
				if (result.getStatusCode() == HttpStatus.CREATED) {
					log.info("   Created: " + result);
				} else {
					log.info("*** There was a problem creating: " + alog );
				}

				Thread.sleep(1000);				
			}
			
			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();
			
			for ( AttendanceLog logEntry : logEntries ) {
				log.info("   Current Log Entry : " + logEntry);
			}
		};
	}

	ResponseEntity<AttendanceLog> saveAttendanceLogEntry(AttendanceLog alog) {
		String MAIN_APP_BASEURI = "http://localhost:" + 8080;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<AttendanceLog> request = new HttpEntity<>(alog, headers);
		ResponseEntity<AttendanceLog> responseEntity = restTemplate.postForEntity(MAIN_APP_BASEURI + "/Log", request,
				AttendanceLog.class);
		return responseEntity;
	}
	
	ResponseEntity<AttendanceLog[]> getAttendanceLogEntries() {
		String MAIN_APP_BASEURI = "http://localhost:" + 8080;

		ResponseEntity<AttendanceLog[]> response =
				  restTemplate.getForEntity(
						  MAIN_APP_BASEURI + "/Log",
				  AttendanceLog[].class);
				AttendanceLog[] logEntries = response.getBody();
	
		return response;
	}

	AttendanceLog generateRandomLogEvent() {

		long terminalIdRange = 3;
		long userIdRange = 5;
		
		Date date = new Date();

		AttendanceLog alog = new AttendanceLog();

		long randTerminalInt = (int) (Math.random() * terminalIdRange) + 1;
		long randomUserInt = (int) (Math.random() * userIdRange) + 1;

		alog.setTerminalId(randTerminalInt);

		long randUserInt = (int) (Math.random() * userIdRange);

		alog.setUserId(randomUserInt);
		alog.setEntryTime(date.getTime());
		;
		return alog;

	}

}