package eu.stupidsoup.backpack.accessor;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity @Table(name="item")
public class BackpackListItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private Boolean completed;
	private String text;
	private BackpackList list;

	
	@Id
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

	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(nullable=false)
	@SuppressWarnings("unused")
	private BackpackList getList() {
		return list;
	}

	@SuppressWarnings("unused")
	private void setList(BackpackList list) {
		this.list = list;
	}
}
