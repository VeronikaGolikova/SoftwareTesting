package ru.stqa.pft.mantis.appmanager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

public class DbHelper {

    private ApplicationManager app;
    private SessionFactory sessionFactory;


    public DbHelper(ApplicationManager app) {
        this.app = app;
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    public Users users() {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<UserData> result = session.createQuery( "from UserData" ).list();
            session.getTransaction().commit();
            session.close();
            return new Users(result);
        }


}
