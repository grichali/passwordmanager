
package Interface;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import repository.DatabaseConnector;
import repository.UserRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import SessionManagement.SessionManager;
public class SignIn implements ActionListener {
    private JPanel panel;
    private JLabel usernamLabel, passwordLabel, titleLabel, createAccountLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signinButton;
    private JFrame parentFrame;
    JMenuItem signout = new JMenuItem("Sign Out");
    SessionManager sessionManager = SessionManager.getInstance();

    public SignIn(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
    }

    public void initUI() {
        panel = new JPanel();
        panel.setLayout(null);

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
                sessionManager.loginUser(username);
                signout.addActionListener(this);
                parentFrame.getJMenuBar().getMenu(0).add(signout, 4);
                JOptionPane.showMessageDialog(parentFrame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Form form = new Form(parentFrame, username);
                parentFrame.setContentPane(form.getPanel());
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    private void openSignUpInterface() {
        // Implement your code to open the SignUp interface here
        try {
            SignUp signUp = new SignUp(parentFrame);
            parentFrame.setContentPane(signUp.getPanel());
            parentFrame.revalidate();
            parentFrame.repaint();
        
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object src = e.getSource();
        if (src.equals(signout)) {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logoutUser();
                parentFrame.setContentPane(new SignIn(this.parentFrame).getPanel());
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                parentFrame.getJMenuBar().getMenu(0).remove(signout);
            }
        }
    }

    

    // public static void main(String[] args) {
    //     SignIn signIn = new SignIn();
    //     signIn.setVisible(true);
    // }
}
