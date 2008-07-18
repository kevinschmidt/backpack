package eu.stupidsoup.backpack.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
		Hyperlink link1 = new Hyperlink("Half-Private", "half-private");
		link1.addClickListener(this);
		Hyperlink link2 = new Hyperlink("Work", "work");
		link2.addClickListener(this);

		mainTable.setWidget(1, 0, link0);
		mainTable.setWidget(2, 0, link1);
		mainTable.setWidget(3, 0, link2);
		
		expandedList.put("private", false);
		expandedList.put("half-private", false);
		expandedList.put("work", false);
	}

	
	public void onClick(Widget sender) {
		if (sender.getClass() == Hyperlink.class ) {	
			final String category = ((Hyperlink) sender ).getTargetHistoryToken();

			if ( this.expandedList.containsKey(category) ) { 
				if ( this.expandedList.get(category) == false ) {
					final Integer newRowNumber = this.createTableForCategory(category);
					
					AsyncCallback<List<BackpackClientGTD>> callback = new AsyncCallback<List<BackpackClientGTD>>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught.getMessage());
						}
						
						public void onSuccess(List<BackpackClientGTD> result) {
							updateTableForRow(category, newRowNumber, result.get(0));
						}
					};
					backpackAsync.getGTDListsByTag(category, callback);
				} else {
					this.destroyTableForCategory(category);
				}
			}
		}
	}
	

	
	private void updateTableForRow(String category, int newRow, BackpackClientGTD item) {
		if ( !this.expandedList.containsKey(category) || this.expandedList.get(category) != true );
		
		mainTable.setText(newRow, 0, item.getPageName());
		mainTable.setText(newRow, 1, item.getNextList().getItemsAsString());
		mainTable.setText(newRow, 2, item.getWaitingList().getItemsAsString());
		mainTable.setText(newRow, 3, item.getLaterList().getItemsAsString());
	}


	private Integer createTableForCategory(String category) {
		if ( !this.expandedList.containsKey(category) || this.expandedList.get(category) != false ) return null;

		Integer categoryRow = this.findRowWithCategory(category);
		if (categoryRow == null) return null;
		
		int newRow = mainTable.insertRow(categoryRow+1);
		mainTable.setText(newRow, 0, "Loading ...");
				
		this.expandedList.put(category, true);
		return newRow;
	}
	
	private void destroyTableForCategory(String category) {
		if ( !this.expandedList.containsKey(category) || this.expandedList.get(category) != true ) return;
		
		Integer categoryRow = this.findRowWithCategory(category);
		if (categoryRow == null) return;
		
		mainTable.removeRow(categoryRow+1);
		this.expandedList.put(category, false);
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
			} else {
				if ( mainTable.getText(i, 0).equals(category) ) {
					return i;
				}
			}
		}
		return null;
	}
	
}