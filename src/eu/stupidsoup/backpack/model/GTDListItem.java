package eu.stupidsoup.backpack.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public interface GTDListItem extends Serializable {
	public Long getId();
	public String getName();
	public Date getDueDate();
	
	public GTDList getGTDList();
	public Set<GTDTag> getGTDTags();
}
