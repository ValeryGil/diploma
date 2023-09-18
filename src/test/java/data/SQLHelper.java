package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final String url = System.getProperty("datasource.url");
    private static final String user = System.getProperty("datasource.username");
    private static final String pass = System.getProperty("datasource.password");
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection("url", "user", "pass");
    }

    @SneakyThrows
    private static String getCardPayment() {
        var connection = getConnection();
        var codeSQL = "SELECT code FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    private static String getCreditPayment() {
        var connection = getConnection();
        var codeSQL = "SELECT code FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanBase() {
        var connection = getConnection();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }
}
