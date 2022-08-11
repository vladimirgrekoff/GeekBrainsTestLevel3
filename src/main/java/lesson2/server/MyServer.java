package lesson2.server;


import lesson2.server.handlers.ClientHandler;
import lesson2.server.services.AuthenticationService;
import lesson2.server.services.impl.SqlAuthenticationServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyServer {

    private final ServerSocket serverSocket;
    private final AuthenticationService authenticationService;
    private final ArrayList<ClientHandler> clients;

    public MyServer(int port) throws IOException, SQLException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        authenticationService = new SqlAuthenticationServiceImpl();
//        authenticationService = new SimpleAuthenticationServiceImpl();

        clients = new ArrayList<>();

    }


    public void start() throws SQLException, ClassNotFoundException {
        System.out.println("СЕРВЕР ЗАПУЩЕН!");
        System.out.println("---------------");
        authenticationService.connect();

        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        System.out.println("Ожидание клиента...");
        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключился!");
        processClientConnection(socket);
    }

    private void processClientConnection(Socket socket) throws IOException {
        ClientHandler handler = new ClientHandler(this, socket);
        handler.handle();
    }

    public synchronized void subscribe(ClientHandler handler) {
        clients.add(handler);
    }

    public synchronized void unSubscribe(ClientHandler handler) {
        clients.remove(handler);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void stop() throws SQLException {
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println("ЗАВЕРШЕНИЕ РАБОТЫ");
        authenticationService.disconnect();
        System.exit(0);
    }

    public void updateUserListInChat(String typeMessage, String clientAuth) throws IOException {
        StringBuilder userList = new StringBuilder(typeMessage);
        String username;
        for (ClientHandler client : clients) {
            username = client.getUsername();
            userList.append("/").append(username);
        }
        sendUserListInChat(userList.toString());
    }
    public synchronized void sendUserListInChat(String message) throws IOException {
        for (ClientHandler client : clients) {
                client.sendServerToClientMessage(message);
            }

    }
    public synchronized void broadcastMessage(ClientHandler sender, String typeMessage, String message) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
//                client.sendEchoMessage(sender.getUsername(), message);
                continue;
            } else {
                client.sendMessage(sender.getUsername(), typeMessage, message);
            }
        }
    }

    public synchronized void sendingPrivateMessage(ClientHandler sender, String message) throws IOException {

        int indexStart;
        int indexEnd;
        indexStart = message.indexOf("/",0);
        indexEnd = message.indexOf("/",indexStart+1);
        String typeMessage = message.substring(0,indexEnd);
        message = message.replaceAll(typeMessage, "").trim();
        message = message.substring(1);

        String[] parts = message.split("/");
        String recipient = parts[0].trim();
        parts[0] = "";

        String privateMessage = (String.join(" ", parts)).trim();

        for (ClientHandler client : clients) {
            if (client.getUsername().equals(recipient)) {
                client.sendPrivateMessage(sender.getUsername(), privateMessage);
                return;
            }
        }
//            sender.sendEchoMessage(sender.getUsername(),message);
    }

}