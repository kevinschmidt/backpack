package eu.stupidsoup.backpack.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.stupidsoup.backpack.client.model.BackpackClientGTD;
import eu.stupidsoup.backpack.client.model.BackpackClientList;
import eu.stupidsoup.backpack.client.model.BackpackClientListItem;


public class BackpackView implements EntryPoint, ClickListener {
	private BackpackViewServiceAsync backpackAsync;
	private FlexTable mainTable = new FlexTable();
	private Map<String,Boolean> expandedList = new HashMap<String,Boolean>();
	private Image loadingImage = new Image("ajax-loader.gif");
	

	public void onModuleLoad() {
		backpackAsync = (BackpackViewServiceAsync) GWT.create(BackpackViewService.class);
	    RootPanel.get().add(mainTable);

		mainTable.setWidth("100%");
		//mainTable.setBorderWidth(1);
		mainTable.getColumnFormatter().setWidth(0, "10%");
		mainTable.getColumnFormatter().setWidth(1, "30%");
		mainTable.getColumnFormatter().setWidth(2, "30%");
		mainTable.getColumnFormatter().setWidth(3, "30%");
		
		mainTable.setText(0, 0, "Category");
		mainTable.setText(0, 1, "Next List");
		mainTable.setText(0, 2, "Waiting List");
		mainTable.setText(0, 3, "Later List");
		
		mainTable.getRowFormatter().setStyleName(0, "gwt-row-header");
		
		mainTable.setWidget(1, 0, loadingImage);
		BackpackCategoryCallback callback = new BackpackCategoryCallback();
		backpackAsync.getAllGTDTags(callback);
	}

	
	public void onClick(Widget sender) {
		if (sender.getClass() == Hyperlink.class ) {	
			String category = ((Hyperlink) sender ).getTargetHistoryToken();

			if ( this.expandedList.containsKey(category) ) { 
				if ( !this.expandedList.get(category) ) {
					this.setLoadingForCategory(category);
					BackpackGTDCallback callback = new BackpackGTDCallback(category); 
					backpackAsync.getGTDListsByTag(category, callback);
				} else {
					this.deleteRowsAfterCategory(category);
				}
			}
		}
	}
	

	
	private void updateSubTableForCategory(String category, List<BackpackClientGTD> items) {
		if ( !this.expandedList.containsKey(category) ) return;
		
		int newRow = this.createRowsAfterCategory(category, items.size());
		int counter = 0;
		for (BackpackClientGTD gtd: items) {
			mainTable.setText(newRow + counter, 0, gtd.getPageName());
			if (counter % 2 == 0) {
				mainTable.getRowFormatter().setStyleName(newRow + counter, "gwt-row-odd");
			} else {
				mainTable.getRowFormatter().setStyleName(newRow + counter, "gwt-row-even");
			}
			if (gtd.getNextList() != null) {
				mainTable.setWidget(newRow + counter, 1, this.createItemsPanel( gtd.getNextList() ));
			}
			if (gtd.getWaitingList() != null) {
				mainTable.setWidget(newRow + counter, 2, this.createItemsPanel( gtd.getWaitingList() ));
			}
			if (gtd.getLaterList() != null) {
				mainTable.setWidget(newRow + counter, 3, this.createItemsPanel( gtd.getLaterList() ));
			}
			counter++;
		}
		this.unsetLoadingForCategory(category);
	}
	
	
	private void updateCategories(Set<String> categories) {
		this.clearTable(1, 0, categories.size(), 4, "gwt-row-main");
		
		int counter = 0;
		for (String category: categories) {
			expandedList.put(category, false);
			
			Hyperlink tempLink = new Hyperlink(category, category);
			tempLink.addClickListener(this);
			mainTable.setWidget(counter+1, 0, tempLink);
			counter++;
		}
	}


	private Widget createItemsPanel(BackpackClientList list) {
		VerticalPanel panel = new VerticalPanel();
		for (BackpackClientListItem item: list) {
			panel.add( new HTML("<li>"+item.getText()+"</li>") );
		}
		return panel;
	}
	
	
	private void setLoadingForCategory(String category) {
		if ( !this.expandedList.containsKey(category) );
		
		mainTable.setWidget( this.findRowWithCategory(category), 1, loadingImage);
		//int newRow = this.createRowsAfterCategory(category, 1);
		//mainTable.setText(newRow, 0, "Loading ...");
	}
	
	private void unsetLoadingForCategory(String category) {
		if ( !this.expandedList.containsKey(category) );
		
		mainTable.setText( this.findRowWithCategory(category), 1, "");
		//int newRow = this.createRowsAfterCategory(category, 1);
		//mainTable.setText(newRow, 0, "Loading ...");
	}
	
	
	private Integer createRowsAfterCategory(String category, int rowAmount) {
		Integer categoryRow = this.findRowWithCategory(category);
		Integer nextCategoryRow = this.findNextCategory(category);
		if (nextCategoryRow == null ) {
			nextCategoryRow  = mainTable.getRowCount();
		}
			
		for (int i=nextCategoryRow-categoryRow-1; i < rowAmount; i++) {
			mainTable.insertRow(nextCategoryRow);
		}
		this.expandedList.put(category, true);
		return categoryRow+1;
	}
	
	private void deleteRowsAfterCategory(String category) {
		Integer categoryRow = this.findRowWithCategory(category);
		Integer nextCategoryRow = this.findNextCategory(category);
		if (nextCategoryRow == null ) {
			nextCategoryRow  = mainTable.getRowCount();
		}
		
		for (int i=nextCategoryRow-1; i > categoryRow; i--) {
			mainTable.removeRow(i);
		}
		this.expandedList.put(category, false);
	}
	
	private Integer findNextCategory(String category) {
		int categoryRow = this.findRowWithCategory(category);
		for (int i = categoryRow+1; i < mainTable.getRowCount(); i++) {
			Widget widget = mainTable.getWidget(i, 0);
			if ( widget != null ) {
				if ( widget.getClass() == Hyperlink.class ) {
					Hyperlink tempLink = (Hyperlink) widget;
					if ( this.expandedList.containsKey( tempLink.getTargetHistoryToken() ) ) {
						return i;
					}
				}
			}
		}
		return null;
	}
	
	private Integer findRowWithCategory(String category) {
		for (int i = 0; i < mainTable.getRowCount(); i++) {
			Widget widget = mainTable.getWidget(i, 0);
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
	
	private void clearTable(int rowStart, int columnStart, int rowAmount, int columnAmount, String styleName) {
		for (int i=rowStart; i < rowStart+rowAmount; i++) {
			mainTable.getRowFormatter().setStyleName(i, styleName);
			for (int j=columnStart; j < columnStart+columnAmount; j++) {
				mainTable.setText(i, j, "");
			}
		}
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
			updateCategories(result);
		}

	}

}