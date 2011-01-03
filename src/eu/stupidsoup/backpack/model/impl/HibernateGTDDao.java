package eu.stupidsoup.backpack.model.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("hibernateDao")
public class HibernateGTDDao extends BasicGTDDao {

	@Override
	protected BasicGTDList getSessionGTDList(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BasicGTDList getSessionGTDListByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
