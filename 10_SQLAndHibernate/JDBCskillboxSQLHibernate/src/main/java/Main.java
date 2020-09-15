import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
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
            fillLinkedPurchaseList(session);
        }
    }

    public static void fillLinkedPurchaseList(Session session){
        List<Object[]> pList = session.createNativeQuery(
                "SELECT student_name, course_name FROM purchaselist").getResultList();
        for (Object[] pow: pList){
            session.beginTransaction();
            int sId = Integer.parseInt(session.createQuery("SELECT S.id FROM Student S WHERE name = :sname").
                    setParameter("sname", pow[0])
                    .getResultList().toString()
                    .replace("[","").replace("]",""));

            int cId = Integer.parseInt(session.createQuery("SELECT C.id FROM Course C WHERE name = :cname")
                    .setParameter("cname", pow[1])
                    .getResultList().toString()
                    .replace("[","").replace("]",""));
            LinkedPurchaseList linkedPL = new LinkedPurchaseList(sId, cId);
            session.save(linkedPL);
            session.getTransaction().commit();
        }
    }
}
