package eu.stupidsoup.backpack.model;

import java.io.Serializable;
import java.util.Set;

public interface GTDList extends Serializable {
	public Long getId();
	public String getName();
	public Set<GTDListItem> getGTDItems();
}
