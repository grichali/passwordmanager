package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Model.User;
import repository.DatabaseConnector;
import repository.UserRepository;

public class SignIn extends JFrame{
    
    private JLabel usernamLabel,passwordLabel,titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signinButton;

    public SignIn(){
        initUI();
    }

    public void initUI(){
        setTitle("Sign In");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        // Create a title label
        titleLabel = new JLabel("Sign In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and style
        titleLabel.setBounds(300, 10, 300, 40); // Set bounds

        usernamLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password : ");


        usernameField = new JTextField();
        passwordField = new JPasswordField();


        signinButton = new JButton("Log In");
        signinButton.setBounds(310, 140, 170, 25);;

        usernamLabel.setBounds(200, 60, 80, 25);
        usernameField.setBounds(300, 60, 200, 25);
        
        passwordLabel.setBounds(200, 100, 80, 25);
        passwordField.setBounds(300, 100, 200, 25);




        panel.add(titleLabel);
        panel.add(usernamLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signinButton);

        add(panel);
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
        
                System.out.println("Username: " + username);
                System.out.println("password: " + password);
        
                DatabaseConnector databaseConnector = new DatabaseConnector();
                try {
                    UserRepository userRepository = new UserRepository(databaseConnector);
        
                    if (userRepository.loginUser(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // TODO: Add code to open a new window or perform other actions upon successful login.
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                        // TODO: Add code to display an error message to the user.
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                    // TODO: Handle exceptions, display an error message, or log the exception.
                } 
            }
        });
        
    }
        


    public static void main(String[] args){
        SignIn signIn = new SignIn();

        signIn.setVisible(true);
    }
}
