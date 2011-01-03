package eu.stupidsoup.backpack.model.impl;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.stupidsoup.backpack.model.GTDList;
import eu.stupidsoup.backpack.model.GTDListItem;
import eu.stupidsoup.backpack.model.GTDTag;

@Entity
@Table(name="GTD_LISTITEM")
public class BasicGTDListItem implements GTDListItem {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Date dueDate;
	private BasicGTDList gtdList;
	private Set<BasicGTDTag> gtdTags;
	
	
	public BasicGTDListItem() {
		this.gtdTags = new HashSet<BasicGTDTag>();
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

	@Column(nullable=false)
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getDueDate() {
		return dueDate;
	}
	@Override
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@ManyToOne(cascade={CascadeType.ALL} )
	@Override
	public GTDList getGTDList() {
		return gtdList;
	}
	@Override
	public void setGTDList(GTDList list) {
		if (list instanceof BasicGTDList) {
			this.gtdList = (BasicGTDList) list;
		} else {
			// TODO: add populate
			throw new UnsupportedOperationException();
		}	
	}


	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Override
	public Set<GTDTag> getGTDTags() {
		Set<GTDTag> result = new TreeSet<GTDTag>();
		for (BasicGTDTag gtdTag: gtdTags) {
			result.add(gtdTag);
		}
		return result;
	}
	@Override
	public void setGTDTags(Set<GTDTag> tags) {
		this.gtdTags.clear();
		for (GTDTag tag: tags) {
			if (tag instanceof BasicGTDTag) {
				this.gtdTags.add( (BasicGTDTag) tag );
			} else {
				// TODO: add populate
				throw new UnsupportedOperationException();
			}
		}
	}

}
