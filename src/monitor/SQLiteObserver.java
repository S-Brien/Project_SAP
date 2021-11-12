package monitor;

import Model.Logger;
import persistence.DBMySQLite;

import java.sql.Connection;

public class SQLiteObserver extends Observer {

    public SQLiteObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update(Logger log) {
        System.out.println("SQLite "+log.toString());
        DBMySQLite.getInstance();
        DBMySQLite.insert(log);

    }
}
