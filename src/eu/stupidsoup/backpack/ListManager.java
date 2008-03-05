package eu.stupidsoup.backpack;

import java.util.Map;
import java.util.TreeMap;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.accessor.BackpackList;

public class ListManager {
	private Accessor accessor;

	
	public void setBackpackAccessor(Accessor accessor) {
		this.accessor = accessor;
	}
	
	
	public Map<String, BackpackList> getAllNextLists() {
		Map<String, BackpackList> result = new TreeMap<String, BackpackList>();
		
		Map<Integer, String> pageList = accessor.getPageList();

		for (Integer pageId: pageList.keySet()) {
			BackpackList nextList = accessor.getListByName(pageId, "Next");
			if ( nextList != null ) {
				result.put(pageList.get(pageId), nextList);
			}
		}
		
		return result;
	}


	public Map<String, String> getAllNextListsAsString() {
		Map<String, String> result = new TreeMap<String, String>();
		
		Map<Integer, String> pageList = accessor.getPageList();

		for (Integer pageId: pageList.keySet()) {
			BackpackList nextList = accessor.getListByName(pageId, "Next");
			if ( nextList != null ) {
				result.put(pageList.get(pageId), nextList.getItemsAsString());
			}
		}
		
		return result;
	}
	
}
