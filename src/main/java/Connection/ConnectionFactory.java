package Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * <p>
 * Class to manage database connections.
 * Provides methods to obtain and close connections, statements, and result sets safely.
 * </p>
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final  String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/shop";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    public ConnectionFactory() {
        try
        {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    /**
     * <p>
     * Creates a new database connection using the configured URL, user, and password.
     * </p>
     *
     * @return a new {@link Connection} object to the database
     */
 private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * <p>
     * Provides a connection from the singleton ConnectionFactory.
     * </p>
     *
     * @return a {@link Connection} object to the database
     */
 public static Connection getConnection() {
        return singleInstance.createConnection();
    }
    /**
     * <p>
     * Closes the given database connection if it is not null.
     * </p>
     *
     * @param connection the {@link Connection} to close
     */
 public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * <p>
     * Closes the given SQL statement if it is not null.
     * </p>
     *
     * @param statement the {@link Statement} to close
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * <p>
     * Closes the given result set if it is not null.
     * </p>
     *
     * @param resultSet the {@link ResultSet} to close
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }


}
