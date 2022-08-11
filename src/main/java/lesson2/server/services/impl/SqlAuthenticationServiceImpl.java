package lesson2.server.services.impl;


import lesson2.server.services.AuthenticationService;

import java.sql.*;

public class SqlAuthenticationServiceImpl implements AuthenticationService {
    private Connection connection;
    private Statement stmt;


    @Override
    public String getUsernameByLoginAndPassword(String login, String password) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT * from auth WHERE login = '%s'", login));

        if (rs.isClosed()) {
            return null;
        }

        String usernameDB = rs.getString("username");
        String passwordDB = rs.getString("password");


        return ((passwordDB != null) && (passwordDB.equals(password))) ? usernameDB : null;
    }

    @Override
    public boolean addNewUserEntry(String loginReg, String passwordReg, String usernameReg) throws SQLException {


        if (!isLoginBusy(loginReg) && !isUsernameBusy(usernameReg)){
            stmt.executeUpdate(String.format("INSERT INTO auth (login, password, username) VALUES ('%s', '%s', '%s')", loginReg, passwordReg, usernameReg));
        }

        ResultSet rs = stmt.executeQuery(String.format("SELECT * from auth WHERE login = '%s'", loginReg));
        if (rs.isClosed()) {
            return false;
        }

        String usernameDB = rs.getString("username");
        String passwordDB = rs.getString("password");


        return ((passwordDB != null) && (passwordDB.equals(passwordReg)) && (usernameDB != null) && (usernameDB.equals(usernameReg)));

    }

    @Override
    public boolean isLoginBusy(String loginReg) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM auth WHERE login = '%s'", loginReg));
        return !rs.isClosed();
    }


    @Override
    public boolean isUsernameBusy(String usernameReg) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM auth WHERE username = '%s'", usernameReg));
        return !rs.isClosed();
    }

    public boolean updateUsernameByLoginAndPassword(String login, String password, String newUsername) throws SQLException {
        if (getUsernameByLoginAndPassword(login, password) != null) {
            stmt.executeUpdate(String.format("UPDATE auth SET username = '%s' WHERE login = '%s'", newUsername, login));
        }

        ResultSet rs = stmt.executeQuery(String.format("SELECT * from auth WHERE login = '%s'", login));
        if (rs.isClosed()) {
            return false;
        }
        String usernameDB = rs.getString("username");
        return ((usernameDB != null) && (usernameDB.equals(newUsername)));
    }

    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/db/mainDB.db");

        stmt = connection.createStatement();
    }

    public void disconnect() throws SQLException {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}