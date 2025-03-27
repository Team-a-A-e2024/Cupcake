package app.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Double.compare(credit, user.credit) == 0 && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, credit);
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
