package app.models;

public class User {
    private int id;
    private String role;
    private String email;
    private String password;
    private double credit;


    public User(int id, String role, String email, String password, double credit) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }


    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getCredit() {
        return credit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}