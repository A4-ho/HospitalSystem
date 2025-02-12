package src.db;

import src.db.interfaces.IDB;
import java.sql.*;

public class DatabaseConnection implements IDB,AutoCloseable {
    private String host;
    private String username;
    private String password;
    private String database;
    private int port;
    private Connection connection;

    public DatabaseConnection(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = Integer.parseInt(port);
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public DatabaseConnection(int i, String postgres, String admin) {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() { return  port; }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public Connection getConnection() {
        // Construct the URL correctly without redundancy
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database; // Ensure the correct URL format
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return null;
    }


    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
