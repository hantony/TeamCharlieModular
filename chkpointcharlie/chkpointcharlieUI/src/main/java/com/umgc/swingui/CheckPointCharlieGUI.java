package com.umgc.swingui;
/*
 * CMSC 495 6381 Capstone
 * CHeckPoint Carlie 
 * June 7, 2024
 * 
 * The first GUI that is displayed prompting a user to log attendance or an administrator to sign in.
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.remoteterminal.RemoteTerminal;

public class CheckPointCharlieGUI {
	
	private JFrame frame;
	private RestTemplate restTemplate = new RestTemplate();
	private static final Logger log = LoggerFactory.getLogger(CheckPointCharlieGUI.class);

	public CheckPointCharlieGUI() {
		frame = new JFrame("CheckPoint Charlie");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setLayout(new BorderLayout());

		JButton scanSmartCardButton = new JButton("Scan Smart Card");
		scanSmartCardButton.setPreferredSize(new Dimension(150, 50));
		
		scanSmartCardButton.addActionListener(e -> {
			System.out.println("Preparing to scan smart card.");

			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();
			//
			for (AttendanceLog logEntry : logEntries) {
				log.info("   Current Log Entry : " + logEntry);
			}

			displayArrayContents(logEntries, null, "Attendance Log");
		});

		JButton adminLoginButton = new JButton("Admin Login");
		adminLoginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Admin Login Clicked");
				new AdminLoginWindow();
			}
		});

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.add(scanSmartCardButton);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(adminLoginButton, BorderLayout.EAST);

		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}
	
	ResponseEntity<AttendanceLog[]> getAttendanceLogEntries() {

		String MAIN_APP_BASEURI = "http://localhost:" + 8080;

		ResponseEntity<AttendanceLog[]> response = restTemplate.getForEntity(MAIN_APP_BASEURI + "/Log",
				AttendanceLog[].class);
		return response;
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
        		if ( alog.getCardId().equals(cardIdStr )) {
        			element = alog.toString();
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

}
