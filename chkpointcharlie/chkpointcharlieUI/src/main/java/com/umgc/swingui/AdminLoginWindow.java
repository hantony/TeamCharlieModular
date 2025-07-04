package com.umgc.swingui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminLoginWindow {
	
	 private JFrame frame;

	 public AdminLoginWindow() {
	        frame = new JFrame("Admin Login");
	        frame.setSize(300, 200);
	        frame.setLayout(new GridLayout(3, 2));

	        JLabel userLabel = new JLabel("Username:");
	        JTextField userField = new JTextField();

	        JLabel passLabel = new JLabel("Password:");
	        JPasswordField passField = new JPasswordField();

	        JButton loginButton = new JButton("Login");
	        loginButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.out.println("Admin Attempt Login - User: " + userField.getText());
	            }
	        });

	        frame.add(userLabel);
	        frame.add(userField);
	        frame.add(passLabel);
	        frame.add(passField);
	        frame.add(new JLabel()); // empty cell
	        frame.add(loginButton);
	        
	        loginButton.addActionListener(e -> {
	            String user = userField.getText();
	            String pass = new String(passField.getPassword());
	            
	            // Temporary login pass
	            System.out.println("Admin Login Attempt - User: " + user + ", Password: " + pass);
	            JOptionPane.showMessageDialog(frame, "Login Successful!");
	            frame.dispose();
	            new AdminControlPanel();
	        });

	        frame.setLocationRelativeTo(null);

	        frame.setVisible(true);
	    }  
}
