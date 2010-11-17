package eu.stupidsoup.backpack.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gtdservice")
public interface GTDService extends RemoteService {
	public void ping() throws IllegalArgumentException;
}
