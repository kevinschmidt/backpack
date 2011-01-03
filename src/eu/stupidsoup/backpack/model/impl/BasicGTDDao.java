package eu.stupidsoup.backpack.model.impl;

import eu.stupidsoup.backpack.model.GTDDao;
import eu.stupidsoup.backpack.model.GTDList;


public abstract class BasicGTDDao implements GTDDao {

	@Override
	public GTDList getGTDList(Long id) {
		return this.getSessionGTDList(id);
	}

	@Override
	public GTDList getGTDListByName(String name) {
		return this.getSessionGTDListByName(name);
	}
	
	protected abstract BasicGTDList getSessionGTDList(Long id);
	protected abstract BasicGTDList getSessionGTDListByName(String name);
}
