package lesson2.server.handlers;

import lesson2.server.MyServer;
import lesson2.server.services.AuthenticationService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;


public class ClientHandler {
    private static final String AUTH_CMD_PREFIX = "/auth"; // + login + password
    private static final String AUTH_OK_CMD_PREFIX = "/authOk"; // + username
    private static final String AUTH_ERR_CMD_PREFIX = "/authErr"; // + error message
    private static final String REG_CMD_PREFIX = "/reg"; // + login + password + username
    private static final String REG_OK_CMD_PREFIX = "/regOk"; // + entry
    private static final String REG_ERR_CMD_PREFIX = "/regErr"; // + error message
    private static final String REG_EDIT_CMD_PREFIX = "/regEdit"; // + login + password + newUsername
    private static final String REG_EDIT_OK_CMD_PREFIX = "/regEditOk"; // + newUsername
    private static final String CLIENT_MSG_CMD_PREFIX = "/cMsg"; // + msg
    private static final String SERVER_ECHO_MSG_CMD_PREFIX = "/echo"; // + msg

    private static final String SERVER_MSG_CMD_PREFIX = "/sMsg"; // + msg

    private static final String SERVER_MSG_CMD_USERS_PREFIX = "/userList"; // + msg
    private static final String PRIVATE_MSG_CMD_PREFIX = "/pm"; // + username + msg
    private static final String CONNECT_CMD_PREFIX = "/connect"; // + login + password
    private static final String STOP_SERVER_CMD_PREFIX = "/stop";
    private static final String END_CLIENT_CMD_PREFIX = "/end";
    private MyServer myServer;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(MyServer myServer, Socket socket) {

        this.myServer = myServer;
        clientSocket = socket;
    }


    public void handle() throws IOException {
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        sendServerToClientMessage(String.format("%s/%s",CONNECT_CMD_PREFIX,"СВЯЗЬ УСТАНОВЛЕНА\nВам необходимо войти в чат в течении\n2 минут, или соединение закроется"));// + "\n");//


        new Thread(() -> {
            try {
                authentication();
                readMessage();
            } catch (IOException | SQLException e) {
                try {
                    throw new SocketException("соединение сброшено");
                } catch (SocketException ex) {
//                    throw new RuntimeException(ex);
                    System.out.println("Клиент сбросил соединение");
                }
                System.out.println("Данные не получены");
//                e.printStackTrace();
            }
        }).start();
    }

    private void authentication() throws IOException, SQLException {

        while (true) {
            String message = in.readUTF();
            sendEchoMessage(message);

            if (message.equals(CONNECT_CMD_PREFIX)) {
                completedConnection(message);
                continue;
            }
            if (message.startsWith(REG_CMD_PREFIX) || message.startsWith(REG_EDIT_CMD_PREFIX)) {
                boolean isSuccessReg;

                isSuccessReg = processRegistration(message);
                if (isSuccessReg) {
                    continue;
                }


            } else if (message.startsWith(AUTH_CMD_PREFIX)) {
                boolean isSuccessAuth = processAuthentication(message);
                if (isSuccessAuth) {
                    break;
                }
            } else {
                sendServerToClientMessage(AUTH_ERR_CMD_PREFIX + "/" + "Неверная команда аутентификации");// + "\n");
                System.out.println("Неверная команда аутентификации");
            }

        }
    }
    private boolean processRegistration(String message) throws IOException, SQLException {

        int indexStart;
        int indexEnd;

        message = message.trim();
        indexStart = message.indexOf("/");
        indexEnd = message.indexOf("/", indexStart + 1);
        String typeMessage = message.substring(0, indexEnd);
        message = message.replaceAll(typeMessage,"");
        String[] parts = message.split("/");
        parts[0] = typeMessage;

        boolean returnResult = false;
        boolean isRegistered = false;
        boolean isEdited = false;

        if (parts.length != 4) {
            sendServerToClientMessage(REG_ERR_CMD_PREFIX + "/" + "Неверная команда регистрации");// + "\n");
            System.out.println("Неверная команда регистрации");
            return false;
        }

        String loginReg = parts[1];
        String passwordReg = parts[2];
        String usernameReg = parts[3];

        AuthenticationService reg = myServer.getAuthenticationService();

        if (parts[0].equalsIgnoreCase(REG_CMD_PREFIX)) {
            isRegistered = reg.addNewUserEntry(loginReg, passwordReg,usernameReg);
        } else {
            isEdited = reg.updateUsernameByLoginAndPassword(loginReg, passwordReg, usernameReg);
        }

        if (isEdited) {
            sendServerToClientMessage(REG_EDIT_OK_CMD_PREFIX + "/" + "Имя пользователя изменено");// + "\n");
            System.out.println("Произведена смена имени пользователя");
            returnResult = true;
        } else if (isRegistered) {
            sendServerToClientMessage(REG_OK_CMD_PREFIX + "/" + "Регистрация успешна");// + "\n");
            System.out.println("Новый пользователь зарегистрирован");
            returnResult = true;
        } else {
            if (reg.isLoginBusy(loginReg) && reg.isUsernameBusy(usernameReg)) {
                sendServerToClientMessage(REG_ERR_CMD_PREFIX + "/" + "Пользователь с таким логином и именем уже зарегитрирован");// + "\n");
                System.out.println("Пользователь с таким логином и именем уже зарегитрирован");
            } else if (reg.isUsernameBusy(usernameReg)) {
                sendServerToClientMessage(REG_ERR_CMD_PREFIX + "/" + "Имя пользователя уже используется");// + "\n");
                System.out.println("Имя пользователя уже используется");
            } else if (reg.isLoginBusy(loginReg)) {
                sendServerToClientMessage(REG_ERR_CMD_PREFIX + "/" + "Логин уже используется");
                System.out.println("Логин уже используется");
            }
        }
        return returnResult;
    }
    private boolean processAuthentication(String message) throws IOException, SQLException {
        int indexStart;
        int indexEnd;

        message = message.trim();
        indexStart = message.indexOf("/");
        indexEnd = message.indexOf("/", indexStart + 1);
        String typeMessage = message.substring(0, indexEnd);
        message = message.replaceAll(typeMessage,"");
        String[] parts = message.split("/");
        parts[0] = typeMessage;

        if (parts.length != 3) {
            sendServerToClientMessage(AUTH_ERR_CMD_PREFIX + "/" + "Неверная команда аутентификации");// + "\n");
            System.out.println("Неверная команда аутентификации");
            return false;
        }

        String login = parts[1];
        String password = parts[2];

        AuthenticationService auth = myServer.getAuthenticationService();

        username = auth.getUsernameByLoginAndPassword(login, password);

        if (username != null) {
            if (myServer.isUsernameBusy(username)) {
                sendServerToClientMessage(AUTH_ERR_CMD_PREFIX + "/" + "Логин уже используется");
                return false;
            }

            myServer.subscribe(this);
            sendServerToClientMessage(AUTH_OK_CMD_PREFIX + "/" + username);// + "\n");
            myServer.broadcastMessage(this, SERVER_MSG_CMD_PREFIX, "подключился к чату");
            System.out.println("Пользователь " + username +  " подключился к чату");
            myServer.updateUserListInChat(SERVER_MSG_CMD_USERS_PREFIX, username);

            return true;
        } else {
            sendServerToClientMessage(AUTH_ERR_CMD_PREFIX + "/" + "Неверная комбинация логина и пароля");
            return false;
        }
    }


