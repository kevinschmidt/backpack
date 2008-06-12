package eu.stupidsoup.backpack.accessor;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CollectionOfElements;



@Entity @Table(name="gtd")
public class BackpackGTD {
	@Id @GeneratedValue
	@SuppressWarnings("unused")
	private Long gtdId;
	private String pageName;
	@CollectionOfElements
	private Set<String> pageTags;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="next_list_listid")
	private BackpackList nextList;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="waiting_list_listid")
	private BackpackList waitingList;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="later_list_listid")
	private BackpackList laterList;
	
	
	public BackpackGTD() {
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
