import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import skillboxsql.*;

import java.util.*;

public class Main {
  public static void main(String[] args) {

    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
         Session session = sessionFactory.openSession()) {
      //List listTables = session.createSQLQuery("show tables from skillbox").list();
      fillLinkedPurchaseList(session);
    }
  }

  public static void fillLinkedPurchaseList(Session session) {
    session.beginTransaction();

    List<Object[]> pList = session.createNativeQuery(
            "SELECT student_name, course_name FROM purchaselist").getResultList();
    List<Object[]> sList = session.createNativeQuery(
            "SELECT id, name FROM students").getResultList();
    List<Object[]> cList = session.createNativeQuery(
            "SELECT id, name FROM courses").getResultList();

    Map<Integer, String> studentsMap = new HashMap<>();
    Map<Integer, String> coursesMap = new HashMap<>();

    for (Object[] student : sList) {
      studentsMap.put((Integer) student[0], (String) student[1]);
    }
    for (Object[] course : cList) {
      coursesMap.put((Integer) course[0], (String) course[1]);
    }
    for (Object[] pow : pList) {
      int studentIndex = indexReturn(studentsMap, (String) pow[0]);
      int courseIndex = indexReturn(coursesMap, (String) pow[1]);
      LinkedPurchaseList linkedPL = new LinkedPurchaseList(studentIndex, courseIndex);
      session.saveOrUpdate(linkedPL);
    }
    session.getTransaction().commit();
  }

  public static int indexReturn(Map<Integer, String> map, String searchIndex) {
    Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
    for (Map.Entry<Integer, String> pair : entrySet) {
      if (searchIndex.equals(pair.getValue())) {
        return pair.getKey();
      }
    }
    return 0;
  }
}
