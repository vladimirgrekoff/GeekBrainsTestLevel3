package lesson6.task1.server.services.impl;

import lesson6.task1.server.models.User;
import lesson6.task1.server.services.AuthenticationService;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthenticationServiceImpl implements AuthenticationService {

    private  List<User> clients = new ArrayList<>(List.of(
            new User("martin", "1111", "Martin_Superstar"),
            new User("batman", "2222", "Брюс_Уэйн"),
            new User("gena", "3333", "Гендальф_Серый"),
            new User("mario", "4444", "Super_Mario"),
            new User("bender", "5555", "Bender"),
            new User("ezhik", "6666", "Super_Sonic")
    ));

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User client : clients) {
            if (client.getLogin().equals(login) && client.getPassword().equals(password) ) {
                return client.getUsername();
            }
        }
        return null;
    }

    @Override
    public boolean addNewUserEntry(String loginReg, String passwordReg, String usernameReg) {
        boolean result = false;
        User newUser = new User(loginReg,passwordReg,usernameReg);

        if (!isLoginBusy(loginReg) && !isUsernameBusy(usernameReg)){
            clients.add(newUser);
        }

        for (User client : clients) {
            if (client.getLogin().equals(loginReg) && client.getPassword().equals(passwordReg) && client.getUsername().equals(usernameReg)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean isLoginBusy(String loginReg) {
        boolean result = false;

        for (User client : clients) {
            if (client.getLogin().equals(loginReg)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean isUsernameBusy(String usernameReg) {
        boolean result = false;

        for (User client : clients) {
            if (client.getUsername().equals(usernameReg)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean updateUsernameByLoginAndPassword(String login, String password, String newUsername) {
        String oldName;
        boolean result = false;
        for (User client : clients) {
            if (client.getLogin().equals(login) && client.getPassword().equals(password)) {
                client.setUsername(newUsername);
                result = true;
            }
        }
        return result;
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }
}