import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import skillboxsql.Course;
import skillboxsql.Student;

import java.util.List;

public class Main {
    public static void main(String[] args){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        try(SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
            Session session = sessionFactory.openSession())
        {

            String hql = "from Course order by studentCount";
            List<Course> courses = session.createQuery(hql).getResultList();
            for (Course course : courses) {
                System.out.println(course.getName() + " // - Кол-во: " + course.getStudentCount());
                List<Student> studentList = course.getStudents();
                System.out.println(">> Данные из базы о студентах на курсе:");
                studentList.forEach(s -> System.out.println(">>>> " + s.getName()));
            }
        }
    }
}
