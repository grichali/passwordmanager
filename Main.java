import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Interface.SignIn;
import Interface.SignUp;
import Model.User;
import Model.SavedPasswords;
import repository.UserRepository;
import repository.SavedPasswordRepository;
import repository.DatabaseConnector;


public class Main implements ActionListener { 


    JFrame mainFen;
    JMenuBar menuBar;
    JMenu userMenu;
    JMenuItem userAccount, quitter, addPassword, signin, signup;
    boolean isLogged = false;

    public void start() {
        mainFen = new JFrame("Password Manager");
        mainFen.setSize(600, 400);
        mainFen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        userMenu = new JMenu("User");
        userAccount = new JMenuItem("User Account");
        quitter = new JMenuItem("Quitter");
        addPassword = new JMenuItem("Add Password");
        signin = new JMenuItem("Sign In");
        signup = new JMenuItem("Sign Up");

        quitter.addActionListener(this);
        userAccount.addActionListener(this);
        addPassword.addActionListener(this);
        signin.addActionListener(this);
        signup.addActionListener(this);



        userMenu.add(userAccount);
        userMenu.add(addPassword);
        userMenu.add(signin);
        userMenu.add(signup);
        userMenu.add(quitter);
        menuBar.add(userMenu);
        mainFen.setJMenuBar(menuBar);
        mainFen.setLocationRelativeTo(null);
        mainFen.setVisible(true);
        mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
        mainFen.revalidate();
        mainFen.repaint();
    }

    public static void main(String[] args) {
        // Initialize the database connector and repositories
        // DatabaseConnector databaseConnector = new DatabaseConnector();
        // UserRepository userRepository = new UserRepository(databaseConnector);
        // SavedPasswordRepository passwordRepository = new SavedPasswordRepository(databaseConnector);

        // Create a new user account
        // User newUser = new User("Ali", "Grich", "ali.grich", "password123");
        // userRepository.saveUser(newUser);

        // Add passwords for the user
        // SavedPasswords password1 = new SavedPasswords("Website1", "ali.grich@gmail.com", "pass123", newUser.getUsername());
        // passwordRepository.savePassword(password1);

        // SavedPasswords password2 = new SavedPasswords("Website2", "ali.grich@yahoo.com", "pass456", newUser.getUsername());
        // passwordRepository.savePassword(password2);

        // Retrieve all passwords for the user
        // List<SavedPasswords> userPasswords = passwordRepository.getPasswordsByUserId(newUser.getUsername());
        // for (SavedPasswords password : userPasswords) {
        //     System.out.println("Website: " + password.getWebsitename());
        //     System.out.println("Email: " + password.getEmail());
        //     System.out.println("Password: " + password.getPassword());
        //     System.out.println("User ID: " + password.getUserid());
        //     System.out.println("------------------------");
        // }

        // 

        new Main().start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object src = e.getSource();
        if (src == quitter) {
            System.exit(0);
        } else if (src == userAccount) {
            if (isLogged) {
                // new UserAccount().setVisible(true);
            } else {
                mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            }
        } else if (src == addPassword) {
            if (isLogged) {
                // new Form().setVisible(true);
            } else {
                mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            }
        } else if (src == signin) {
            mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
            mainFen.revalidate();
            mainFen.repaint();
        } else if (src == signup) {
            SignUp signupForm = new SignUp(this.mainFen);
            mainFen.setContentPane(signupForm.getPanel());
            mainFen.revalidate();
            mainFen.repaint();
        }
       
    }
    


}
