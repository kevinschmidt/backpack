package eu.stupidsoup.backpack.client;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.stupidsoup.backpack.client.model.BackpackClientGTD;


public interface BackpackViewServiceAsync {
	public void getGTDListsByTag(String tag, AsyncCallback<List<BackpackClientGTD>> callback);
	public void getAllGTDTags(AsyncCallback<Set<String>> callback);
}
