package eu.stupidsoup.backpack;

import java.io.IOException;
import java.util.HashMap;
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
			
			Map backpackModel = new HashMap();
			backpackModel.put("allNextLists", this.listManager.getAllNextListsAsString());
			return new ModelAndView("nextlists", "model", backpackModel);
			
		}

		public void setListManager(ListManager listManager) {
			this.listManager = listManager;
		}
}
