package eu.stupidsoup.backpack.accessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;



@Entity @Table(name="list")
public class BackpackList implements Iterable<BackpackListItem>, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long listId;
	private String name;
	@OneToMany(mappedBy="list", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("text")
	private Set<BackpackListItem> items;

	
	public BackpackList() {
		this.items = new HashSet<BackpackListItem>();
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

	public Set<BackpackListItem> getItemList() {
		return items;
	}

	public void setItemList(Set<BackpackListItem> items) {
		this.items = items;
	}

	
	public Iterator<BackpackListItem> iterator() {
		return this.items.iterator();
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	public void makeEmpty() {
		this.items.clear();
	}
	
	public void addItem(BackpackListItem item) {
		this.items.add(item);
		item.setList(this);
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
