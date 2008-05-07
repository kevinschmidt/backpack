package eu.stupidsoup.backpack.accessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity @Table(name="list")
public class BackpackList implements Iterable<BackpackListItem> {
	private Long listId;
	private String name;
	
	private Set<BackpackListItem> items;

	
	public BackpackList() {
		this.items = new HashSet<BackpackListItem>();
	}
	
	
	@Id
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

	@OneToMany(mappedBy="list")
	public Set<BackpackListItem> getItemList() {
		return items;
	}

	@Transient
	public void setItemList(Set<BackpackListItem> items) {
		this.items = items;
	}

	public Iterator<BackpackListItem> iterator() {
		return this.items.iterator();
	}
	
	@Transient
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	
	
	@Transient
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
	
	@Transient
	public List<String> getItemsAsStringList() {
		List<String> result = new ArrayList<String>();
		
		for (BackpackListItem item : this.items) {
			result.add(item.getText());
		}
		
		return result;
	}
}
