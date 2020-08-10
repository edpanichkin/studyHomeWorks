import java.sql.*;

public class Main {
    public static void main(String[] args){
        try {
            String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "testtest";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT course_name, " +
                    "COUNT(subscription_date)/" +
                    "(MAX(MONTH(subscription_date)) - MIN(MONTH(subscription_date)) + 1) as avgMonthSales " +
                    "FROM purchaselist " +
                    "WHERE YEAR(subscription_date) = 2018 " +
                    "GROUP BY course_name;");
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String avgMonthSales = resultSet.getString("avgMonthSales");
                System.out.println(courseName + " | Среднее кол-во покупок в месяц: " + avgMonthSales);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
