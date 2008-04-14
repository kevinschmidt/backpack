package eu.stupidsoup.backpack.accessor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class AxiomAccessor implements Accessor {
	String url;
	String apiKey;
	
	ServiceClient serviceClient;
	Options serviceOptions;
	OMFactory elementFactory;
	
	Map<String, Integer> pageCache;
	

	public AxiomAccessor() {
		pageCache = new TreeMap<String, Integer>();
		elementFactory = OMAbstractFactory.getOMFactory();
		
		try {
			serviceClient = new ServiceClient();

			serviceOptions = new Options();
			serviceOptions.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);
			serviceOptions.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
			serviceOptions.setProperty(
				Constants.Configuration.MESSAGE_TYPE,
				org.apache.axis2.transport.http.HTTPConstants.MEDIA_TYPE_X_WWW_FORM
			);
			
			serviceClient.setOptions(serviceOptions);
		} catch (AxisFault e) {
			e.printStackTrace();
			throw new RuntimeException("Problem in ServiceClient creation.");
		}
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public void setCredentials(String url, String apiKey) {
		this.url = url;
		this.apiKey = apiKey;
	}



	public Map<String, Integer> getPageMap() {
		this.updatePageCache();
		return this.pageCache;
	}
	
	
	public SortedSet<String> getPageNames() {
		this.updatePageCache();
		return new TreeSet<String>(this.pageCache.keySet());
	}


	public BackpackList getListByName(Integer pageId, String listName) {
		OMElement response = this.sendRequest( Accessor.URL_PAGE + "/" + pageId );
		OMElement lists = response.getFirstChildWithName( new QName("page") ).getFirstChildWithName( new QName("lists") );
		if ( lists == null ) {
			return null;
		}
		for (Iterator listItr = lists.getChildrenWithName( new QName("list") ); listItr.hasNext(); ) {
			OMElement xmlList = (OMElement) listItr.next();
			if ( xmlList.getAttributeValue(new QName("name")).equalsIgnoreCase(listName) ) {
				return this.extractListFromXML(xmlList);
			}
		}
		
		return null;
	}


	public BackpackList getListByName(String pageName, String listName) {
		if (pageCache.isEmpty() ) {
			this.updatePageCache();
		}
		Integer pageId = pageCache.get(pageName);
		if (pageId == null) {
			return null;
		}
		return getListByName(pageId, listName);
	}
	
	
	public OMElement getPageByName(String pageName) {
		if (pageCache.isEmpty() ) {
			this.updatePageCache();
		}
		Integer pageId = pageCache.get(pageName);
		if (pageId == null) {
			return null;
		}
		
		OMElement response = this.sendRequest( Accessor.URL_PAGE + "/" + pageId );
		return response.getFirstChildWithName( new QName("page") );
	}
	
	
	public BackpackGTD getGTDByName(String pageName) {
		BackpackGTD gtd = new BackpackGTD();
		gtd.setPageName(pageName);
		
		OMElement page = this.getPageByName(pageName);
		gtd.setPageTags(this.extractTagsFromXML( page.getFirstChildWithName( new QName("tags") ) ));
		
		OMElement lists = page.getFirstChildWithName( new QName("lists") );
		if ( lists == null ) {
			return gtd;
		}
		for (Iterator listItr = lists.getChildrenWithName( new QName("list") ); listItr.hasNext(); ) {
			OMElement xmlList = (OMElement) listItr.next();
			if ( xmlList.getAttributeValue(new QName("name")).equalsIgnoreCase("Next") ) {
				gtd.setNextList(this.extractListFromXML(xmlList));
			}
			if ( xmlList.getAttributeValue(new QName("name")).equalsIgnoreCase("Waiting") ) {
				gtd.setWaitingList(this.extractListFromXML(xmlList));
			}
			if ( xmlList.getAttributeValue(new QName("name")).equalsIgnoreCase("Later") ) {
				gtd.setLaterList(this.extractListFromXML(xmlList));
			}
		}
		return gtd;
	}

	
	
	private void updatePageCache() {
		if (pageCache.isEmpty() ) {
			this.pageCache.putAll( this.getFullPageList() );
		}
	}
	
	private BackpackList extractListFromXML(OMElement xmlList) {
		BackpackList itemList = new BackpackList();
		itemList.setId( Long.parseLong( xmlList.getAttributeValue(new QName("id")) ) );
		itemList.setName( xmlList.getAttributeValue(new QName("name")) );
			
		Iterator items = xmlList.getFirstChildWithName( new QName("items") ).getChildrenWithName( new QName("item") );
		while ( items.hasNext() ) {
			OMElement xmlItem = (OMElement) items.next();
			BackpackListItem item = new BackpackListItem();
			item.setId( Long.parseLong( xmlItem.getAttributeValue(new QName("id")) ) );
			item.setCompleted( Boolean.parseBoolean( xmlItem.getAttributeValue(new QName("completed")) ) );
			item.setText( xmlItem.getText() );
			itemList.getItemList().add( item );
		}
		
		return itemList;
	}
	
	private List<String> extractTagsFromXML(OMElement xmlList) {
		List<String> tagList = new ArrayList<String>();
		if (xmlList == null) {
			return tagList;
		}
		
		Iterator items = xmlList.getChildrenWithName( new QName("tag") );
		while ( items.hasNext() ) {
			OMElement xmlItem = (OMElement) items.next();
			tagList.add( xmlItem.getAttributeValue(new QName("name")) );
		}
		
		return tagList;
	}
	
	private Map<String, Integer> getFullPageList() {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();

		OMElement response = this.sendRequest( Accessor.URL_PAGES_ALL );			
		OMElement pages = response.getFirstChildWithName( new QName("pages") );
		for (Iterator children = pages.getChildrenWithName( new QName("page") ); children.hasNext(); ) {
			OMElement child = (OMElement) children.next();
			resultMap.put(
				child.getAttributeValue( new QName("title") ),
				Integer.parseInt( child.getAttributeValue( new QName("id") ) )
			);
		}
		
		return resultMap;
	}
	
	
	private OMElement prepareRequestObject() {
        OMElement rootElement = elementFactory.createOMElement("request", null);
        OMElement tokenElement = elementFactory.createOMElement("token", null, rootElement);
        tokenElement.setText(this.apiKey);
        return rootElement;
	}
	
	
	private OMElement sendRequest(String urlPart) {
		try {
			serviceOptions.setTo( new EndpointReference( this.url + urlPart ) );
			OMElement response = serviceClient.sendReceive( this.prepareRequestObject() );
			if ( !response.getAttributeValue( new QName("success") ).equalsIgnoreCase("true") ) {
				throw new RuntimeException("Server responded with failure");
			}
			return response;
		} catch (AxisFault e) {
			e.printStackTrace();
			throw new RuntimeException("Problem sending request.");
		}
	}

}
