import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Interface.*;
import SessionManagement.SessionManager;


public class Main implements ActionListener { 


    JFrame mainFen;
    JMenuBar menuBar;
    JMenu userMenu;
    JMenuItem userAccount, quitter, addPassword, signin, signup, signout;
    SessionManager sessionManager = SessionManager.getInstance();

    public void start() {
        mainFen = new JFrame("Password Manager");
        mainFen.setSize(600, 400);
        mainFen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        userMenu = new JMenu("User");
        userAccount = new JMenuItem("User Account");
        quitter = new JMenuItem("Quit");
        addPassword = new JMenuItem("Add Password");
        signin = new JMenuItem("Sign In");
        signup = new JMenuItem("Sign Up");
        signout = new JMenuItem("Sign Out");

        quitter.addActionListener(this);
        userAccount.addActionListener(this);
        addPassword.addActionListener(this);
        signin.addActionListener(this);
        signup.addActionListener(this);
        signout.addActionListener(this);


        userMenu.add(userAccount);
        userMenu.add(addPassword);
        userMenu.add(signin);
        userMenu.add(signup);
        if (sessionManager.isLoggedIn()) {
            userMenu.add(signout);
        }
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
        
        new Main().start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object src = e.getSource();
        if (src.equals(quitter)) {
            System.exit(0);
        } else if (src.equals(userAccount)) {
            if (sessionManager.isLoggedIn()) {
                mainFen.setContentPane(new UserAccount(mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            } else {
                mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            }
        } else if (src.equals(addPassword)) {
            if (sessionManager.isLoggedIn()) {
                Form form = new Form(mainFen, sessionManager.getUsername());
                mainFen.setContentPane(form.getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            } else {
                mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            }
        } else if (src.equals(signin)) {
            mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
            mainFen.revalidate();
            mainFen.repaint();
        } else if (src.equals(signup)) {
            SignUp signupForm = new SignUp(this.mainFen);
            mainFen.setContentPane(signupForm.getPanel());
            mainFen.revalidate();
            mainFen.repaint();
        } else if (src.equals(signout)) {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logoutUser();
                mainFen.setContentPane(new SignIn(this.mainFen).getPanel());
                mainFen.revalidate();
                mainFen.repaint();
            } else {
                userMenu.remove(signout);
            }
            
        } else if (src.equals(userMenu)) {
            if (!sessionManager.isLoggedIn()) {
                userMenu.remove(signout);
            }
        }
       
    }
    


}
