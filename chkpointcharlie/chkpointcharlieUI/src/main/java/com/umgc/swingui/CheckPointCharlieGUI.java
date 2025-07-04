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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CheckPointCharlieGUI {
	private JFrame frame;

	public CheckPointCharlieGUI() {
		frame = new JFrame("CheckPoint Charlie");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setLayout(new BorderLayout());

		JButton logAttendanceButton = new JButton("Scan Smart Card");
		logAttendanceButton.setPreferredSize(new Dimension(150, 50));
		logAttendanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Awaiting Smart Card Scans");
				
				// Start Remote Terminal Application
				new ScanSmartCardWindow();
			}
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
		centerPanel.add(logAttendanceButton);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(adminLoginButton, BorderLayout.EAST);

		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

}