    private void readMessage() throws IOException {
        int indexStart;
        int indexEnd;
        String typeMessage;

        try {
            while (true) {

                String message = in.readUTF();
                System.out.println("message | " + username + ": " + message);
                sendEchoMessage(message);

                if (message.length() != 0) {
                    if (message.contains("/")) {
                        indexStart = message.indexOf("/", 0);
                        indexEnd = message.indexOf("/", indexStart + 1);
                        if (indexEnd > message.length() || indexEnd < 0) {
                            indexEnd = message.length();
                        }
                        typeMessage = message.substring(0, indexEnd);
                    } else {
                        typeMessage = CLIENT_MSG_CMD_PREFIX;
                    }

                    switch (typeMessage) {
                        case STOP_SERVER_CMD_PREFIX -> myServer.stop();
                        case END_CLIENT_CMD_PREFIX -> closeConnection();
                        case PRIVATE_MSG_CMD_PREFIX -> myServer.sendingPrivateMessage(this, message);

                        default -> myServer.broadcastMessage(this, CLIENT_MSG_CMD_PREFIX, message);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Пользователь отключился");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void completedConnection(String clientMessage) throws IOException {

        if (clientMessage.startsWith(CONNECT_CMD_PREFIX)) {
            sendServerToClientMessage("/" + "СОЕДИНЕНИЕ ПРОШЛО УСПЕШНО");

        }
    }
    private void closeConnection() throws IOException {
        String name = username;
        try {

            myServer.broadcastMessage(this, SERVER_MSG_CMD_PREFIX ,"покинул чат");// + "\n");
            myServer.unSubscribe(this);
            clientSocket.close();
            myServer.updateUserListInChat(SERVER_MSG_CMD_USERS_PREFIX, username);
        } catch (SocketException e) {
            System.out.println(name + " отключился");
            System.out.println("Сокет закрыт");
        }
    }

    public void sendServerToClientMessage(String serverMessage) throws IOException {
        out.writeUTF(serverMessage);
        out.flush();
    }
    public void sendMessage(String sender, String typeMessage, String message) throws IOException {
        out.writeUTF(String.format("%s/%s/%s", typeMessage, sender, message));
        out.flush();
    }
    public void sendPrivateMessage(String sender, String message) throws IOException {
        out.writeUTF(String.format("%s/%s/%s", PRIVATE_MSG_CMD_PREFIX, sender, message));
        out.flush();
    }
    public void sendEchoMessage(/*String sender, */String message) throws IOException {
        out.writeUTF(String.format("%s%s", SERVER_ECHO_MSG_CMD_PREFIX, message));
        out.flush();
    }

    public String getUsername() {
        return username;
    }

}
