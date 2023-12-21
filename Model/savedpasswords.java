package Model;

public class savedpasswords {

    private String websitename;
    private String email;
    private String password;
    private String userid;

    public savedpasswords(String websitename, String email, String password, String userid) {
        this.websitename = websitename;
        this.email = email;
        this.password = password;
        this.userid = userid;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
