package eu.stupidsoup.backpack.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.stupidsoup.backpack.client.model.BackpackClientGTD;
import eu.stupidsoup.backpack.client.model.BackpackClientList;
import eu.stupidsoup.backpack.client.model.BackpackClientListItem;


public class BackpackView implements EntryPoint, ClickListener, TabListener {
	private BackpackViewServiceAsync backpackAsync;
	
	private FlexTable gtdTable = new FlexTable();
	private Map<String,Boolean> gtdExpandedList = new HashMap<String,Boolean>();
	
	private Button expand = new Button("Expand");
	private Button collapse = new Button("Collapse");
	private Button refresh = new Button("Refresh");
	private Button sync = new Button("Sync");
	
	private DialogBox popup = new DialogBox();
	private Image loadingImage = new Image("/backpack/images/ajax-loader.gif");
	private Image refreshLoadingImage = new Image("/backpack/images/refresh-loader.gif");
	private TreeImages backpackItemTreeImages = (TreeImages) GWT.create(BackpackItemTreeImages.class);
	
	
	public void onModuleLoad() {
		backpackAsync = (BackpackViewServiceAsync) GWT.create(BackpackViewService.class);
		
		HorizontalSplitPanel superDivider = new HorizontalSplitPanel();
		RootPanel.get().add(superDivider);
		
		
		TabPanel mainTabPanel = new TabPanel();
		mainTabPanel.setWidth("100%");
		mainTabPanel.setHeight("100%");
		
		HorizontalPanel gtdPanel = new HorizontalPanel();
		gtdPanel.add(gtdTable);
		mainTabPanel.add(gtdPanel, "GTD");
		mainTabPanel.add(new Label("test"), "Inbox");
		mainTabPanel.selectTab(0);
		mainTabPanel.addTabListener(this);
		
		StackPanel commandPanel = new StackPanel();
		commandPanel.setWidth("100%");
		VerticalPanel controlPanel = new VerticalPanel();
		controlPanel.add(expand);
		controlPanel.add(collapse);
		controlPanel.add(refresh);
		controlPanel.add(sync);
		
		expand.addClickListener(this);
		collapse.addClickListener(this);
		refresh.addClickListener(this);
		sync.addClickListener(this);
		
		commandPanel.add(controlPanel, "Control");
		commandPanel.add(new Label("test"), "Test");
		
		superDivider.setHeight("500px");
		superDivider.setSplitPosition("90%");
		superDivider.setLeftWidget(mainTabPanel);
		superDivider.setRightWidget(commandPanel);
		
		
		gtdTable.setWidth("100%");
		gtdTable.getColumnFormatter().setWidth(0, "10%");
		gtdTable.getColumnFormatter().setWidth(1, "30%");
		gtdTable.getColumnFormatter().setWidth(2, "30%");
		gtdTable.getColumnFormatter().setWidth(3, "30%");
		
		gtdTable.setText(0, 0, "Category");
		gtdTable.setText(0, 1, "Next List");
		gtdTable.setText(0, 2, "Waiting List");
		gtdTable.setText(0, 3, "Later List");
		
		gtdTable.getRowFormatter().setStyleName(0, "gwt-row-header");
		
		refreshRootCategories();
	}



	private void refreshRootCategories() {
		gtdTable.setWidget(1, 0, loadingImage);
		BackpackCategoryCallback callback = new BackpackCategoryCallback();
		backpackAsync.getAllGTDTags(callback);
	}
	
	
	private void refreshGTD() {
		for (Map.Entry<String, Boolean> entry: gtdExpandedList.entrySet()) {
			if (entry.getValue()) {
				this.updateCategory(entry.getKey());
			}
		}
	}


