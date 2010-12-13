package eu.stupidsoup.backpack.model.impl;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.stupidsoup.backpack.model.GTDList;
import eu.stupidsoup.backpack.model.GTDListItem;

@Entity
@Table(name="GTD_LIST")
public class BasicGTDList implements GTDList {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Set<BasicGTDListItem> gtdListItems;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}
	
	@Column(nullable=false, unique=true)
	@Override
	public String getName() {
		return name;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Override
	public Set<GTDListItem> getGTDItems() {
		Set<GTDListItem> result = new TreeSet<GTDListItem>();
		for(BasicGTDListItem listItem: gtdListItems) {
			result.add(listItem);
		}
		return result;
	}

}
