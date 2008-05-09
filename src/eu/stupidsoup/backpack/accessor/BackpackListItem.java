package eu.stupidsoup.backpack.accessor;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity @Table(name="item")
public class BackpackListItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long itemId;
	private Boolean completed;
	@Lob
	private String text;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinColumn(nullable=false)
	private BackpackList list;

	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long id) {
		this.itemId = id;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	
	public BackpackList getList() {
		return list;
	}

	public void setList(BackpackList list) {
		this.list = list;
	}
}
