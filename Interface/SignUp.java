package Interface;

import javax.swing.*;
 
import Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import repository.DatabaseConnector;
import repository.UserRepository;

public class SignUp extends JFrame {

    private JButton signupButton;
    private JLabel nomLabel, prenomLabel, usernameLabel, passwordLabel, titleLabel;
    private JTextField nomField, prenomField, usernameField;
    private JPasswordField passwordField;

    public SignUp() {
        initUI();
    }

    private void initUI() {
        setTitle("Password Manager");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with a background color
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240)); // Light gray background color

        // Create a title label
        titleLabel = new JLabel("Password Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and style
        titleLabel.setBounds(200, 10, 300, 40); // Set bounds

        // Other components
        nomLabel = new JLabel("Nom:");
        prenomLabel = new JLabel("Prenom:");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        nomField = new JTextField();
        prenomField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        signupButton = new JButton("Sign Up");

        // Set bounds for each component
        nomLabel.setBounds(200, 60, 80, 25);
        nomField.setBounds(200 + 100, 60, 200, 25);

        prenomLabel.setBounds(200, 90, 80, 25);
        prenomField.setBounds(200 + 100, 90, 200, 25);

        usernameLabel.setBounds(200, 120, 80, 25);
        usernameField.setBounds(200 + 100, 120, 200, 25);

        passwordLabel.setBounds(200, 150, 80, 25);
        passwordField.setBounds(200 + 100, 150, 200, 25);

        signupButton.setBounds(200 + 100, 180, 100, 25);

        // Add components to the panel
        panel.add(titleLabel);
        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signupButton);

        // Set layout for the frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Add action listener for the signup button
signupButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle signup button click event
        System.out.println("Nom: " + nomField.getText());
        System.out.println("Prenom: " + prenomField.getText());
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + new String(passwordField.getPassword()));

        // Assuming you have a DatabaseConnector and UserRepository instance
        DatabaseConnector databaseConnector = new DatabaseConnector();
        UserRepository userRepository = new UserRepository(databaseConnector);

        // Create a User object
        User user = new User(
                nomField.getText(),
                prenomField.getText(),
                usernameField.getText(),
                new String(passwordField.getPassword())
        );

        // Save the user to the database
        userRepository.saveUser(user);
    }
});
    }

    public static void main(String[] args) {
        // Create an instance of the SignUp class
        SignUp signUp = new SignUp();

        // Set the frame to be visible
        signUp.setVisible(true);
    }
}
