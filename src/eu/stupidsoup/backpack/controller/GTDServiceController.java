package eu.stupidsoup.backpack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import eu.stupidsoup.backpack.client.GTDService;
import eu.stupidsoup.spring.GWTIntegrationController;


@Controller
@RequestMapping("backpack/gtdservice")
public class GTDServiceController extends GWTIntegrationController implements GTDService {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void ping() throws IllegalArgumentException {
		System.out.println("ping called");
	}
}
