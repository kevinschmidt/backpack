package eu.stupidsoup.backpack.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class BackpackClientList implements Iterable<BackpackClientListItem>, Serializable {
	private static final long serialVersionUID = 1L;

	private Long listId;
	private String name;
	private Set<BackpackClientListItem> items;

	
	public BackpackClientList() {
		this.items = new HashSet<BackpackClientListItem>();
	}
	
	
	public Long getlistId() {
		return listId;
	}

	public void setListId(Long id) {
		this.listId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BackpackClientListItem> getItemList() {
		return items;
	}

	public void setItemList(Set<BackpackClientListItem> items) {
		this.items = items;
	}

	
	public Iterator<BackpackClientListItem> iterator() {
		return this.items.iterator();
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	public void makeEmpty() {
		this.items.clear();
	}
	
	public void addItem(BackpackClientListItem item) {
		this.items.add(item);
		item.setList(this);
	}
	
	
	public String getItemsAsString() {
		StringBuffer result = new StringBuffer();
		
		for (Iterator<BackpackClientListItem> i = this.items.iterator(); i.hasNext(); ) {
			result.append(i.next().getText());
			if (i.hasNext()) {
				result.append(",");
			}
		}
		
		return result.toString();
	}
	
	public List<String> getItemsAsStringList() {
		List<String> result = new ArrayList<String>();
		
		for (BackpackClientListItem item : this.items) {
			result.add(item.getText());
		}
		
		return result;
	}
}
