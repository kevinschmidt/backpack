package eu.stupidsoup.backpack.model;

import java.io.Serializable;
import java.util.Set;

import eu.stupidsoup.backpack.bean.GTDListBean;

public interface GTDList extends Serializable {
	public Long getId();
	public void setId(Long id);
	public String getName();
	public void setName(String name);
	public Set<GTDListItem> getGTDItems();
	public void setGTDItems(Set<GTDListItem> items);
	
	public GTDListBean createBean();
}
