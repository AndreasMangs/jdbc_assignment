package se.lexicon.g33.andreas.data;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        FileReader reader = null;
        try{
            reader = new FileReader("src/main/resources/dbconnection.properties");
            properties.load(reader);
        }catch (IOException e){
            e.printStackTrace();
        }
        URL = properties.getProperty("URL");
        USER = properties.getProperty("user");
        PASSWORD = properties.getProperty("password");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER,PASSWORD);
    }
}
