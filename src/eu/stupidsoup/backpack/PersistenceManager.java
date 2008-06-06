package eu.stupidsoup.backpack;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import eu.stupidsoup.backpack.accessor.BackpackGTD;
import eu.stupidsoup.backpack.accessor.BackpackList;
import eu.stupidsoup.backpack.model.BackpackModel;


public class PersistenceManager implements BackpackManager {
	private BackpackModel model;
	private BackpackManager directManager;
	
	public void setBackpackModel(BackpackModel model) {
		this.model = model;
	}
	
	public void setBackpackManager(BackpackManager manager) {
		this.directManager = manager;
	}
	
	
	public List<String> getListByName(String listName, String pageName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Map<String, BackpackList> getAllListsByName(String listName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Map<String, String> getAllListsByNameAsString(String listName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Map<String, List<String>> getAllListsByNameAsStringList(
			String listName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public Map<String, BackpackGTD> getGTDLists() {
		List<BackpackGTD> gtdList = this.model.getAllBackpackGTD();
		Map<String, BackpackGTD> result;
		if (gtdList.isEmpty()) {
			result = this.directManager.getGTDLists();
			this.model.putAllBackpackGTD(result.values());
		} else {
			result = new TreeMap<String, BackpackGTD>();
			for(BackpackGTD gtd: gtdList) {
				result.put(gtd.getPageName(), gtd);
			}
		}
		return result;
	}


	public Map<String, BackpackGTD> getGTDListsByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}



	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
