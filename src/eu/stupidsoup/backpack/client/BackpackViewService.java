package eu.stupidsoup.backpack.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.stupidsoup.backpack.client.model.BackpackClientGTD;


@RemoteServiceRelativePath("../backpackViewService.htm")
public interface BackpackViewService extends RemoteService {
	public List<BackpackClientGTD> getGTDListsByTag(String tag);
}
