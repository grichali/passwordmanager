package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.User;
import repository.DatabaseConnector;
import repository.UserRepository;

public class SignUp {
    private JPanel panel;
    private JButton btnSignUp;
    private JLabel nomLabel, prenomLabel, usernameLabel, passwordLabel, titleLabel;
    private JTextField nomField, prenomField, txtSignUserName;
    private JPasswordField passwordField, txtSignPassword;
    private JFrame parentFrame;

    public SignUp(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
    }

    private void initUI() {
        panel = new JPanel();
        panel.setLayout(null);

        titleLabel = new JLabel("Password Manager");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        titleLabel.setBounds(100, 10, 300, 30);

        JLabel signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
        signUpLabel.setBounds(150, 50, 100, 20);

        nomLabel = new JLabel("Nom");
        nomLabel.setBounds(50, 90, 100, 20);
        nomField = new JTextField();
        nomField.setBounds(150, 90, 200, 25);

        prenomLabel = new JLabel("Prenom");
        prenomLabel.setBounds(50, 120, 100, 20);
        prenomField = new JTextField();
        prenomField.setBounds(150, 120, 200, 25);

        usernameLabel = new JLabel("User Name");
        usernameLabel.setBounds(50, 150, 100, 20);
        txtSignUserName = new JTextField();
        txtSignUserName.setBounds(150, 150, 200, 25);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 180, 100, 20);
        txtSignPassword = new JPasswordField();
        txtSignPassword.setBounds(150, 180, 200, 25);

        btnSignUp = new JButton("Sign Up");
        btnSignUp.setBounds(150, 210, 100, 30);

        panel.add(titleLabel);
        panel.add(signUpLabel);
        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(usernameLabel);
        panel.add(txtSignUserName);
        panel.add(passwordLabel);
        panel.add(txtSignPassword);
        panel.add(btnSignUp);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup();
            }
        });
    }

    private void Signup() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String username = txtSignUserName.getText();
        String password = new String(txtSignPassword.getPassword());

        if (!nom.isEmpty() && !prenom.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            UserRepository userRepository = new UserRepository(databaseConnector);

            User user = new User(nom, prenom, username, password);

            if (userRepository.saveUser(user)) {
                JOptionPane.showMessageDialog(parentFrame, "Sign Up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                SignIn signIn = new SignIn(parentFrame);
                parentFrame.setContentPane(signIn.getPanel());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}

// public class SignUp extends JFrame {

//     private JButton btnSignUp;
//     private JLabel nomLabel, prenomLabel, usernameLabel, passwordLabel, titleLabel;
//     private JTextField nomField, prenomField, txtSignUserName;
//     private JPasswordField passwordField, txtSignPassword;

//     public SignUp() {
//         initUI();
//     }

//     private void initUI() {
//         setTitle("Password Manager - SignUp");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         JPanel panel = new JPanel();
//         panel.setLayout(null);

//         titleLabel = new JLabel("Password Manager");
//         titleLabel.setFont(new Font("Courier New", Font.BOLD, 18));
//         titleLabel.setBounds(100, 10, 300, 30);

//         JLabel signUpLabel = new JLabel("SIGN UP");
//         signUpLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
//         signUpLabel.setBounds(150, 50, 100, 20);

//         nomLabel = new JLabel("Nom");
//         nomLabel.setBounds(50, 90, 100, 20);
//         nomField = new JTextField();
//         nomField.setBounds(150, 90, 200, 25);

//         prenomLabel = new JLabel("Prenom");
//         prenomLabel.setBounds(50, 120, 100, 20);
//         prenomField = new JTextField();
//         prenomField.setBounds(150, 120, 200, 25);

//         usernameLabel = new JLabel("User Name");
//         usernameLabel.setBounds(50, 150, 100, 20);
//         txtSignUserName = new JTextField();
//         txtSignUserName.setBounds(150, 150, 200, 25);

//         passwordLabel = new JLabel("Password");
//         passwordLabel.setBounds(50, 180, 100, 20);
//         txtSignPassword = new JPasswordField();
//         txtSignPassword.setBounds(150, 180, 200, 25);

//         btnSignUp = new JButton("Sign Up");
//         btnSignUp.setBounds(150, 210, 100, 30);

//         panel.add(titleLabel);
//         panel.add(signUpLabel);
//         panel.add(nomLabel);
//         panel.add(nomField);
//         panel.add(prenomLabel);
//         panel.add(prenomField);
//         panel.add(usernameLabel);
//         panel.add(txtSignUserName);
//         panel.add(passwordLabel);
//         panel.add(txtSignPassword);
//         panel.add(btnSignUp);

//         add(panel);
//         setResizable(false);
//         setLocationRelativeTo(null);

//         btnSignUp.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 Signup();
//             }
//         });
//     }

//     private void Signup() {
//         String nom = nomField.getText();
//         String prenom = prenomField.getText();
//         String username = txtSignUserName.getText();
//         String password = new String(txtSignPassword.getPassword());

//         if (!nom.isEmpty() && !prenom.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
//             DatabaseConnector databaseConnector = new DatabaseConnector();
//             UserRepository userRepository = new UserRepository(databaseConnector);

//             User user = new User(nom, prenom, username, password);

//             if (userRepository.saveUser(user)) {
//                 JOptionPane.showMessageDialog(null, "Sign Up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                 SignIn signIn = new SignIn();
//                 signIn.setVisible(true);
//                 this.setVisible(false);
//                 dispose();
//             }
//         } else {
//             JOptionPane.showMessageDialog(this, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     // public static void main(String[] args) {
//     //     SignUp signUp = new SignUp();
//     //     signUp.setVisible(true);
//     // }
// }
