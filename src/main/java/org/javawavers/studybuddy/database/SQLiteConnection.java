package org.javawavers.studybuddy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class manages the connection to the SQLite database.
 * It provides methods to connect to and disconnect from the database.
 */
public class SQLiteConnection {

  // Path to the SQLite database file
  private static final String DATABASE_URL =
      "jdbc:sqlite:src/main/resources/org/javawavers/studybuddy/DataBase.db";

  /**
   * Establishes a connection to the SQLite database.
   *
   * @return a {@link Connection} object representing the database connection.
   * @throws SQLException if there is an error establishing the connection.
   */
  public static Connection connect() throws SQLException {
    Connection connection = null;
    try {
      // Load the SQLite JDBC driver
      Class.forName("org.sqlite.JDBC");

      // Establish a connection to the database
      connection = DriverManager.getConnection(DATABASE_URL);
      System.out.println("Connection to SQLite has been established.");
    } catch (ClassNotFoundException e) {
      System.err.println("SQLite JDBC driver not found.");
      e.printStackTrace();
      throw new SQLException("Failed to load SQLite JDBC driver.", e);
    } catch (SQLException e) {
      System.err.println("Failed to connect to the database.");
      e.printStackTrace();
      throw e;
    }
    return connection;
  }

  /**
   * Closes the connection to the SQLite database.
   * This method is called to safely release the database resources.
   *
   * @param connection the {@link Connection} object to be closed.
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Connection to SQLite has been closed.");
      } catch (SQLException e) {
        System.err.println("Failed to close the database connection.");
        e.printStackTrace();
      }
    }
  }

  /**
   * Main method to test the connection to the database.
   * It establishes a connection and then closes it after operations are done.
   *
   * @param args command-line arguments (not used in this example).
   */
  public static void main(String[] args) {
    Connection connection = null;
    try {
      connection = SQLiteConnection.connect();
      // Perform database operations here

    } catch (SQLException e) {
      System.err.println("An error occurred while working with the database.");
    } finally {
      SQLiteConnection.closeConnection(connection);
    }
  }
}
