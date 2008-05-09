package eu.stupidsoup.backpack.accessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;



@Entity @Table(name="list")
public class BackpackList implements Iterable<BackpackListItem>, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long listId;
	private String name;
	@OneToMany(mappedBy="list", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Sort(type = SortType.NATURAL)
	private SortedSet<BackpackListItem> items;

	
	public BackpackList() {
		this.items = new TreeSet<BackpackListItem>();
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

	public SortedSet<BackpackListItem> getItemList() {
		return items;
	}

	public void setItemList(SortedSet<BackpackListItem> items) {
		this.items = items;
	}

	
	public Iterator<BackpackListItem> iterator() {
		return this.items.iterator();
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	
	public void addItem(BackpackListItem item) {
		this.items.add(item);
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
