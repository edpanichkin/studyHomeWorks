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
            List<PurchaseList> pList = session.createQuery("from skillboxsql.PurchaseList").getResultList();

            for(PurchaseList pl : pList){
                session.beginTransaction();
                LinkedPurchaseList stId = new LinkedPurchaseList(pl.getPlId().getStudent().getId(),
                        pl.getPlId().getCourse().getId());
                session.save(stId);
                session.getTransaction().commit();
            }


        }
    }
}
