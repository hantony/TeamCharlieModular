package com.umgc.swingui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.attendancelog.AttendanceLog;
import com.umgc.application.attendancelog.AttendanceLogService;

//import com.umgc.application.user.UserRepository;
//@SpringBootApplication(scanBasePackages = "com.umgc.application.attendancelog")
//@EnableJpaRepositories(basePackages = {"com.umgc.application.attendancelog"})
//@ComponentScan(basePackages = {"com.umgc.application.attendancelog"})

@ComponentScan(basePackages = { "com.umgc.application.attendancelog", "com.umgc.swingui" })
@EnableJpaRepositories(basePackages = { "attendancelog" })
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.umgc.application.attendancelog" })

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
		JButton printStudentLogBtn = new JButton("Print A Specific Student's Attendance Log");
		JButton printTerminalLogBtn = new JButton("Print Teminal Scan Log");

		addStudentBtn.addActionListener(e -> {
			System.out.println("Admin selected to add a new student.");
			new AddStudentWindow();// add userRepository
			// JOptionPane.showMessageDialog(frame, "Add Student functionality coming
			// soon."); //Remove
		});

		printStudentLogBtn.addActionListener(e -> {
			System.out.println("Admin selected to print a specific student's attendance log.");

			String userIdStr = JOptionPane.showInputDialog(frame, "Enter Student's Card ID:");

			// AttendanceLogService logService = new AttendanceLogService();
			// List<AttendanceLog> logList = attendanceLogService.findAll();

			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();
			//
			for (AttendanceLog logEntry : logEntries) {
				log.info("   Current Log Entry : " + logEntry);
			}
		});

		printTerminalLogBtn.addActionListener(e -> {
			System.out.println("Admin selected to print the terminal log.");
			
			ResponseEntity<AttendanceLog[]> logEntriesResponse = getAttendanceLogEntries();
			AttendanceLog[] logEntries = logEntriesResponse.getBody();
			//
			for (AttendanceLog logEntry : logEntries) {
				log.info("   Current Log Entry : " + logEntry);
			}
		});

		frame.add(addStudentBtn);
		frame.add(printStudentLogBtn);
		frame.add(printTerminalLogBtn);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	ResponseEntity<AttendanceLog[]> getAttendanceLogEntries() {

		String MAIN_APP_BASEURI = "http://localhost:" + 8080;

		ResponseEntity<AttendanceLog[]> response = restTemplate.getForEntity(MAIN_APP_BASEURI + "/Log",
				AttendanceLog[].class);
		return response;
	}

}
