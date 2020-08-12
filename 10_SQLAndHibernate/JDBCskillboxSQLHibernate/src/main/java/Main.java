import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Course order by studentCount";
        List<Course> courses = session.createQuery(hql).getResultList();
        for (Course course : courses) {
            System.out.println(course.getName() + " // - Кол-во: " + course.getStudentCount());
            List<Student> studentList = course.getStudents();
            System.out.println(">> Данные из базы о студентах на курсе:");
            studentList.forEach(s-> System.out.println(">>>> " + s.getName()));
        }
        transaction.commit();
        sessionFactory.close();
//        try {
//            String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
//            String user = "root";
//            String password = "testtest";
//            Connection connection = DriverManager.getConnection(url, user, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT course_name, " +
//                    "COUNT(subscription_date)/" +
//                    "(MAX(MONTH(subscription_date)) - MIN(MONTH(subscription_date)) + 1) as avgMonthSales " +
//                    "FROM purchaselist " +
//                    "WHERE YEAR(subscription_date) = 2018 " +
//                    "GROUP BY course_name;");
//            while (resultSet.next()) {
//                String courseName = resultSet.getString("course_name");
//                String avgMonthSales = resultSet.getString("avgMonthSales");
//                System.out.println(courseName + " | Среднее кол-во покупок в месяц: " + avgMonthSales);
//            }
//            resultSet.close();
//            statement.close();
//            connection.close();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
    }
}
