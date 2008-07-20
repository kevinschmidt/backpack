package eu.stupidsoup.backpack.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.stupidsoup.backpack.client.model.BackpackClientGTD;


public class BackpackView implements EntryPoint, ClickListener {
	private BackpackViewServiceAsync backpackAsync;
	private FlexTable mainTable = new FlexTable();
	private Map<String,Boolean> expandedList = new HashMap<String,Boolean>();
	

	public void onModuleLoad() {
		backpackAsync = (BackpackViewServiceAsync) GWT.create(BackpackViewService.class);
	    RootPanel.get().add(mainTable);

		mainTable.setWidth("100%");
		mainTable.setBorderWidth(1);
		mainTable.getColumnFormatter().setWidth(0, "10%");
		mainTable.setText(0, 0, "Category");
		mainTable.setText(0, 1, "Next List");
		mainTable.setText(0, 2, "Waiting List");
		mainTable.setText(0, 3, "Later List");
		
		Hyperlink link0 = new Hyperlink("Private", "private");
		link0.addClickListener(this);
		Hyperlink link1 = new Hyperlink("Half-Private", "halfprivate");
		link1.addClickListener(this);
		Hyperlink link2 = new Hyperlink("Work", "work");
		link2.addClickListener(this);

		mainTable.setWidget(1, 0, link0);
		mainTable.setWidget(2, 0, link1);
		mainTable.setWidget(3, 0, link2);
		
		expandedList.put("private", false);
		expandedList.put("halfprivate", false);
		expandedList.put("work", false);
	}

	
	public void onClick(Widget sender) {
		if (sender.getClass() == Hyperlink.class ) {	
			final String category = ((Hyperlink) sender ).getTargetHistoryToken();

			if ( this.expandedList.containsKey(category) ) { 
				if ( !this.expandedList.get(category) ) {
					this.setLoadingForCategory(category);
					
					AsyncCallback<List<BackpackClientGTD>> callback = new AsyncCallback<List<BackpackClientGTD>>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught.getMessage());
						}
						
						public void onSuccess(List<BackpackClientGTD> result) {
							updateSubTableForCategory(category, result);
						}
					};
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
			if (gtd.getNextList() != null) {
				mainTable.setText(newRow + counter, 1, gtd.getNextList().getItemsAsString());
			}
			if (gtd.getWaitingList() != null) {
				mainTable.setText(newRow + counter, 2, gtd.getWaitingList().getItemsAsString());
			}
			if (gtd.getLaterList() != null) {
				mainTable.setText(newRow + counter, 3, gtd.getLaterList().getItemsAsString());
			}
			counter++;
		}
	}


	private void setLoadingForCategory(String category) {
		if ( !this.expandedList.containsKey(category) );
		
		int newRow = this.createRowsAfterCategory(category, 1);
		mainTable.setText(newRow, 0, "Loading ...");
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
	
}