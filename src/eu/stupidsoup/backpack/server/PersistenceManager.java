package eu.stupidsoup.backpack.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eu.stupidsoup.backpack.model.BackpackGTD;
import eu.stupidsoup.backpack.model.BackpackList;
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

	
	
	public Set<String> getAllGTDTags() {
		Set<String> result = new HashSet<String>();
		
		List<BackpackGTD> gtdList = this.model.getAllBackpackGTD();
		for (BackpackGTD gtd: gtdList) {
			result.addAll( gtd.getPageTags() );
		}
		
		return result;
	}
	
	
	public BackpackGTD getGTDbyPage(String pageName) {
		return null;
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
		this.refreshGTD();
	}
	
	public void refreshGTD() {
		Set<String> pageList = this.model.getGTDPageList();
		for (String pageName: pageList) {
			System.out.println("Refreshing page " + pageName);
			BackpackGTD gtd = this.directManager.getGTDbyPage(pageName);
			this.model.saveBackpackGTD(gtd);
		}
	}

	
	
	public void sync() {
		this.syncGTD();
	}
	
	public void syncGTD() {
		this.model.clearGTD();
		Collection<BackpackGTD> gtdList = this.directManager.getGTDLists().values();
		for (BackpackGTD gtd: gtdList) {
			System.out.println("Adding page " + gtd.getPageName());
			this.model.saveBackpackGTD(gtd);
		}
	}
}
