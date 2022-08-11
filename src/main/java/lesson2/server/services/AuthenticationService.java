package lesson2.server.services;

import java.sql.SQLException;

public interface AuthenticationService {
    String getUsernameByLoginAndPassword(String login, String password) throws SQLException;

    boolean addNewUserEntry(String loginReg, String passwordReg, String usernameReg) throws SQLException;

    boolean isLoginBusy(String loginReg) throws SQLException;

    boolean isUsernameBusy(String usernameReg) throws SQLException;

    boolean updateUsernameByLoginAndPassword(String login, String password, String newUsername) throws SQLException;
    void connect() throws SQLException, ClassNotFoundException;

    void disconnect() throws SQLException;

}