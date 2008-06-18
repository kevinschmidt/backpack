package eu.stupidsoup.backpack.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import eu.stupidsoup.backpack.accessor.BackpackGTD;
import eu.stupidsoup.backpack.accessor.HibernateUtil;


public class BackpackModel {

	public List<BackpackGTD> getAllBackpackGTD() {
		System.out.println("called getAllBackpackGTD");
		
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();

		List<BackpackGTD> result = session.createCriteria(BackpackGTD.class).list();

		trans.commit();
		session.close();
		
		return result;
	}


	public List<BackpackGTD> getBackpackGTDByTag(String tagName) {
		System.out.println("called getBackpackGTDByTag");
		
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();

		List<BackpackGTD> result = session.createQuery("from BackpackGTD as gtd where :tag in elements(gtd.pageTags)")
			.setString("tag", tagName)
			.list();

		trans.commit();
		session.close();
		
		return result;
	}

	
	public void saveBackpackGTD(Collection<BackpackGTD> gtdList) {
		System.out.println("called putAllBackpackGTD");
		
		for (BackpackGTD gtd: gtdList) {
			this.saveBackpackGTD(gtd);
		}
	}
	
	
	public void saveBackpackGTD(BackpackGTD gtd) {
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();
		
		BackpackGTD sessionGTD = (BackpackGTD) session.createCriteria(BackpackGTD.class).add(Restrictions.eq("pageName", gtd.getPageName())).uniqueResult();
		if ( sessionGTD == null ) {
			session.save(gtd);
		} else {
			sessionGTD.clearLists();
			session.flush();
			gtd.setGtdId(sessionGTD.getGtdId());
			session.merge(gtd);
		}
		
		trans.commit();
		session.close();
	}


	public Set<String> getGTDPageList() {
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();
		
		List results = session.createCriteria(BackpackGTD.class)
    		.setProjection( Property.forName("pageName").group() )
    		//.addOrder( Order.asc("pageName") )
    		.list();
		
		trans.commit();
		session.close();
		
		return new HashSet<String>( results );
	}
}
