package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoSQL {
    /**
     * @param  SQLCommand    the command to execute
     * @param  url           the url to connect to the database (protocol//[hosts][/database][?properties])
     * @param  username      the username that you want to use to connect
     * @param  password      the password of the user
     * @throws SQLException  when there is an error while connection
     * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">docs of JDBC</a>
     */
    DemoSQL (String SQLCommand, String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
    }
}
