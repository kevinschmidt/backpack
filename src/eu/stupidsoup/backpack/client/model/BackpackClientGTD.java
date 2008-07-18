package eu.stupidsoup.backpack.client.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class BackpackClientGTD implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long gtdId;
	private String pageName;
	private Set<String> pageTags;
	private BackpackClientList nextList;
	private BackpackClientList waitingList;
	private BackpackClientList laterList;
	
	
	public BackpackClientGTD() {
		pageTags = new HashSet<String>();
	}
	
	public Long getGtdId() {
		return gtdId;
	}
	public void setGtdId(Long gtdId) {
		this.gtdId = gtdId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public Set<String> getPageTags() {
		return pageTags;
	}
	public void setPageTags(Set<String> pageTags) {
		this.pageTags = pageTags;
	}
	public void addPageTag(String pageTag) {
		this.pageTags.add(pageTag);
	}
	public boolean hasTag(String pageTag) {
		return this.pageTags.contains(pageTag);
	}
	
	public BackpackClientList getNextList() {
		return nextList;
	}
	public void setNextList(BackpackClientList nextList) {
		this.nextList = nextList;
	}
	public BackpackClientList getWaitingList() {
		return waitingList;
	}
	public void setWaitingList(BackpackClientList waitingList) {
		this.waitingList = waitingList;
	}
	public BackpackClientList getLaterList() {
		return laterList;
	}
	public void setLaterList(BackpackClientList laterList) {
		this.laterList = laterList;
	}
	
	
	
	public boolean isEmpty() {
		if (nextList == null && waitingList == null && laterList == null) {
			return true;
		}
		return false;
	}
	
	public boolean isPartiallyEmpty() {
		if (nextList == null || waitingList == null || laterList == null) {
			return true;
		}
		return false;
	}
	
	public void makeEmpty() {
		this.nextList = null;
		this.waitingList = null;
		this.laterList = null;
	}
	
	public void clearLists() {
		this.nextList.makeEmpty();
		this.waitingList.makeEmpty();
		this.laterList.makeEmpty();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( (this.nextList != null) ? this.nextList.getItemsAsString() : "" );
		sb.append("\n");
		sb.append( (this.waitingList != null) ? this.waitingList.getItemsAsString() : "" );
		sb.append("\n");
		sb.append( (this.laterList != null) ? this.laterList.getItemsAsString() : "" );
		return sb.toString();
	}
}
