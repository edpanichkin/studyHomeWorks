import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DBConnection {
  private static Connection connection;

  private static String dbName = "jdbc:mysql://localhost:3306/vote_analyzer?useSSL=false&serverTimezone=UTC";
  private static String dbUser = "user";
  private static String dbPass = "useruser";
  private static StringBuilder insertQuery = new StringBuilder();

  public static Connection getConnection() {

    if (connection == null) {
      try {
        connection = DriverManager.getConnection(dbName, dbUser, dbPass);
        connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
//
        connection.createStatement().execute("CREATE TABLE voter_count(" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT NOT NULL, " +
                "birthDate DATE NOT NULL, " +
                "`count` INT NOT NULL, " +
                "PRIMARY KEY(id))");

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void executeMultiInsert() throws SQLException {
    String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
            "VALUES" + insertQuery.toString();
    DBConnection.getConnection().createStatement().execute(sql);
  }

  public static void countVoter(String name, String birthDay, int count) throws SQLException {
    insertQuery.append((insertQuery.length() == 0 ? "" : ",") +
            "('" + name + "', '" + birthDay + "', " + count + ")");
    if (insertQuery.length() > 3_000_000) {
      executeMultiInsert();
      insertQuery = new StringBuilder();
    }
  }

  public static void printVoterCounts() throws SQLException {
    //DBConnection.executeMultiInsert();
    String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
    ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
    while (rs.next()) {
      System.out.println("\t" + rs.getString("name") + " (" +
              rs.getString("birthDate") + ") - " + rs.getInt("count"));
    }
    rs.close();
//    sql = "SELECT count(*) as count FROM voter_count";
//    rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//    while (rs.next()) {
//      System.out.println("\tSUM OF ROWS:" + rs.getString("count"));
//    }
//    rs.close();
  }
}
