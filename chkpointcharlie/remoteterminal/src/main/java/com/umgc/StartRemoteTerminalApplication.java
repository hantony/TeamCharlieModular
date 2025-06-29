package com.umgc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.umgc.remoteterminal.RemoteTerminal;
import com.umgc.remoteterminal.RemoteTerminalRepository;

@SpringBootApplication(scanBasePackages = "com.umgc")

public class StartRemoteTerminalApplication {

	private static final Logger log = LoggerFactory.getLogger(StartRemoteTerminalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartRemoteTerminalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialTerminals(RemoteTerminalRepository remoteTerminalRepository) {
		return (args) -> {
			
			RemoteTerminal rt1 = new RemoteTerminal ("Lecture Hall A" );
			RemoteTerminal rt2 = new RemoteTerminal ("Student Lounge" );
			RemoteTerminal rt3 = new RemoteTerminal ("Faculty Lounge" );
						
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