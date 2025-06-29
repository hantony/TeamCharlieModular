package com.umgc.remoteterminal;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.umgc.remoteterminal")

public class StartRemoteTerminalApplication {

	private static final Logger log = LoggerFactory.getLogger(StartRemoteTerminalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartRemoteTerminalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialTerminals(RemoteTerminalRepository remoteTerminalRepository) {
		return (args) -> {
			
			Date date  = new Date();
			
			RemoteTerminal rt1 = new RemoteTerminal ("Lecture Hall A", 1L, date.getTime()  );
			RemoteTerminal rt2 = new RemoteTerminal ("Student Lounge", 2L, date.getTime()  );
			RemoteTerminal rt3 = new RemoteTerminal ("Faculty Lounge", 3L, date.getTime()  );
			
	
						
			// save a few users, ID auto increase, expect 1, 2, 3, 4
			remoteTerminalRepository.saveAll(List.of(rt1, rt2, rt3));
			
			// find all termainals
			log.info("-------------------------------");
			log.info("findAll(), expect 3 remote terminals");
			log.info("-------------------------------");
			for (RemoteTerminal rt : remoteTerminalRepository.findAll()) {
				log.info(rt.toString());
			}
		};
	}
}