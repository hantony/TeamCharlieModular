package com.umgc.swingui;

import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.remoteterminal.RemoteTerminal;

public class ScanSmartCardWindow {
	
	 private JFrame frame;
	 String BASEURI = "http://localhost:8081";
	 RestTemplate  restTemplate = new RestTemplate();

	 public ScanSmartCardWindow() {
		
        JFrame frame = new JFrame("Scan Smart Card");
        
//        JTextField nameField = new JTextField(20);
//        JTextField roleField = new JTextField();
//        JTextField cardIdField = new JTextField();
//        JTextField statusField = new JTextField();
        
        JButton submit = new JButton("Scan");
        
        RestTemplate restTemplate = new RestTemplate();
        String base = "http://localhost:8081/RemoteTerminal/scan";
        
        submit.addActionListener(e -> {
            try {
        		 		
        		Date date = new Date();

        		RemoteTerminal terminal = new RemoteTerminal(3L, date.getTime());
        		HttpHeaders headers = new HttpHeaders();
        		headers.add("Content-Type", "application/json");
        		HttpEntity<RemoteTerminal> request = new HttpEntity<>(terminal, headers);

        		ResponseEntity<AttendanceLog> response = restTemplate.getForEntity(BASEURI + "/RemoteTerminal/scan", AttendanceLog.class);
        		AttendanceLog alog = response.getBody();
        		System.out.println(alog.toString());
        		        		
//        		ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
//    			AttendanceLog[] logEntries = logEntriesResponse.getBody();
//    			
//         		for ( AttendanceLog attendanceLogEntry : logEntries ) {
//          			System.out.println(attendanceLogEntry.toString());
//         		}

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        panel.add(submit);

        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
	 
		ResponseEntity<AttendanceLog[]> getAttendanceLogEntries() {

			String MAIN_APP_BASEURI = "http://localhost:" + 8080;

			ResponseEntity<AttendanceLog[]> response = restTemplate.getForEntity(MAIN_APP_BASEURI + "/Log",
					AttendanceLog[].class);
			return response;
		}
}
