package eu.stupidsoup.backpack.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.stupidsoup.backpack.bean.GTDListBean;


public interface GTDServiceAsync {
	public void ping(AsyncCallback<Void> callback);
	public void getGTDListByName(String name, AsyncCallback<GTDListBean> callback);
}
