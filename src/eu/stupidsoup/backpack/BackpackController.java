package eu.stupidsoup.backpack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import eu.stupidsoup.backpack.accessor.BackpackList;
import eu.stupidsoup.backpack.accessor.BackpackListItem;

public class BackpackController implements Controller {

		private ListManager listManager;

		
		public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			Map<String, Map<String, List<String>>> backpackModel = new HashMap<String, Map<String, List<String>>>();
			backpackModel.put("allNextLists", this.listManager.getAllListsByNameAsStringList("Next"));
			backpackModel.put("allWaitLists", this.listManager.getAllListsByNameAsStringList("Waiting"));
			backpackModel.put("allLaterLists", this.listManager.getAllListsByNameAsStringList("Later"));

			Map<String, List<String>> extraMap = new HashMap<String, List<String>>();
			extraMap.put("Inbox", this.listManager.getListByName("New", "Inbox"));
			backpackModel.put("extraLists", extraMap);
			
			return new ModelAndView("main", "model", backpackModel);
		}

		public void setListManager(ListManager listManager) {
			this.listManager = listManager;
		}
}
