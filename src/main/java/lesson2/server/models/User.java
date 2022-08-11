package lesson2.server.models;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
    private String username;

    public User(String login, String password, String username) {
        this.login = login;
        this.password = password;
        this.username = username;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
