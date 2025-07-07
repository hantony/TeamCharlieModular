package com.umgc.swingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.application.attendancelog.AttendanceLogService;
import com.umgc.application.user.User;

public class AdminControlPanel {

	@Autowired
	AttendanceLogService attendanceLogService;

	private static final Logger log = LoggerFactory.getLogger(AdminControlPanel.class);

	private RestTemplate restTemplate = new RestTemplate();

	public AdminControlPanel() {
		JFrame frame = new JFrame("Admin Control Panel");
		frame.setSize(400, 200);
		frame.setLayout(new FlowLayout());

		JButton addStudentBtn = new JButton("Add New Student");
		JButton printStudentLogBtn = new JButton("Personalized Attendance Log");
		JButton printTerminalLogBtn = new JButton("Print Terminal Scan Log");

		addStudentBtn.addActionListener(e -> {
			System.out.println("Admin selected to add a new student.");
			new AddStudentWindow();// add userRepository
			// JOptionPane.showMessageDialog(frame, "Add Student functionality coming
			// soon."); //Remove
		});

		printStudentLogBtn.addActionListener(e -> {
			System.out.println("Admin selected to print a specific student's attendance log.");

			String cardIdStr = JOptionPane.showInputDialog(frame, "Enter Student's Card ID:");

			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();

			for (AttendanceLog logEntry : logEntries) {
				log.info("   Current Log Entry : " + logEntry.toString());
			}

			displayArrayContents(logEntries, cardIdStr, "Pesonalized Attendance Log");
		});

		printTerminalLogBtn.addActionListener(e -> {
			System.out.println("Admin selected to print the terminal log.");

			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();
			//
			for (AttendanceLog logEntry : logEntries) {
				log.info("   Current Log Entry : " + logEntry);
			}

			displayArrayContents(logEntries, null, "Attendance Log");
		});

		frame.add(addStudentBtn);
		frame.add(printStudentLogBtn);
		frame.add(printTerminalLogBtn);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void displayArrayContents(AttendanceLog[] array, String cardIdStr, String windowTitle) {
        JFrame logFrame = new JFrame(windowTitle);
        logFrame.setSize(400, 300);
        logFrame.setLayout(new BorderLayout());
 
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
 
        String element = null;
        
        for (AttendanceLog alog : array) 
        {
        	if ( cardIdStr != null ) {
        		log.info(  " alog: " + alog.getCardId() + "   cardIdStr: " + cardIdStr );
        		if ( alog.getCardId().equals(cardIdStr )) {
        			User u = findUserByCardId(cardIdStr);
        			if ( u != null ) {
        			element = "   user ID: " + alog.getUserId() + "   name = " + u.getName() + "  card ID: " + alog.getCardId() + "  Terminal: " + alog.getTerminalId() + "  Time: " + alog.getEntryTime() ;
        			} else {
        	   			element = "   user ID: " + alog.getUserId() + "  card ID: " + alog.getCardId() + "  Terminal: " + alog.getTerminalId() + "  Time: " + alog.getEntryTime();
        			}
        			textArea.append(element + "\n");
        		}
        	} else {
        	element = alog.toString();
            textArea.append(element + "\n");
        	}
        }
 
        JScrollPane scrollPane = new JScrollPane(textArea);
        logFrame.add(scrollPane, BorderLayout.CENTER);
 
        logFrame.setLocationRelativeTo(null);
        logFrame.setVisible(true);
    }
	
	User findUserByCardId( String cardId ) {
		
		String url = "http://localhost:8080/User";
		
		// find all Users and return List<User>
		ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {};
		ResponseEntity<List<User>> response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		
		
		List<User> users = response.getBody();
		//
		for (User u : users ) {
			if ( u.getCardId().equals(cardId )) {
				return u;
			}
		}
		
		return null;
		
		
	}

	ResponseEntity<AttendanceLog[]> getAttendanceLogEntries() {

		String MAIN_APP_BASEURI = "http://localhost:" + 8080;

		ResponseEntity<AttendanceLog[]> response = restTemplate.getForEntity(MAIN_APP_BASEURI + "/Log",
				AttendanceLog[].class);
		return response;
	}
	

}
