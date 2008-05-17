package eu.stupidsoup.backpack;

import java.util.List;
import java.util.Map;

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
		// TODO Auto-generated method stub
		return null;
	}


	public Map<String, BackpackGTD> getGTDListsByTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}



	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
