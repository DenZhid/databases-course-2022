package entities;

public class Customer {

    private final long id;  //Для использования Dao
    private final String login;
    private final String password;
    private final String email;

    public Customer(long id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    // Используется генератором, поле id, неважно
    public Customer(String login, String password, String email) {
        this.id = 0;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
