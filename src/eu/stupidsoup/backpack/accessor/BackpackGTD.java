package eu.stupidsoup.backpack.accessor;

import java.util.ArrayList;
import java.util.List;

public class BackpackGTD {
	private String pageName;
	private List<String> pageTags;
	private BackpackList nextList;
	private BackpackList waitingList;
	private BackpackList laterList;
	
	
	public BackpackGTD() {
		pageTags = new ArrayList<String>();
	}
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public List<String> getPageTags() {
		return pageTags;
	}
	public void setPageTags(List<String> pageTags) {
		this.pageTags = pageTags;
	}
	public void addPageTag(String pageTag) {
		this.pageTags.add(pageTag);
	}
	public boolean hasTag(String pageTag) {
		return this.pageTags.contains(pageTag);
	}
	
	public BackpackList getNextList() {
		return nextList;
	}
	public void setNextList(BackpackList nextList) {
		this.nextList = nextList;
	}
	public BackpackList getWaitingList() {
		return waitingList;
	}
	public void setWaitingList(BackpackList waitingList) {
		this.waitingList = waitingList;
	}
	public BackpackList getLaterList() {
		return laterList;
	}
	public void setLaterList(BackpackList laterList) {
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
