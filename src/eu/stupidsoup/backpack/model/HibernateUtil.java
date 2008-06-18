package eu.stupidsoup.backpack.model;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	public static Session getSession() throws HibernateException {
		if (sessionFactory == null) {
			createFactory();
		}
	    return sessionFactory.openSession();
	}

	private static void createFactory() {
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

}