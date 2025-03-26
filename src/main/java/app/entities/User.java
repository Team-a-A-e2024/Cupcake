package app.entities;

public class User {

    private int id;
    private String email;
    private String password;
    private String role;
    private double credit;

    public User(int id, String email, String password, String role, double credit) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.credit = credit;
    }
    public User(String email, String password, String role, double credit) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.credit = credit;
    }

    //getter and setters from here on

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
