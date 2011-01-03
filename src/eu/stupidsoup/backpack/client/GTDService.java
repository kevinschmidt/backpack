package eu.stupidsoup.backpack.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.stupidsoup.backpack.bean.GTDListBean;


@RemoteServiceRelativePath("gtdservice")
public interface GTDService extends RemoteService {
	public void ping() throws IllegalArgumentException;
	public GTDListBean getGTDListByName(String name);
}
