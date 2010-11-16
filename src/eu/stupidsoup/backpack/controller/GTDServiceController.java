package eu.stupidsoup.backpack.controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.stupidsoup.backpack.client.GTDService;

public class GTDServiceController extends RemoteServiceServlet implements GTDService {
	private static final long serialVersionUID = 1L;
	

	@Override
	public void ping() throws IllegalArgumentException {
		System.out.println("ping called");
	}

}
