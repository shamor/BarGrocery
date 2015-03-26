package com.project.sam.bargrocery.backend.DatabasePersistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.net.ssl.SSLEngineResult.Status;

/**
 * Created by sam on 3/16/2015.
 */
public class RealDatabase implements IDatabase {
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (Exception e) {
            throw new IllegalStateException("Could not load sqlite driver");
        }
    }

    private interface Transaction<ResultType> {
        public ResultType execute(Connection conn) throws SQLException;
    }

    private static final int MAX_ATTEMPTS = 10;

    private Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");

        // Set autocommit to false to allow multiple the execution of
        // multiple queries/statements as part of the same transaction.
        conn.setAutoCommit(false);

        return conn;
    }

    public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
        try {
            return doExecuteTransaction(txn);
        } catch (SQLException e) {
            throw new PersistenceException("Transaction failed", e);
        }
    }

    public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
        Connection conn = connect();

        try {
            int numAttempts = 0;
            boolean success = false;
            ResultType result = null;

            while (!success && numAttempts < MAX_ATTEMPTS) {
                try {
                    result = txn.execute(conn);
                    conn.commit();
                    success = true;
                } catch (SQLException e) {
                    if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
                        // Deadlock: retry (unless max retry count has been reached)
                        numAttempts++;
                    } else {
                        // Some other kind of SQLException
                        throw e;
                    }
                }
            }

            if (!success) {
                throw new SQLException("Transaction failed (too many retries)");
            }

            // Success!
            return result;
        } finally {
            DBUtil.closeQuietly(conn);
        }
    }

//TODO:
    public void createItemTables() {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    // Note that the 'id' column is an autoincrement primary key,
                    // so SQLite will automatically assign an id when rows
                    // are inserted.
                    stmt = conn.prepareStatement(
                            "create table users (" +
                                    "  id integer primary key not null generated always as identity," +
                                    "  username varchar(30) unique," +
                                    "  firstname varchar(30) unique," +
                                    "  lastname varchar(30) unique," +
                                    "  password varchar(30) unique," +
                                    "  type boolean not null " +
                                    ")"

                    );

                    stmt.executeUpdate();

                    return true;
                } finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }

    public void loadInitialUserData() {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    stmt = conn.prepareStatement("insert into users (username, firstname, lastname, password, type) values (?, ?, ?, ?, ?)");
                    storeUserNoId(new User("Apples","Apples","Apples","Apples", true), stmt, 1);
                    stmt.addBatch();
                    storeUserNoId(new User("Oranges","Oranges","Oranges","Oranges",false), stmt, 1);
                    stmt.addBatch();
                    storeUserNoId(new User("Pomegranates","Pomegranates","Pomegranates","Pomegranates",true), stmt, 1);
                    stmt.addBatch();

                    stmt.executeBatch();

                    return true;
                } finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }

    protected void storeUserNoId(User user, PreparedStatement stmt, int index) throws SQLException {
        // Note that we are assuming that the Item does not have a valid id,
        // and so are not attempting to store the (invalid) id.
        // This is the preferred approach when inserting a new row into
        // a table in which a unique id is automatically generated.
        stmt.setString(index++, user.getUserName());
        stmt.setString(index++, user.getFirstName());
        stmt.setString(index++, user.getLastName());
        stmt.setString(index++, user.getPassword());
        stmt.setBoolean(index++, user.getType());
    }

    public static void main(String[] args) {
        RealDatabase db = new RealDatabase();
        System.out.println("Creating tables...");
        db.createItemTables();
        System.out.println("Loading initial data...");
        db.loadInitialUserData();
        System.out.println("Done!");
    }

   //implement database functions
}