	private void updateCategory(String category) {
		this.setLoadingForCategory(category);
		BackpackGTDCallback callback = new BackpackGTDCallback(category); 
		backpackAsync.getGTDListsByTag(category, callback);
	}
	
	
	private void updateSubTableForCategory(String category, List<BackpackClientGTD> items) {
		if ( !this.gtdExpandedList.containsKey(category) ) return;
		
		int newRow = this.createRowsAfterCategory(category, items.size());
		int counter = 0;
		for (BackpackClientGTD gtd: items) {
			gtdTable.setText(newRow + counter, 0, gtd.getPageName());
			if (counter % 2 == 0) {
				gtdTable.getRowFormatter().setStyleName(newRow + counter, "gwt-row-odd");
			} else {
				gtdTable.getRowFormatter().setStyleName(newRow + counter, "gwt-row-even");
			}
			if (gtd.getNextList() != null) {
				gtdTable.setWidget(newRow + counter, 1, this.createItemsWidget( gtd.getNextList() ));
			}
			if (gtd.getWaitingList() != null) {
				gtdTable.setWidget(newRow + counter, 2, this.createItemsWidget( gtd.getWaitingList() ));
			}
			if (gtd.getLaterList() != null) {
				gtdTable.setWidget(newRow + counter, 3, this.createItemsWidget( gtd.getLaterList() ));
			}
			counter++;
		}
		this.unsetLoadingForCategory(category);
	}
	
	
	private void updateRootCategories(Set<String> categories) {
		this.clearTable();
		this.clearTable(1, 0, categories.size(), 4, "gwt-row-main");
		
		if (categories.size() == 0) {
			gtdTable.setText(1, 0, "");
			return;
		}
		
		int counter = 0;
		for (String category: new TreeSet<String>(categories)) {
			gtdExpandedList.put(category, false);
			
			Hyperlink tempLink = new Hyperlink(category, category);
			tempLink.addClickListener(this);
			gtdTable.setWidget(counter+1, 0, tempLink);
			counter++;
		}
	}


	private Widget createItemsWidget(BackpackClientList list) {
		Tree tree = new Tree(backpackItemTreeImages, true);
		for (BackpackClientListItem item: list) {
			tree.addItem( item.getText() );
		}
		return tree;
	}
	
	
	private void setLoadingForCategory(String category) {
		if ( !this.gtdExpandedList.containsKey(category) );
		
		gtdTable.setWidget( this.findRowWithCategory(category), 1, loadingImage);
	}
	
	private void unsetLoadingForCategory(String category) {
		if ( !this.gtdExpandedList.containsKey(category) );
		
		gtdTable.setText( this.findRowWithCategory(category), 1, "");
	}
	
	
	private Integer createRowsAfterCategory(String category, int rowAmount) {
		Integer categoryRow = this.findRowWithCategory(category);
		Integer nextCategoryRow = this.findNextCategory(category);
		if (nextCategoryRow == null ) {
			nextCategoryRow  = gtdTable.getRowCount();
		}
			
		for (int i=nextCategoryRow-categoryRow-1; i < rowAmount; i++) {
			gtdTable.insertRow(nextCategoryRow);
		}
		this.gtdExpandedList.put(category, true);
		return categoryRow+1;
	}
	
	private void deleteRowsAfterCategory(String category) {
		Integer categoryRow = this.findRowWithCategory(category);
		Integer nextCategoryRow = this.findNextCategory(category);
		if (nextCategoryRow == null ) {
			nextCategoryRow  = gtdTable.getRowCount();
		}
		
		for (int i=nextCategoryRow-1; i > categoryRow; i--) {
			gtdTable.removeRow(i);
		}
		this.gtdExpandedList.put(category, false);
	}
	
	private Integer findNextCategory(String category) {
		int categoryRow = this.findRowWithCategory(category);
		for (int i = categoryRow+1; i < gtdTable.getRowCount(); i++) {
			Widget widget = gtdTable.getWidget(i, 0);
			if ( widget != null ) {
				if ( widget.getClass() == Hyperlink.class ) {
					Hyperlink tempLink = (Hyperlink) widget;
					if ( this.gtdExpandedList.containsKey( tempLink.getTargetHistoryToken() ) ) {
						return i;
					}
				}
			}
		}
		return null;
	}
	
