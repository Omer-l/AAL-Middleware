package dao;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateUtil {
	public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public HibernateUtil(String db) {
    	sessionFactory(db);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /** Closes Hibernate down and stops its threads from running*/
    public void shutDown() {
        sessionFactory.close();
    }
    
    public String configFileName(String db) {
    	switch(db) {
    	case "MySQL": return "";
    	case "PostgreSQL": return "";
    	}
    	return "";
    }
    
    /**
     * Gets the session factory used to connect with the MySQL database
     * @return  the session factory
     */
    @Bean
    public SessionFactory sessionFactory(String db) {
        if(sessionFactory == null) { //ensures sessionFactory is built only once.

            try {
                //Create a builder for the standard service registry
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

                //Load configuration from hibernate configuration file
                switch(db) {
	                case "MySQL" : standardServiceRegistryBuilder.configure("hibernate-mysql.cfg.xml"); break;
	                case "PostgreSQL" : standardServiceRegistryBuilder.configure("hibernate-postgresql.cfg.xml"); break; 
                }
                //Create the registry that will be used to build the session factory
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

                try {
                    //Create the session factory - this is the goal of the init method.
                    sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                }
                catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                    System.err.println("Session Factory build failed.");
                    e.printStackTrace();
                    StandardServiceRegistryBuilder.destroy( registry );
                }

                //Ouput result
                System.out.println("Session factory built.");

            }
            catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("SessionFactory creation failed." + ex);
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
