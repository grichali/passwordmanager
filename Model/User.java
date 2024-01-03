package Model;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    
     private String nom ;
     private String prenom ; 
     private String username ;
     private String password;
     private String encryptedPassword;
    
     public User(String nom, String prenom, String username, String password, boolean isEncrypted) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        if (isEncrypted) {
            this.password = null;
            this.encryptedPassword = password;
        } else {
            this.password = password;
            this.encryptedPassword = encryptPassword(password);
        }
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    private String encryptPassword(String password) {
        // Implement your code to encrypt the password here
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return encryptedPassword;
    }
   
}