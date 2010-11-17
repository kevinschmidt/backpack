package eu.stupidsoup.backpack.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.stupidsoup.backpack.client.GTDService;


@Controller
@RequestMapping("backpack/gtdservice")
public class GTDServiceController extends RemoteServiceServlet implements GTDService, ServletContextAware {
	private static final long serialVersionUID = 1L;
	private ServletContext servletContext;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doPost(request, response);
		return null;
	}
	

	@Override
	public void ping() throws IllegalArgumentException {
		System.out.println("ping called");
	}


	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
