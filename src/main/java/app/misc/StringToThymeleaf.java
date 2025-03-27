package app.misc;

public class StringToThymeleaf {
    //this is the simplest way i could think of passing a string to a thymeleaf page
    private String string;

    public StringToThymeleaf(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
