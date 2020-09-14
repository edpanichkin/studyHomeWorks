import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import skillboxsql.*;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        try(SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
            Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();



            Student student = new Student();
            Teacher teacher = new Teacher();
            Course course = new Course();


            student.setName("Юнец Сорванец");
            student.setAge(20);
            student.setRegistrationDate(new Date());
            teacher.setName("Ученый Заученый");
            teacher.setAge(35);
            teacher.setSalary(50000);
            session.save(student);
            session.save(teacher);

            course.setName("Тестовый курс");
            course.setType(CourseType.BUSINESS);
            course.setDescription("Проверка маппингов в задании 10.5.3");
            course.setTeacher(teacher);
            session.save(course);

            Subscription subscription = new Subscription(
                    new Subscription.IdSubscription(student, course), new Date());
            session.save(subscription);

//            String hql = "from Course order by studentCount";
//            List<Course> courses = session.createQuery(hql).getResultList();
//            for (Course course : courses) {
//                System.out.println(course.getName() + " // - Кол-во: " + course.getStudentCount());
//                List<Student> studentList = course.getStudents();
//                System.out.println(">> Данные из базы о студентах на курсе:");
//                studentList.forEach(s -> System.out.println(">>>> " + s.getName()));
//            }
            transaction.commit();
        }
    }
}
