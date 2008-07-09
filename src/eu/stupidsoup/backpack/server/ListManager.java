package eu.stupidsoup.backpack.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import eu.stupidsoup.backpack.accessor.Accessor;
import eu.stupidsoup.backpack.model.BackpackGTD;
import eu.stupidsoup.backpack.model.BackpackList;

public class ListManager implements BackpackManager {
	private Accessor accessor;

	
	public void setBackpackAccessor(Accessor accessor) {
		this.accessor = accessor;
	}
	
	
	public List<String> getListByName(String listName, String pageName) {
		return this.accessor.getListByName(pageName, listName).getItemsAsStringList();
	}
	
	
	public Map<String, BackpackList> getAllListsByName(String listName) {
		Map<String, BackpackList> result = new TreeMap<String, BackpackList>();
		
		Map<String, Integer> pageList = accessor.getPageMap();

		for (Map.Entry<String, Integer> pageEntry: pageList.entrySet()) {
			BackpackList nextList = accessor.getListByName(pageEntry.getValue(), listName);
			if ( nextList != null ) {
				result.put(pageEntry.getKey(), nextList);
			}
		}
		
		return result;
	}

	
	public Map<String, String> getAllListsByNameAsString(String listName) {
		Map<String, String> result = new TreeMap<String, String>();
		
		Map<String, Integer> pageList = accessor.getPageMap();

		for (Map.Entry<String, Integer> pageEntry: pageList.entrySet()) {
			BackpackList nextList = accessor.getListByName(pageEntry.getValue(), listName);
			if ( nextList != null ) {
				result.put(pageEntry.getKey(), nextList.getItemsAsString());
			}
		}
		
		return result;
	}
	
	public Map<String, List<String>> getAllListsByNameAsStringList(String listName) {
		Map<String, List<String>> result = new TreeMap<String, List<String>>();
		
		Map<String, Integer> pageList = accessor.getPageMap();

		for (Map.Entry<String, Integer> pageEntry: pageList.entrySet()) {
			BackpackList nextList = accessor.getListByName(pageEntry.getValue(), listName);
			if ( nextList != null && !nextList.isEmpty() ) {
				result.put(pageEntry.getKey(), nextList.getItemsAsStringList());
			}
		}
		
		return result;
	}
	
	
	
	public BackpackGTD getGTDbyPage(String pageName) {
		return accessor.getGTDByName(pageName);
	}
	
	
	public Map<String, BackpackGTD> getGTDLists() {
		SortedMap<String, BackpackGTD> result = new TreeMap<String, BackpackGTD>();
		Set<String> pageList = accessor.getPageNames();
		for(String pageName : pageList) {
			BackpackGTD gtd = accessor.getGTDByName(pageName);
			if (!gtd.isEmpty()) {
				result.put(pageName, gtd);
			}
		}
		return result;
	}


	public Map<String, BackpackGTD> getGTDListsByTag(String tagName) {
		SortedMap<String, BackpackGTD> result = new TreeMap<String, BackpackGTD>();
		Set<String> pageList = accessor.getPageNames();
		for(String pageName : pageList) {
			BackpackGTD gtd = accessor.getGTDByName(pageName);
			if (gtd.hasTag(tagName) && !gtd.isEmpty()) {
				result.put(pageName, gtd);
			}
		}
		return result;
	}


	public void refresh() {
	}
	
}
