package eu.stupidsoup.backpack;

import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.accessor.AxiomAccessor;
import eu.stupidsoup.backpack.accessor.BackpackGTD;
import eu.stupidsoup.backpack.accessor.BackpackList;
import eu.stupidsoup.backpack.accessor.BackpackListItem;
import eu.stupidsoup.backpack.accessor.HibernateUtil;



public class TestBackpack {

	public static void main(String[] args) {
		Accessor accessor = new AxiomAccessor();
		accessor.setCredentials("https://soup.backpackit.com/", "8e549ba5fe851d32ac8ab12d143c19ab723b5002");

		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();
		
		//BackpackList test = accessor.getListByName("Inbox", "New");
		//System.out.println(test.getItemsAsStringList());
		//test.getItemList().first().setList(test);
		BackpackGTD gtd = accessor.getGTDByName("Personal");
		
		session.save(gtd);
		trans.commit();
		session.close();
	}

}
