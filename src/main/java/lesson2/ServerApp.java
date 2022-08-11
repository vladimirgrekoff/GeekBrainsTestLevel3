package lesson2;

import lesson2.server.MyServer;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ServerApp {

    private static final int DEFAULT_PORT = 8086;
    private static String configsFile = "src/main/resources/configs/application-dev.properties";

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(configsFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int port;

        try {
            port = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            port = DEFAULT_PORT;
        }

        try {
            new MyServer(port).start();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
