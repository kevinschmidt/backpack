package eu.stupidsoup.backpack.accessor;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
	
	HashMap<String, Integer> pageCache;
	

	public AxiomAccessor() {
		pageCache = new HashMap<String, Integer>();
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


	
	public Map<Integer, String> getPageList() {
		Map<Integer, String> resultMap = new TreeMap<Integer, String>();

		OMElement response = this.sendRequest( Accessor.URL_PAGES_ALL );			
		OMElement pages = response.getFirstChildWithName( new QName("pages") );
		for (Iterator children = pages.getChildrenWithName( new QName("page") ); children.hasNext(); ) {
			OMElement child = (OMElement) children.next();
			resultMap.put(
				Integer.parseInt( child.getAttributeValue( new QName("id") ) ),
				child.getAttributeValue( new QName("title") )
			);
		}
		
		this.updatePageCache(resultMap);
		return resultMap;
	}


	public BackpackList getListByName(Integer pageId, String listName) {
		BackpackList itemList = new BackpackList();
		
		OMElement response = this.sendRequest( Accessor.URL_PAGE + "/" + pageId );
		OMElement lists = response.getFirstChildWithName( new QName("page") ).getFirstChildWithName( new QName("lists") );
		if ( lists == null ) {
			return null;
		}
		for (Iterator listItr = lists.getChildrenWithName( new QName("list") ); listItr.hasNext(); ) {
			OMElement xmlList = (OMElement) listItr.next();
			if ( xmlList.getAttributeValue(new QName("name")).equalsIgnoreCase(listName) ) {
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
			}
		}
		
		return itemList;
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

	
	
	private void updatePageCache() {
		this.updatePageCache(this.getPageList());
	}
	
	private void updatePageCache(Map<Integer, String> pageMap) {
		this.pageCache = new HashMap<String, Integer>();
		for (Map.Entry<Integer, String> e : pageMap.entrySet()) {
			pageCache.put(e.getValue(), e.getKey());
		}
	}
	
}
