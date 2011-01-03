package eu.stupidsoup.backpack.model.impl;

import java.util.HashSet;
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

import eu.stupidsoup.backpack.bean.GTDListBean;
import eu.stupidsoup.backpack.model.GTDList;
import eu.stupidsoup.backpack.model.GTDListItem;

@Entity
@Table(name="GTD_LIST")
public class BasicGTDList implements GTDList {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Set<BasicGTDListItem> gtdListItems;
	
	public BasicGTDList() {
		this.gtdListItems = new HashSet<BasicGTDListItem>();
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, unique=true)
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
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
	@Override
	public void setGTDItems(Set<GTDListItem> items) {
		this.gtdListItems.clear();
		for (GTDListItem item: items) {
			if (item instanceof BasicGTDListItem) {
				this.gtdListItems.add( (BasicGTDListItem) item );
			} else {
				// TODO: add populate
				throw new UnsupportedOperationException();
			}
		}
	}


	@Override
	public GTDListBean createBean() {
		GTDListBean bean = new GTDListBean();
		bean.setId( this.getId() );
		bean.setName( this.getName() );
		return bean;
	}

}
