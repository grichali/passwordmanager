package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.savedpasswords;

public class savedpasswordRepository {
    private DatabaseConnector databaseConnector;

    public savedpasswordRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    // Method to save a savedpasswords entity to the database
    public boolean savePassword(savedpasswords savedpassword) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO savedpasswords (websitename, email, password, userid) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, savedpassword.getWebsitename());
                statement.setString(2, savedpassword.getEmail());
                statement.setString(3, savedpassword.getPassword());
                statement.setString(4, savedpassword.getUserid());
    
                int rowsAffected = statement.executeUpdate();
    
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    


    public savedpasswords getPasswordByWebsite(String websiteName) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM savedpasswords WHERE websitename = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, websiteName);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return mapResultSetToSavedPassword(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no password is found or an error occurs
    }

    // Method to update a savedpasswords entity in the database
    public void updatePassword(savedpasswords savedpassword) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE savedpasswords SET email=?, password=?, userid=? WHERE websitename=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, savedpassword.getEmail());
                statement.setString(2, savedpassword.getPassword());
                statement.setString(3, savedpassword.getUserid());
                statement.setString(4, savedpassword.getWebsitename());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a savedpasswords entity by website name
    public void deletePasswordByWebsite(String websiteName) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM savedpasswords WHERE websitename=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, websiteName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Method to retrieve all savedpasswords entities by user ID
public List<savedpasswords> getPasswordsByUserId(String userId) {
    List<savedpasswords> passwordList = new ArrayList<>();
    try (Connection connection = databaseConnector.getConnection()) {
        String sql = "SELECT * FROM savedpasswords WHERE userid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    passwordList.add(mapResultSetToSavedPassword(result));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return passwordList;
}


    // Helper method to map a ResultSet to a savedpasswords entity
    private savedpasswords mapResultSetToSavedPassword(ResultSet resultSet) throws SQLException {
        String websitename = resultSet.getString("websitename");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String userid = resultSet.getString("userid");
        return new savedpasswords(websitename, email, password, userid);
    }


}
