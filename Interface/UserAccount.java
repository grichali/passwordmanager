package Interface;

import javax.swing.*;

import Model.User;
import SessionManagement.SessionManager;
import repository.DatabaseConnector;
import repository.UserRepository;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAccount {
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstnameField; // Added firstname field
    private JTextField lastnameField; // Added lastname field
    private JButton updateButton;
    SessionManager sessionManager = SessionManager.getInstance();
    DatabaseConnector databaseConnector = new DatabaseConnector();
    UserRepository userRepository = new UserRepository(databaseConnector);
    User user = userRepository.getUserByUsername(sessionManager.getUsername());
    JFrame parentFrame;

    public UserAccount(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        panel = new JPanel(null); // Set layout to null

        // Set bounds for labels and fields
        JLabel firstnameLabel = new JLabel("First Name:");
        firstnameLabel.setBounds(10, 10, 80, 25);
        firstnameField = new JTextField(user.getPrenom());
        firstnameField.setBounds(100, 10, 160, 25);
        panel.add(firstnameLabel);
        panel.add(firstnameField);

        JLabel lastnameLabel = new JLabel("Last Name:");
        lastnameLabel.setBounds(10, 40, 80, 25);
        lastnameField = new JTextField(user.getNom());
        lastnameField.setBounds(100, 40, 160, 25);
        panel.add(lastnameLabel);
        panel.add(lastnameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 70, 80, 25);
        usernameField = new JTextField(user.getUsername());
        usernameField.setBounds(100, 70, 160, 25);
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 100, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(100, 100, 160, 25);
        panel.add(passwordLabel);
        panel.add(passwordField);

        updateButton = new JButton("Update");
        updateButton.setBounds(100, 130, 80, 25);
        panel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform update logic here
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String prenom = firstnameField.getText(); // Get firstname value
                String nom = lastnameField.getText(); // Get lastname value

                if (!nom.isEmpty() && !prenom.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
        
                    user.setNom(nom);
                    user.setPrenom(prenom);
                    user.setUsername(username);
                    user.setPassword(password);
                
                    if (userRepository.updateUser(user)) {
                        JOptionPane.showMessageDialog(panel, "User information updated successfully");
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                // Update user information
               
                

                
                
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}

