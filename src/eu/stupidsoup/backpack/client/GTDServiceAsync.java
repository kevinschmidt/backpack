package eu.stupidsoup.backpack.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface GTDServiceAsync {
	public void ping(AsyncCallback<Void> callback);
}
