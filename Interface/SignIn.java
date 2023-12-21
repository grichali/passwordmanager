package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.User;
import repository.DatabaseConnector;
import repository.UserRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SignIn extends JFrame {

    private JLabel usernamLabel, passwordLabel, titleLabel, createAccountLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signinButton;

    public SignIn() {
        initUI();
    }

    public void initUI() {
        setTitle("Password Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create a title label
        titleLabel = new JLabel("Sign In");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        titleLabel.setBounds(150, 10, 300, 30);

        usernamLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        signinButton = new JButton("Log In");
        signinButton.setBounds(150, 140, 100, 25);

        createAccountLabel = new JLabel("Create Account");
        createAccountLabel.setForeground(Color.BLUE);
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountLabel.setBounds(150, 180, 200, 20);
        createAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openSignUpInterface();
            }
        });

        usernamLabel.setBounds(50, 60, 80, 25);
        usernameField.setBounds(150, 60, 200, 25);

        passwordLabel.setBounds(50, 100, 80, 25);
        passwordField.setBounds(150, 100, 200, 25);

        panel.add(titleLabel);
        panel.add(usernamLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signinButton);
        panel.add(createAccountLabel);

        add(panel);
        setResizable(false);
        setLocationRelativeTo(null);

        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            UserRepository userRepository = new UserRepository(databaseConnector);

            if (userRepository.loginUser(username, password)) {
                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSignUpInterface() {
        SignUp signUp = new SignUp();
        signUp.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SignIn signIn = new SignIn();
        signIn.setVisible(true);
    }
}
