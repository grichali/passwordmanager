package Model;


public class User {
    
     private String nom ;
     private String prenom ; 
     private String username ;
     private String password ;
     

     public User(String nom, String prenom, String username, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
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

}