package com.umgc.application;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.application.attendancelog.AttendanceLogRepository;
import com.umgc.application.location.Location;
import com.umgc.application.location.LocationRepository;
import com.umgc.application.user.User;
import com.umgc.application.user.UserRepository;

@SpringBootApplication(scanBasePackages = "com.umgc.application")

public class StartApplication {

	private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	// Spring runs CommandLineRunner bean when Spring Boot App starts
	
	String LECTURE_HALL_A = "Lecture Hall A";
	String STUDENT_LOUNGE = "Student Lounge";
	String FACULTY_LOUNGE = "Faculty Lounge";
	
	@Bean
	public CommandLineRunner initializeLocations (LocationRepository locationRepository) {
		return (args) -> {
			
			Location loc1 = new Location(LECTURE_HALL_A);
			Location loc2 = new Location(STUDENT_LOUNGE);
			Location loc3 = new Location(FACULTY_LOUNGE);
						
			// save a few locations, ID auto increase, expect 1, 2, 3
			locationRepository.saveAll(List.of(loc1, loc2, loc3));
			
			// find all users
			log.info("-------------------------------");
			log.info("findAll(), expect 3 locations");
			log.info("-------------------------------");
			for (Location loc: locationRepository.findAll()) {
				log.info(loc.toString());
			}
		};
	}

	@Bean
	public CommandLineRunner initializeUsers(UserRepository userRepository) {
		return (args) -> {
			
			User u1 = new User("Alice Johnson", "Student", "CARD1001", "Status01");
			User u2 = new User("Dextor Poindexter", "Student", "CARD1002", "Status01");
			User u3 = new User("Dr. Dolittle", "Professor", "CARD1003", "Status02");
			User u4 = new User("Dr. Annalise Keating", "Professor", "CARD1004", "Status02");
			User u5 = new User("Will Hunting", "Maintenance", "CARD1005", "Status03");
			User u6 = new User("Dana White", "Technical Support", "CARD1006", "Status03");
			
			// save a few users, ID auto increase, expect 1, 2, 3, 4 ...
			userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));
			
			// find all users
			log.info("-------------------------------");
			log.info("findAll(), expect 6 users");
			log.info("-------------------------------");
			for (User user : userRepository.findAll()) {
				log.info(user.toString());
			}
		};
	}
	
    @Bean
    public CommandLineRunner initializeAttendanceLogEntries(AttendanceLogRepository attendanceLogRepository) {
        return (args) -> {
        	
        	Date date = new Date();
        	
        	Long userId1 = Long.valueOf(1);
        	Long userId2 = Long.valueOf(2);
        	Long userId3 = Long.valueOf(3);
        	Long userId4 = Long.valueOf(4);
        	
        	Long terminalId1 = Long.valueOf(1);
    		Long terminalId2 = Long.valueOf(2);
    		Long terminalId3 = Long.valueOf(3);
    		Long terminalId4 = Long.valueOf(4);
    		
    		AttendanceLog newLog1 = new AttendanceLog(userId1, terminalId1, "CARD1001", date.getTime() );
    		
    		AttendanceLog newLog2 = new AttendanceLog(userId2, terminalId2, "CARD1002", date.getTime()+10);
    		AttendanceLog newLog3 = new AttendanceLog(userId2, terminalId2, "CARD1002", date.getTime()+20);
    		
    		AttendanceLog newLog4 = new AttendanceLog(userId3, terminalId3, "CARD1003", date.getTime()+30);
    		AttendanceLog newLog5 = new AttendanceLog(userId4, terminalId4, "CARD1004", date.getTime()+40);
    		AttendanceLog newLog6 = new AttendanceLog(userId4, terminalId4, "CARD1005", date.getTime()+50);
        	
            // save a few attendance log entries, ID auto increase, expect 1, 2, 3
            attendanceLogRepository.saveAll(List.of(newLog1, newLog2, newLog3));
            // find all log entries
            log.info("-------------------------------");
            log.info("findAll(), expect 3 log entries");
            log.info("-------------------------------");
            for (AttendanceLog alog : attendanceLogRepository.findAll()) {
                log.info(alog.toString());
            }
        };
    }

}