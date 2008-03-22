package eu.stupidsoup.backpack.accessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackpackList implements Iterable<BackpackListItem> {
	private Long id;
	private String name;
	
	private List<BackpackListItem> items;

	
	public BackpackList() {
		this.items = new ArrayList<BackpackListItem>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BackpackListItem> getItemList() {
		return items;
	}

	public void setItemList(List<BackpackListItem> items) {
		this.items = items;
	}

	public Iterator<BackpackListItem> iterator() {
		return this.items.iterator();
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	
	
	
	public String getItemsAsString() {
		StringBuffer result = new StringBuffer();
		
		for (Iterator<BackpackListItem> i = this.items.iterator(); i.hasNext(); ) {
			result.append(i.next().getText());
			if (i.hasNext()) {
				result.append(",");
			}
		}
		
		return result.toString();
	}
	
	public List<String> getItemsAsStringList() {
		List<String> result = new ArrayList<String>();
		
		for (BackpackListItem item : this.items) {
			result.add(item.getText());
		}
		
		return result;
	}
}
