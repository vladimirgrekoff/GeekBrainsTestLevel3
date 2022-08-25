package lesson6.task1;

import lesson6.task1.server.MyServer;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.*;

public class ServerApp {

    private static final int DEFAULT_PORT = 8086;
    private static String configsFile = "src/main/resources/configs/application-dev.properties";

    private static Logger logger;

    public static void main(String[] args) throws IOException {


        try( FileInputStream ins = new FileInputStream("src/main/resources/logs/config/jul.properties")){
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(ServerApp.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            logger.log(Level.SEVERE, e.toString(), e);
        }


        logger.setLevel(Level.ALL);

        Handler fHandler = new FileHandler("src/main/resources/logs/ChatLogFromJULLogger.%u.%g.log");
        logger.addHandler(fHandler);
        Handler cHandler = new ConsoleHandler();
        logger.addHandler(cHandler);

        fHandler.setLevel(Level.ALL);
        fHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("%s\t%s\t%s\t%s\t%s%n",
                        record.getLevel(),
                        new Date(record.getMillis()),
                        record.getMessage(),
                        record.getSourceClassName(),
                        record.getSourceMethodName());
            }
        });

        Handler cnslHandler = new ConsoleHandler();
        logger.addHandler(cnslHandler);
        cnslHandler.setLevel(Level.SEVERE);
        cnslHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("%s\t%s\t%s\t%s\t%s%n",
                        record.getLevel(),
                        new Date(record.getMillis()),
                        record.getMessage(),
                        record.getSourceClassName(),
                        record.getSourceMethodName());
            }
        });


        Properties properties = new Properties();
        try {
            properties.load(new FileReader(configsFile));
        } catch (IOException e) {logger.log(Level.SEVERE, e.toString(), e);
        }

        int port;

        try {
            port = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            port = DEFAULT_PORT;
        }

        try {
            new MyServer(port).start();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }
}
