package Model;

/**
 * Created by vtnhan on 12/22/2017.
 */

public class User {
    public String Email;
    public String Name;
    public String Pass;
    public String Phone;

    public User(String email, String name, String pass) {
        Email = email;
        Name = name;
        Pass = pass;
    }

    public User() {
    }
}
