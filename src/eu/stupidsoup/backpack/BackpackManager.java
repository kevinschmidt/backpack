package eu.stupidsoup.backpack;

import java.util.List;
import java.util.Map;

import eu.stupidsoup.backpack.accessor.BackpackGTD;
import eu.stupidsoup.backpack.accessor.BackpackList;

public interface BackpackManager {
	
	public Map<String, BackpackGTD> getGTDLists();
	public Map<String, BackpackGTD> getGTDListsByTag(String tagName);
	
	public List<String> getListByName(String listName, String pageName);
	public Map<String, BackpackList> getAllListsByName(String listName);
	public Map<String, String> getAllListsByNameAsString(String listName);
	public Map<String, List<String>> getAllListsByNameAsStringList(String listName);
	
	public void refresh();
}
