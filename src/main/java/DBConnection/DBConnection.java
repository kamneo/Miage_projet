package DBConnection;

import config.OurProperties;

import java.sql.*;
import java.util.Properties;

class DBConnection {
    private static DBConnection instance = null;
    private Connection connection = null;

    private DBConnection(){
        try {
            OurProperties properties = OurProperties.getInstance();
            String DBurl = properties.getDBUrl();
            String unicode="?useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            Properties props = new Properties();
            props.setProperty("user",properties.getDBUser());
            props.setProperty("password",properties.getDBPassword());
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBurl + unicode, props);
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        if(instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeDBConnection() throws SQLException {
        connection.close();
        instance = null;
    }
}
