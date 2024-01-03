package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;

public class UserRepository {
    private DatabaseConnector databaseConnector;

    public UserRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }
    
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return new User(nom, prenom, username, password, true);
    }

    public boolean saveUser(User user) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO users (nom, prenom, username, password) VALUES (?, ?, ?, ?)";
            System.out.println("Saving user: " + user.getUsername()); // Add this line
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                System.out.println("SQL Query: " + sql);
                System.out.println("Parameters: " + user.getNom() + ", " + user.getPrenom() + ", " + user.getUsername() + ", " + user.getPassword());
                
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getUsername());
                statement.setString(4, user.getEncryptedPassword());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                }
                else{
                    return false;
                }      
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return mapResultSetToUser(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user is found or an error occurs
    }

    public void updateUser(User user) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE users SET nom=?, prenom=?, password=? WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getEncryptedPassword());
                statement.setString(4, user.getUsername());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM users WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        User user = mapResultSetToUser(result);
                        if (user.checkPassword(password)) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }
}