	private Integer findRowWithCategory(String category) {
		for (int i = 0; i < gtdTable.getRowCount(); i++) {
			Widget widget = gtdTable.getWidget(i, 0);
			if ( widget != null ) {
				if ( widget.getClass() == Hyperlink.class ) {
					Hyperlink tempLink = (Hyperlink) widget;
					if ( tempLink.getTargetHistoryToken().equals(category) ) {
						return i;
					}
				}
			}
		}
		return null;
	}
	
	private void clearTable() {
		int rowCount = gtdTable.getRowCount();
		for (int i=1; i < rowCount; i++) {
			gtdTable.removeRow(1);
		}
	}
	
	private void clearTable(int rowStart, int columnStart, int rowAmount, int columnAmount, String styleName) {
		for (int i=rowStart; i < rowStart+rowAmount; i++) {
			gtdTable.getRowFormatter().setStyleName(i, styleName);
			for (int j=columnStart; j < columnStart+columnAmount; j++) {
				gtdTable.setText(i, j, "");
			}
		}
	}
	
	
	private void displayRefreshPopup(String title) {
		popup.setWidget(refreshLoadingImage);
		popup.setText(title);
		popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				popup.setPopupPosition(left, top);
			}
		});
	}
	
	private void hideRefreshPopup() {
		popup.hide();
	}
	
	
	
	public void onClick(Widget sender) {
		if (sender.getClass() == Hyperlink.class ) {	
			String category = ((Hyperlink) sender ).getTargetHistoryToken();

			if ( this.gtdExpandedList.containsKey(category) ) { 
				if ( !this.gtdExpandedList.get(category) ) {
					this.updateCategory(category);
				} else {
					this.deleteRowsAfterCategory(category);
				}
			}
		}
		
		if (sender.getClass() == Button.class) {
			if (sender == this.expand) {
				for (Map.Entry<String, Boolean> entry: gtdExpandedList.entrySet()) {
					if (!entry.getValue()) {
						this.updateCategory(entry.getKey());
					}
				}
			}
			if (sender == this.collapse) {
				for (Map.Entry<String, Boolean> entry: gtdExpandedList.entrySet()) {
					if (entry.getValue()) {
						this.deleteRowsAfterCategory(entry.getKey());
					}
				}
			}
			if (sender == this.refresh) {
				this.displayRefreshPopup("Refresh in Progress");
				this.backpackAsync.refresh(new AsyncCallback<Object>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught.getMessage());
						}
					
						public void onSuccess(Object result) {
							hideRefreshPopup();
							refreshGTD();
						}
					}
				);
			}
			if (sender == this.sync) {
				this.displayRefreshPopup("Sync in Progress");
				this.backpackAsync.sync(new AsyncCallback<Object>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught.getMessage());
						}
					
						public void onSuccess(Object result) {
							hideRefreshPopup();
							refreshRootCategories();
						}
					}
				);
			}
		}
	}



	public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
		return true;
	}


	public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
		// TODO Auto-generated method stub
	}
	
	
	
	public interface BackpackItemTreeImages extends TreeImages {
		@Resource("folder-small.png")
		AbstractImagePrototype treeOpen();
		
		@Resource("folder-small.png")
		AbstractImagePrototype treeClosed();
		
		@Resource("folder-small.png")
		AbstractImagePrototype treeLeaf();
	}
	
	private class BackpackGTDCallback implements AsyncCallback<List<BackpackClientGTD>> {
		private String category;

		public BackpackGTDCallback(String category) {
			this.category = category;
		}
		
		public void onFailure(Throwable caught) {
			System.out.println(caught.getMessage());
		}
		
		public void onSuccess(List<BackpackClientGTD> result) {
			updateSubTableForCategory(category, result);
		}
	}
	
	private class BackpackCategoryCallback implements AsyncCallback<Set<String>> {	
		public void onFailure(Throwable caught) {
			System.out.println(caught.getMessage());
		}
		
		public void onSuccess(Set<String> result) {
			updateRootCategories(result);
		}
	}

}