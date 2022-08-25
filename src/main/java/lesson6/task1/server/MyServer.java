package lesson6.task1.server;


import lesson6.task1.server.handlers.ClientHandler;
import lesson6.task1.server.services.AuthenticationService;
import lesson6.task1.server.services.impl.SqlAuthenticationServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class MyServer {
    ////////////////////////escape sequences///////////////////////////
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREY = "\u001B[37m";
    public static final String ANSI_WHITE = "\u001B[17m";
    ///////////////////////////////////////////////////////////////////

    private static final Logger logger = Logger.getLogger(MyServer.class.getName());

    private final ServerSocket serverSocket;
    private final AuthenticationService authenticationService;
    private final ArrayList<ClientHandler> clients;

    public MyServer(int port) throws IOException, SQLException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        authenticationService = new SqlAuthenticationServiceImpl();
//        authenticationService = new SimpleAuthenticationServiceImpl();

        clients = new ArrayList<>();

        logger.setLevel(Level.ALL);
        Handler flHandler = new FileHandler("src/main/resources/logs/ChatLogFromJULLogger.%u.%g.log");
        logger.addHandler(flHandler);
        flHandler.setLevel(Level.ALL);

        Handler cnslHandler = new ConsoleHandler();
        logger.addHandler(cnslHandler);

        cnslHandler.setLevel(Level.ALL);
        cnslHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String formatString = "";
                if (record.getLevel().equals(Level.SEVERE)) {
                    formatString = String.format(ANSI_RED + "%s\t%s\t%s\t%s\t%s%n" + ANSI_RESET,
                            record.getLevel(),
                            new Date(record.getMillis()),
                            record.getMessage(),
                            record.getSourceClassName(),
                            record.getSourceMethodName());
                } else if (record.getLevel().equals(Level.WARNING)) {
                    formatString = String.format(ANSI_YELLOW + "%s\t%s\t%s\t%s\t%s%n" + ANSI_RESET,
                            record.getLevel(),
                            new Date(record.getMillis()),
                            record.getMessage(),
                            record.getSourceClassName(),
                            record.getSourceMethodName());
                } else if (record.getLevel().equals(Level.FINE)) {
                    formatString = String.format(ANSI_GREEN + "%s\t%s\t%s\t%s\t%s%n" + ANSI_RESET,
                            record.getLevel(),
                            new Date(record.getMillis()),
                            record.getMessage(),
                            record.getSourceClassName(),
                            record.getSourceMethodName());
                } else if (record.getLevel().equals(Level.INFO)) {
                    formatString = String.format(ANSI_WHITE + "%s:\t%s%n" + ANSI_RESET,
                            record.getLevel(),
                            record.getMessage());
                }
                return formatString;
            }

        });


    }


    public void start() throws SQLException, ClassNotFoundException {
        logger.log(Level.INFO,"СЕРВЕР ЗАПУЩЕН!");
        logger.log(Level.INFO,"---------------");
        authenticationService.connect();

        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.toString(), e);
        }
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        logger.log(Level.INFO,"Ожидание клиента...");
        Socket socket = serverSocket.accept();
        logger.log(Level.INFO,"Клиент подключился!");
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

    public static Logger getLogger() {
        return logger;
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
        logger.log(Level.INFO,"---------------");
        logger.log(Level.INFO,"---------------");
        logger.log(Level.INFO,"ЗАВЕРШЕНИЕ РАБОТЫ");
        authenticationService.disconnect();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Handler handler : logger.getHandlers())
            {
                handler.close();
            }
        }));
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