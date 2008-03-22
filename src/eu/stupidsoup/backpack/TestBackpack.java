package eu.stupidsoup.backpack;

import java.util.Map;
import java.util.Set;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.accessor.AxiomAccessor;
import eu.stupidsoup.backpack.accessor.BackpackGTD;
import eu.stupidsoup.backpack.accessor.BackpackList;
import eu.stupidsoup.backpack.accessor.BackpackListItem;



public class TestBackpack {

	public static void main(String[] args) {
		Accessor accessor = new AxiomAccessor();
		accessor.setCredentials("https://soup.backpackit.com/", "8e549ba5fe851d32ac8ab12d143c19ab723b5002");

		Set<String> pageList = accessor.getPageNames();
		System.out.println(pageList);
		for (String pageName: pageList) {
			BackpackList nextList = accessor.getListByName(pageName, "Next");
			if ( nextList != null ) {
				for (BackpackListItem i: nextList) {
					System.out.println(i.getText());
				}
			}
		}
		
		System.out.println(accessor.getListByName("Inbox", "New").getItemsAsStringList());
		
		ListManager lm = new ListManager();
		lm.setBackpackAccessor(accessor);
		Map<String, BackpackGTD> gtd = lm.getGTDLists();
		for (Map.Entry<String, BackpackGTD> entry: gtd.entrySet()) {
			System.out.println("*" + entry.getKey());
			System.out.println(entry.getValue());
		}
	}

}
