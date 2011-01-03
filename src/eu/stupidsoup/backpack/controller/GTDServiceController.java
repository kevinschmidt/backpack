package eu.stupidsoup.backpack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.stupidsoup.backpack.adapter.GTDAdapter;
import eu.stupidsoup.backpack.bean.GTDListBean;
import eu.stupidsoup.backpack.client.GTDService;
import eu.stupidsoup.spring.GWTIntegrationController;


@Controller
@RequestMapping("backpack/gtdservice")
public class GTDServiceController extends GWTIntegrationController implements GTDService {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private GTDAdapter gtdAdapter;
	
	@Override
	public void ping() throws IllegalArgumentException {
		System.out.println("ping called");
	}

	@Override
	public GTDListBean getGTDListByName(String name) {
		return this.gtdAdapter.getGTDListByName(name);
	}
}
