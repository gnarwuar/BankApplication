package Database;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseHandler {

    protected String dbHost = "127.0.0.1";
    protected String dbPort = "3306";
    protected String dbUser = "root";
    protected String dbPass = "30025020";
    protected String dbName = "empbank";

    Connection connection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        connection = DriverManager.getConnection(url, dbUser, dbPass);

        return connection;
    }

}
