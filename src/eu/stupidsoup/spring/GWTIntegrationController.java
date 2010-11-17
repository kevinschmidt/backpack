package eu.stupidsoup.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;



public abstract class GWTIntegrationController extends RemoteServiceServlet implements ServletContextAware {
	private static final long serialVersionUID = 1L;
	private ServletContext servletContext;
	
	
	public GWTIntegrationController() {
		// super has to be called so this class gets processed
		// explicitly stating this for people overriding this constructor
		super();
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doPost(request, response);
		return null;
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