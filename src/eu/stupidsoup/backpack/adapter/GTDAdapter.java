package eu.stupidsoup.backpack.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import eu.stupidsoup.backpack.bean.GTDListBean;
import eu.stupidsoup.backpack.model.GTDDao;


@Service
public class GTDAdapter {
	@Autowired
	@Qualifier("memoryDao")
	private GTDDao gtdDao;
	
	public GTDListBean getGTDListByName(String name) {
		return this.gtdDao.getGTDListByName(name).createBean();
	}
}
