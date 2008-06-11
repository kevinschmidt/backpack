package eu.stupidsoup.backpack;

import java.util.List;
import java.util.Map;
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
		return this.directManager.getListByName(listName, pageName);
	}


	public Map<String, BackpackList> getAllListsByName(String listName) {
		return this.directManager.getAllListsByName(listName);
	}


	public Map<String, String> getAllListsByNameAsString(String listName) {
		return this.directManager.getAllListsByNameAsString(listName);
	}


	public Map<String, List<String>> getAllListsByNameAsStringList(String listName) {
		return this.directManager.getAllListsByNameAsStringList(listName);
	}

	
	
	public Map<String, BackpackGTD> getGTDLists() {
		List<BackpackGTD> gtdList = this.model.getAllBackpackGTD();
		Map<String, BackpackGTD> result;
		if (gtdList.isEmpty()) {
			result = this.directManager.getGTDLists();
			this.model.saveBackpackGTD(result.values());
		} else {
			result = new TreeMap<String, BackpackGTD>();
			for(BackpackGTD gtd: gtdList) {
				result.put(gtd.getPageName(), gtd);
			}
		}
		return result;
	}


	public Map<String, BackpackGTD> getGTDListsByTag(String tagName) {
		List<BackpackGTD> gtdList = this.model.getBackpackGTDByTag(tagName);
		Map<String, BackpackGTD> result;
		if (gtdList.isEmpty()) {
			result = this.directManager.getGTDListsByTag(tagName);
			this.model.saveBackpackGTD(result.values());
		} else {
			result = new TreeMap<String, BackpackGTD>();
			for(BackpackGTD gtd: gtdList) {
				result.put(gtd.getPageName(), gtd);
			}
		}
		return result;
	}



	public void refresh() {
		// TODO Auto-generated method stub
	}

}
