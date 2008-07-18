package eu.stupidsoup.backpack.server;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.accessor.AxiomAccessor;
import eu.stupidsoup.backpack.client.model.BackpackClientGTD;
import eu.stupidsoup.backpack.model.BackpackGTD;
import eu.stupidsoup.backpack.model.HibernateUtil;



public class TestBackpack {

	public static void main(String[] args) {
		Accessor accessor = new AxiomAccessor();
		accessor.setCredentials("https://soup.backpackit.com/", "8e549ba5fe851d32ac8ab12d143c19ab723b5002");
		ListManager man = new ListManager();
		man.setBackpackAccessor(accessor);

//		Session session = HibernateUtil.getSession();
//		Transaction trans = session.beginTransaction();
//		
//		//BackpackList test = accessor.getListByName("Inbox", "New");
//		//System.out.println(test.getItemsAsStringList());
//		//test.getItemList().first().setList(test);
//		//BackpackGTD gtd = accessor.getGTDByName("Personal");
//		
//		//session.save(gtd);
//		List results = session.createCriteria(BackpackGTD.class)
//	    	.setProjection( Property.forName("pageName").group() )
//	    	.addOrder( Order.asc("pageName") )
//	    	.list();
//		System.out.println(results);
//		trans.commit();
//		session.close();
		
		MapperIF mapper = new DozerBeanMapper();
		
		List<BackpackClientGTD> conv = new ArrayList<BackpackClientGTD>();
		List<BackpackGTD> result = new ArrayList<BackpackGTD>( man.getGTDListsByTag("private").values() );
		for (BackpackGTD gtd: result) {
			conv.add( (BackpackClientGTD) mapper.map(gtd, BackpackClientGTD.class) );
		}
		
		for (BackpackClientGTD gtd: conv) {
			System.out.println(gtd.getPageName());
		}
	}

}
