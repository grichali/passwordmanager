import java.util.List;

import Model.User;
import Model.savedpasswords;
import repository.UserRepository;
import repository.savedpasswordRepository;
import repository.DatabaseConnector;

public class Main { 

    public static void main(String[] args) {
        // Initialize the database connector and repositories
        DatabaseConnector databaseConnector = new DatabaseConnector();
        UserRepository userRepository = new UserRepository(databaseConnector);
        savedpasswordRepository passwordRepository = new savedpasswordRepository(databaseConnector);

        // Create a new user account
        User newUser = new User("Ali", "Grich", "ali.grich", "password123");
        userRepository.saveUser(newUser);

        // Add passwords for the user
        savedpasswords password1 = new savedpasswords("Website1", "ali.grich@gmail.com", "pass123", newUser.getUsername());
        passwordRepository.savePassword(password1);

        savedpasswords password2 = new savedpasswords("Website2", "ali.grich@yahoo.com", "pass456", newUser.getUsername());
        passwordRepository.savePassword(password2);

        // Retrieve all passwords for the user
        List<savedpasswords> userPasswords = passwordRepository.getPasswordsByUserId(newUser.getUsername());
        for (savedpasswords password : userPasswords) {
            System.out.println("Website: " + password.getWebsitename());
            System.out.println("Email: " + password.getEmail());
            System.out.println("Password: " + password.getPassword());
            System.out.println("User ID: " + password.getUserid());
            System.out.println("------------------------");
        }
    }
}
