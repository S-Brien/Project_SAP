package persistence;

import Model.Logger;
import monitor.FileObserver;
import monitor.Observer;
import monitor.Subject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBMySQL {
    static Subject monitor = new Subject();
    Observer obs2 = new FileObserver(monitor);

    private static DBMySQL firstInstance = null;
    private Connection con;
    private   String user;
    private   String pwd;
    private static final String db_name = "project";
    private static final String connection = "jdbc:mysql://localhost:3306/"+db_name;

    //prevents users from Instaniation
    private DBMySQL() {
        Properties p = new Properties();

        try(FileInputStream ip = new FileInputStream("config.properties")){
            p.load(ip);
            user = p.getProperty("user");
            pwd = p.getProperty("pwd");

        } catch (FileNotFoundException e) {
            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
        } catch (IOException e) {
            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
        }

    }

    public static DBMySQL getInstance() {
        if (firstInstance == null) {
            firstInstance = new DBMySQL();
        }
        return firstInstance;
    }

    public Connection getConnection() throws SQLException {

        if (con == null || con.isClosed()) {
            try {
                Properties props = new Properties();
                props.put("user", user);
                props.put("password", pwd);
                props.put("useUnicode", "true");
                props.put("useServerPrepStmts", "false"); // use client-side prepared statement
                props.put("characterEncoding", "UTF-8"); // ensure charset is utf8 here

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(connection, props);
            } catch (Exception e) {
                monitor.setLog(new Logger("warn", e.getCause().getMessage()));
            }
        }
        return con;
    }
}