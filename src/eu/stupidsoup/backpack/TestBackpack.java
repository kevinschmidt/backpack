package eu.stupidsoup.backpack;

import java.util.Map;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.accessor.AxiomAccessor;
import eu.stupidsoup.backpack.accessor.BackpackList;
import eu.stupidsoup.backpack.accessor.BackpackListItem;



public class TestBackpack {

	public static void main(String[] args) {
		Accessor accessor = new AxiomAccessor();
		accessor.setCredentials("https://soup.backpackit.com/", "8e549ba5fe851d32ac8ab12d143c19ab723b5002");

		Map<Integer, String> pageList = accessor.getPageList();
		System.out.println(pageList);
		for (Integer pageId: pageList.keySet()) {
			BackpackList nextList = accessor.getListByName(pageId, "Next");
			if ( nextList != null ) {
				for (BackpackListItem i: nextList) {
					System.out.println(i.getText());
				}
			}
		}
	}

}
