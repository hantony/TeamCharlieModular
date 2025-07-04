package com.umgc.swingui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//import com.umgc.application.user.UserRepository;

public class AdminControlPanel {
	
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
            //JOptionPane.showMessageDialog(frame, "Add Student functionality coming soon."); //Remove
        });

        printStudentLogBtn.addActionListener(e -> {
            System.out.println("Admin selected to print a specific student's attendance log.");
            
            String userIdStr = JOptionPane.showInputDialog(frame, "Enter Student's Card ID:");
            /*
            if (userIdStr != null && !userIdStr.isEmpty()) {
                try {
                    Long userId = Long.parseLong(userIdStr);
                    List<AttendanceLog> logs = attendanceLogRepository.findByUserId(userId);

                    if (logs.isEmpty()) {
                        System.out.println("No attendance logs found for User ID: " + userId);
                    } else {
                        System.out.println("Attendance Logs for User ID " + userId + ":");
                        logs.forEach(log -> System.out.println(log.toString()));
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid User ID entered.");
                }
            } else {
                System.out.println("Student log print canceled or empty input.");
            }*/
            JOptionPane.showMessageDialog(frame, "Student log print functionality coming soon.");
        });

        printTerminalLogBtn.addActionListener(e -> {
            System.out.println("Admin selected to print the termial log.");
            /*
            List<AttendanceLog> allLogs = attendanceLogRepository.findAll();
            
            if (allLogs.isEmpty()) {
                System.out.println("No terminal logs available.");
            } else {
                System.out.println("Complete Terminal Attendance Logs:");
                allLogs.forEach(log -> System.out.println(log.toString()));
            }*/
            JOptionPane.showMessageDialog(frame, "Terminal log print functionality coming soon.");
        });

        frame.add(addStudentBtn);
        frame.add(printStudentLogBtn);
        frame.add(printTerminalLogBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
