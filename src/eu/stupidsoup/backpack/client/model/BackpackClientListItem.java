package eu.stupidsoup.backpack.client.model;

import java.io.Serializable;


public class BackpackClientListItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long itemId;
	private Boolean completed;
	private String text;
	private BackpackClientList list;

	
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

	
	public BackpackClientList getList() {
		return list;
	}

	public void setList(BackpackClientList list) {
		this.list = list;
	}
}
