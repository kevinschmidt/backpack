package eu.stupidsoup.backpack.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public interface GTDListItem extends Serializable {
	public Long getId();
	public void setId(Long id);
	public String getName();
	public void setName(String name);
	public Date getDueDate();
	public void setDueDate(Date dueDate);
	
	public GTDList getGTDList();
	public void setGTDList(GTDList list);
	public Set<GTDTag> getGTDTags();
	public void setGTDTags(Set<GTDTag> tags);
}
