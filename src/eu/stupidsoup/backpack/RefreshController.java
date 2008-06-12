package eu.stupidsoup.backpack;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class RefreshController implements Controller {

	private BackpackManager backpackManager;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		this.backpackManager.refresh();
		return new ModelAndView("refresh");
	}

	public void setBackpackManager(BackpackManager manager) {
		this.backpackManager = manager;
	}

}
