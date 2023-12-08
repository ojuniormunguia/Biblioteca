package AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Singleton instance
    private static DatabaseConnection instance;

    // Database connection properties
    private final String url = "jdbc:mysql://158.69.48.30:55201/colegio_amigos_de_don_bosco?useUnicode=true&characterEncoding=UTF-8";
    private final String username = "poo23";
    private final String password = "87MmfPc14q7ZChaqrK8X";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver.", e);
        }
    }

    // Singleton instance creation
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Get a database connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://158.69.48.30:55201/colegio_amigos_de_don_bosco?useUnicode=true&characterEncoding=UTF-8";
            String username = "poo23";
            String password = "87MmfPc14q7ZChaqrK8X";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            // Log the exception using a logging framework
            throw new RuntimeException("Failed to establish database connection.", e);
        }
    }

    // Close the connection and statement
    public void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Log the exception using a logging framework
            throw new RuntimeException("Failed to close database resources.", e);
        }
    }
}
