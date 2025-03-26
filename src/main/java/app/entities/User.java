package app.entities;

public class User {

    int id;
    String email;
    String password;
    String role;
    double credit;

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
}
