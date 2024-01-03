

package Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import repository.DatabaseConnector;
import Model.SavedPasswords;
import repository.SavedPasswordRepository;

public class Form {

    private JPanel mainPanel = null;
    private JTextField websiteField, emailField, passwordField;
    private JButton addButton;
    private JTable passwordTable;
    private String username;
    private DatabaseConnector databaseConnector;
    private SavedPasswordRepository passwordRepository;
    private JFrame parentFrame;


    public Form(JFrame parentFrame, String username) {
        this.username = username;
        this.databaseConnector= new DatabaseConnector();
        this.passwordRepository = new SavedPasswordRepository(databaseConnector);
        this.parentFrame = parentFrame;
        initUI();
        fillTableWithUserPasswords();
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }

    public void initUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Password Manager");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        titleLabel.setBounds(200, 10, 300, 30);

        JLabel websiteLabel = new JLabel("Website:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        websiteField = new JTextField();
        emailField = new JTextField();
        passwordField = new JTextField();

        String[] columnNames = {"Website", "Email", "Password"};
        Object[][] data = {};

        passwordTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(passwordTable);
        scrollPane.setBounds(50, 150, 500, 150);

        websiteLabel.setBounds(50, 50, 80, 25);
        websiteField.setBounds(150, 50, 200, 25);

        emailLabel.setBounds(50, 80, 80, 25);
        emailField.setBounds(150, 80, 200, 25);

        passwordLabel.setBounds(50, 110, 80, 25);
        passwordField.setBounds(150, 110, 200, 25);

        addButton = new JButton("Add");
        addButton.setBounds(360, 110, 150, 25);

        mainPanel.add(titleLabel);
        mainPanel.add(websiteLabel);
        mainPanel.add(websiteField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(addButton);
        mainPanel.add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addpassword();
            }
        });
    }

    public void addpassword() {
        String website = websiteField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!website.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            SavedPasswords s = new SavedPasswords(website, email, password, username);

            if (this.passwordRepository.savePassword(s)) {
                JOptionPane.showMessageDialog(null, "The password has been saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                websiteField.setText("");
                emailField.setText("");
                passwordField.setText("");
            }
        }
    }


    // After saving, update the table with the new data
    private void fillTableWithUserPasswords() {
        List<SavedPasswords> passwordList = this.passwordRepository.getPasswordsByUserId(username);
    
        // Convert the list to a two-dimensional array for the table model
        Object[][] data = new Object[passwordList.size()][3];
        for (int i = 0; i < passwordList.size(); i++) {
            SavedPasswords password = passwordList.get(i);
            data[i][0] = password.getWebsitename();
            data[i][1] = password.getEmail();
            data[i][2] = password.getPassword();
        }
    
        // Update the table model directly
        passwordTable.setModel(new DefaultTableModel(data, new Object[]{"Website", "Email", "Password"}));
    }
    

    // public static void main(String[] args) {
    //     Form form = new Form("q"); 
    //     form.setVisible(true);
    // }


}
