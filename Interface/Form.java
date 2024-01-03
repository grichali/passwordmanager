

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

public class Form extends JPanel {

    private JTextField websiteField, emailField, passwordField;
    private JButton addButton, updateButton, deleteButton; // Added deleteButton
    private JTable passwordTable;
    private String username;
    private DatabaseConnector databaseConnector;
    private SavedPasswordRepository passwordRepository;
    private DefaultTableModel tableModel;

    private JFrame parentFrame;
    public Form(JFrame parentFrame, String username) {
        this.username = username;
        this.databaseConnector = new DatabaseConnector();
        this.parentFrame = parentFrame;
        this.passwordRepository = new SavedPasswordRepository(databaseConnector);
        initUI();
        fillTableWithUserPasswords();
    }

    public JPanel getPanel() {
        return this;
    }

    public void initUI() {
        setLayout(null);

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

        tableModel = new DefaultTableModel(data, columnNames);
        passwordTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(passwordTable);
        scrollPane.setBounds(50, 190, 600, 300);

        websiteLabel.setBounds(50, 50, 80, 25);
        websiteField.setBounds(150, 50, 200, 25);

        emailLabel.setBounds(50, 80, 80, 25);
        emailField.setBounds(150, 80, 200, 25);

        passwordLabel.setBounds(50, 110, 80, 25);
        passwordField.setBounds(150, 110, 200, 25);

        addButton = new JButton("Add");
        addButton.setBounds(50, 140, 80, 25);

        updateButton = new JButton("Update");
        updateButton.setBounds(140, 140, 80, 25);

        deleteButton = new JButton("Delete"); // Added deleteButton
        deleteButton.setBounds(230, 140, 80, 25); // Adjusted button position

        add(titleLabel);
        add(websiteLabel);
        add(websiteField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(addButton);
        add(updateButton);
        add(deleteButton); // Added deleteButton
        add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassword();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword();
            }
        });

        deleteButton.addActionListener(new ActionListener() { // ActionListener for deleteButton
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePassword();
            }
        });

        passwordTable.getSelectionModel().addListSelectionListener(e -> { // ListSelectionListener for passwordTable
            int selectedRow = passwordTable.getSelectedRow();
            if (selectedRow != -1) {
                websiteField.setText(passwordTable.getValueAt(selectedRow, 0).toString());
                emailField.setText(passwordTable.getValueAt(selectedRow, 1).toString());
                passwordField.setText(passwordTable.getValueAt(selectedRow, 2).toString());
            }
        });
    }

   

    

    

    private void deletePassword() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow != -1) {
            String website = (String) tableModel.getValueAt(selectedRow, 0);
            String email = (String) tableModel.getValueAt(selectedRow, 1);
            String password = (String) tableModel.getValueAt(selectedRow, 2);

            SavedPasswords savedPassword = new SavedPasswords(username, website, email, password);
            passwordRepository.deletePassword(savedPassword);

            tableModel.removeRow(selectedRow);

            websiteField.setText("");
            emailField.setText("");
            passwordField.setText("");
        }
    }


        


    public void fillTableWithUserPasswords() {
        List<SavedPasswords> passwords = passwordRepository.getPasswordsByUserId(username);
        for (SavedPasswords password : passwords) {
            Object[] row = {password.getWebsitename(), password.getEmail(), password.getPassword()};
            tableModel.addRow(row);
        }
    }

    public void addPassword() {
        String website = websiteField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!website.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            SavedPasswords s = new SavedPasswords(website, email, password, username);

            if (passwordRepository.savePassword(s)) {
                JOptionPane.showMessageDialog(null, "The password has been saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                websiteField.setText("");
                emailField.setText("");
                passwordField.setText("");
                Object[] row = {website, email, password};
                tableModel.addRow(row);
            }
        }
    }

    public void updatePassword() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow != -1) {
            String website = websiteField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (!website.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                SavedPasswords s = new SavedPasswords(website, email, password, username);

                if (passwordRepository.updatePassword(s)) {
                    JOptionPane.showMessageDialog(parentFrame, "The password has been updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    websiteField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    passwordTable.setValueAt(website, selectedRow, 0);
                    passwordTable.setValueAt(email, selectedRow, 1);
                    passwordTable.setValueAt(password, selectedRow, 2);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
              

    // private void fillTableWithUserPasswords() {
    //     List<SavedPasswords> passwordList = this.passwordRepository.getPasswordsByUserId(username);
    
    //     // Convert the list to a two-dimensional array for the table model
    //     Object[][] data = new Object[passwordList.size()][3];
    //     for (int i = 0; i < passwordList.size(); i++) {
    //         SavedPasswords password = passwordList.get(i);
    //         data[i][0] = password.getWebsitename();
    //         data[i][1] = password.getEmail();
    //         data[i][2] = password.getPassword();
    //     }
    
    //     // Update the table model directly
    //     passwordTable.setModel(new DefaultTableModel(data, new Object[]{"Website", "Email", "Password"}));
    // }
    

    // public static void main(String[] args) {
    //     Form form = new Form("q"); 
    //     form.setVisible(true);
    // }


}
