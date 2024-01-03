package SessionManagement;
public class SessionManager {
    private static SessionManager instance;
    private boolean loggedIn;
    private String username;

    private SessionManager() {
        // Private constructor to enforce singleton pattern
        loggedIn = false;
        username = null;
    }
    
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void loginUser(String username) {
        loggedIn = true;
        this.username = username;
    }

    public void logoutUser() {
        loggedIn = false;
        this.username = null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }
}
