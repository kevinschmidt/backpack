package eu.stupidsoup.backpack.accessor;

import java.util.Map;
import java.util.Set;



public interface Accessor {
	public static String URL_PAGES_ALL = "/ws/pages/all";
	public static String URL_PAGE = "/ws/page";
	
	public void setUrl(String url);
	public void setApiKey(String apiKey);
	public void setCredentials(String url, String apiKey);
	
	public Map<String, Integer> getPageMap();
	public Set<String> getPageNames();

	public BackpackList getListByName(Integer pageId, String listName);
	public BackpackList getListByName(String pageName, String listName);
	
	public BackpackGTD getGTDByName(String pageName);
}
