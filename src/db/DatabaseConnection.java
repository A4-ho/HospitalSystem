package src.db;

import src.db.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements IDB {
    private String host;
    private String username;
    private String password;
    private String database;


    private Connection connection;

    public DatabaseConnection(String host, String username, String password, String database) {
        setHost(host);
        setUsername(username);
        setPassword(password);
        setDatabase(database);

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
    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public Connection getConnection() {
        String url = host+'/'+database;
        try{
            if(connection != null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection(url,username,password);
            return connection;
        }catch(Exception e){
            System.out.println("Failed to connect to database" + e.getMessage());
        }
        return null;

    }

    @Override
    public void close() {
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException e){
                System.out.println("Failed to close database connection" + e.getMessage());
            }
        }
    };
//d

}
