package com.umgc.remoteterminal;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;

@Service
public class RemoteTerminalService {
	
	DecimalFormat df = new DecimalFormat("0000");
	
	public final RestTemplate restTemplate = new RestTemplate();
	private static final Logger log = LoggerFactory.getLogger(RemoteTerminalService.class);

    @Autowired
    private RemoteTerminalRepository remoteTerminalRepository;

    public List<RemoteTerminal> findAll() {
        return remoteTerminalRepository.findAll();
    }

    public Optional<RemoteTerminal> findById(Long id) {
        return remoteTerminalRepository.findById(id);
    }
    
    public AttendanceLog scan() {
        return scanSmartCard();
    }

    public RemoteTerminal save(RemoteTerminal terminal) {
        return remoteTerminalRepository.save(terminal);
    }

    public void deleteById(Long id) {
    	remoteTerminalRepository.deleteById(id);
    }
    
	private AttendanceLog scanSmartCard() {

		Date date = new Date();
		RemoteTerminal remoteTerminal = new RemoteTerminal(1L, date.getTime());

		AttendanceLog alog = null;

		alog = generateRandomSmartCardReadEvent();
		ResponseEntity<AttendanceLog> result = saveAttendanceLogEntry(alog);
		if (result.getStatusCode() == HttpStatus.CREATED) {
			log.info("   Created: " + result);
		} else {
			log.info("*** There was a problem creating: " + alog);
		}

//		Sanity Check
		ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
		AttendanceLog[] logEntries = logEntriesResponse.getBody();
	
		for (AttendanceLog logEntry : logEntries) {
			log.info("   Current Log Entry : " + logEntry);
		}

		return alog;
	}
    
	private AttendanceLog generateRandomSmartCardReadEvent() {

		long terminalIdRange = 3;
		long userIdRange = 6;
		
		Date date = new Date();

		AttendanceLog alog = new AttendanceLog();

		long randTerminalInt = (int) (Math.random() * terminalIdRange) + 1;
		long randomUserInt = (int) (Math.random() * userIdRange) + 1;

		alog.setTerminalId(randTerminalInt);

		long randUserInt = (int) (Math.random() * userIdRange);

		alog.setUserId(randomUserInt);
		alog.setEntryTime(date.getTime());
		
		

		String s = df.format(randUserInt);   // Output: 0009
		String cardStr = "CARD" + s;
		alog.setCardId(cardStr);
		
		return alog;
	}
    
	AttendanceLog generateRandomSmartCardReadEvent2() {

		long terminalIdRange = 3;
		long userIdRange = 6;
		
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
		return response;
	}

   
}
