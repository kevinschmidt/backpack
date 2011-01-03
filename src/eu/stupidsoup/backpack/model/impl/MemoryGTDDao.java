package eu.stupidsoup.backpack.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("memoryDao")
public class MemoryGTDDao extends BasicGTDDao {
	private Set<BasicGTDList> gtdLists;
	
	public MemoryGTDDao() {
		gtdLists = new HashSet<BasicGTDList>();
		
		BasicGTDList personal = new BasicGTDList();
		personal.setId( Long.valueOf(1) );
		personal.setName("Personal");
		gtdLists.add(personal);
		
		BasicGTDList development = new BasicGTDList();
		development.setId( Long.valueOf(2) );
		development.setName("Development");
		gtdLists.add(development);
	}
	
	@Override
	protected BasicGTDList getSessionGTDList(Long id) {
		for (BasicGTDList gtdList: gtdLists) {
			if (gtdList.getId().equals(id)) {
				return gtdList;
			}
		}
		return null;
	}

	@Override
	protected BasicGTDList getSessionGTDListByName(String name) {
		for (BasicGTDList gtdList: gtdLists) {
			if (gtdList.getName().equals(name)) {
				return gtdList;
			}
		}
		return null;
	}

}
