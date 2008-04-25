package eu.stupidsoup.backpack.accessor;

public class BackpackListItem {
	Long itemId;
	Boolean completed;
	String text;
	
	
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

}
