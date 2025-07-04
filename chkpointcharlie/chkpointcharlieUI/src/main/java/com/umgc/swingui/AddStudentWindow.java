package com.umgc.swingui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.umgc.application.user.User;

public class AddStudentWindow {
	
	 private JFrame frame;

	 public AddStudentWindow() {
		
        JFrame frame = new JFrame("Add User");
        
        JTextField nameField = new JTextField(20);
        JTextField roleField = new JTextField();
        JTextField cardIdField = new JTextField();
        JTextField statusField = new JTextField();
        
        JButton submit = new JButton("Submit");
        
        RestTemplate restTemplate = new RestTemplate();
        String base = "http://localhost:8080/User";
        
        submit.addActionListener(e -> {
            try {
                                
                User user = new User( nameField.getText(), roleField.getText(), 
                			cardIdField.getText(), statusField.getText() );
                                               
        		// Create a new User
        		HttpHeaders headers = new HttpHeaders();
        		headers.add("Content-Type", "application/json");
        		HttpEntity<User> request = new HttpEntity<>(user, headers);
        		
        		// test POST save
        		ResponseEntity<User> saveResponse = restTemplate.postForEntity(base, request, User.class);
                JOptionPane.showMessageDialog(frame, "Response Code: " + saveResponse.getStatusCode());
                
                // sanity check
                ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
         		ResponseEntity<List<User>> getResponse = restTemplate.exchange(base, HttpMethod.GET, null, typeRef);
         		
         		for ( User u : getResponse.getBody() ) {
          			System.out.println(u.toString());
         		}

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Role:")); panel.add(roleField);
        panel.add(new JLabel("CardId:")); panel.add(cardIdField);
        panel.add(new JLabel("Status:")); panel.add(statusField);
        
        panel.add(submit);

        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
