package eu.stupidsoup.backpack.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

import eu.stupidsoup.backpack.client.BackpackViewService;
import eu.stupidsoup.backpack.client.model.BackpackClientGTD;
import eu.stupidsoup.backpack.model.BackpackGTD;


public class BackpackViewServiceController implements BackpackViewService {
	private static final long serialVersionUID = 1L;
	private BackpackManager listManager;
	
	public List<BackpackClientGTD> getGTDListsByTag(String tag) {
		List<BackpackClientGTD> result = new ArrayList<BackpackClientGTD>();
		MapperIF mapper = new DozerBeanMapper();
		
		for (BackpackGTD gtd: this.listManager.getGTDListsByTag(tag).values()) {
			result.add( (BackpackClientGTD) mapper.map(gtd, BackpackClientGTD.class) );
		}
		return result;
	}
	
	public Set<String> getAllGTDTags() {
		return this.listManager.getAllGTDTags();
	}
	
	public void refresh() {
		this.listManager.refresh();
	}
	
	
	public void setBackpackManager(BackpackManager manager) {
		this.listManager = manager;
	}                                                      
}
